package com.team9.istudy.Adapter;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.team9.istudy.Gson.EnglishDefinition;
import com.team9.istudy.Model.MyWord;
import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;
import com.team9.istudy.Util.Utility;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EnglishCET4Adapter extends RecyclerView.Adapter<EnglishCET4Adapter.ViewHolder> {
    private static final int UNSELECTED = -1;

    private RecyclerView recyclerView;
    private List<MyWord> myWordList = new ArrayList<>();
    private int selectedItem = UNSELECTED;
    private int currentPosition;
    private String EnglishTranslation = null ;

    public EnglishCET4Adapter(RecyclerView recyclerView, List<MyWord> myWordList, int position) {
        this.recyclerView = recyclerView;
        this.myWordList = myWordList;
        this.currentPosition = position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return myWordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {
        private ExpandableLayout expandableLayout;
        private TextView expandButton;
        private TextView translateTextView,exampleTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            //expandableLayout
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            expandableLayout.setInterpolator(new OvershootInterpolator());
            expandableLayout.setOnExpansionUpdateListener(this);

            //expandButton
            expandButton = itemView.findViewById(R.id.expand_button);
            expandButton.setOnClickListener(this);

            //txtView
            translateTextView = itemView.findViewById(R.id.translate_textview_content);
            exampleTextView = itemView.findViewById(R.id.example_textview_content);
        }

        public void bind() {
            int position = getAdapterPosition();
            boolean isSelected = position == selectedItem;
            if(currentPosition <4){
                MyWord myWord = myWordList.get(position);
                expandButton.setText(myWord.getWordName());  //每个单词
                //translateTextView.setText(myWord.getTranslate());
               // exampleTextView.setText(myWord.getExample());
            }
            else
                expandButton.setText(position+"");
            expandButton.setSelected(isSelected);
            expandableLayout.setExpanded(isSelected, false);
        }

        @Override
        public void onClick(View view) {
            ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
            if (holder != null) {
                initTranslate(holder);
                /*try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                holder.translateTextView.setText(EnglishTranslation);
                holder.expandButton.setSelected(false);
                holder.expandableLayout.collapse();
            }

            int position = getAdapterPosition();
            if (position == selectedItem) {
                selectedItem = UNSELECTED;
            } else {
                expandButton.setSelected(true);
                expandableLayout.expand();
                selectedItem = position;
            }


        }

        public void initTranslate(ViewHolder holder){
            //刷新单词翻译
            String searchWordURL = "https://api.shanbay.com/api/v1/bdc/search/?word="+holder.expandButton.getText();
            HttpUtil.sendOkHttpRequest(searchWordURL, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();//获得返回的数据
                    EnglishDefinition englishDefinition = Utility.handleEnglishDefinitionResponse(responseText);
                    EnglishTranslation = englishDefinition.more.definition;
                }
            });
        }

        @Override
        public void onExpansionUpdate(float expansionFraction, int state) {
            Log.d("ExpandableLayout", "State: " + state);
            if (state == ExpandableLayout.State.EXPANDING) {
                recyclerView.smoothScrollToPosition(getAdapterPosition());
            }
        }
    }
}