package com.team9.istudy.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.team9.istudy.Model.Score;
import com.team9.istudy.R;

import java.util.List;

public class ScoreAdapter extends BaseAdapter {
    private List<Score> list;
    private LayoutInflater inflater;
    private Context context;
    public ScoreAdapter(Context context, List<Score> list){
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    public void setList(List<Score> list) {
        this.list = list;
    }
    @Override
    public int getCount() {
        int ret = 0;
        if(list!=null){
            ret = list.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Score examArrangement = (Score) this.getItem(position);

        ScoreAdapter.ViewHolder viewHolder;

        if(convertView == null){

            viewHolder = new ViewHolder();
            context=parent.getContext();
            convertView = inflater.inflate(R.layout.scorelist_item, null);
            /*viewHolder.goodId = (TextView) convertView.findViewById(R.id.text_id);
            viewHolder.goodName = (TextView) convertView.findViewById(R.id.text_goods_name);
            viewHolder.goodCodeBar = (TextView) convertView.findViewById(R.id.text_codeBar);
            viewHolder.goodNum = (TextView) convertView.findViewById(R.id.text_num);
            viewHolder.goodCurrPrice = (TextView) convertView.findViewById(R.id.text_curPrice);
            viewHolder.goodMoney = (TextView) convertView.findViewById(R.id.text_money);*/

            viewHolder.ScoreNameView=convertView.findViewById(R.id.score_name);
            viewHolder.ScoreCodeView=convertView.findViewById(R.id.score_code);
            viewHolder.ScoreScoreView=convertView.findViewById(R.id.score_score);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AssetManager mgr = context.getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/biaozhunkaitijian.ttf");//仿宋
        viewHolder.ScoreNameView.setTypeface(tf);
        viewHolder.ScoreCodeView.setTypeface(tf);
        viewHolder.ScoreScoreView.setTypeface(tf);
        viewHolder.ScoreNameView.setText(examArrangement.getScoreName());
        viewHolder.ScoreNameView.setTextSize(18);
        viewHolder.ScoreCodeView.setText(String.valueOf(examArrangement.getScoreCode()));
        viewHolder.ScoreCodeView.setTextSize(18);
        viewHolder.ScoreScoreView.setText(String.valueOf(examArrangement.getScoreScore()));
        viewHolder.ScoreScoreView.setTextSize(18);
        viewHolder.ScoreNameView.setTextColor(0xFF000000);
        viewHolder.ScoreNameView.setBackgroundColor(0xFFFFFFFF);
        viewHolder.ScoreCodeView.setTextColor(0xFF000000);
        viewHolder.ScoreCodeView.setBackgroundColor(0xFFFFFFFF);
        viewHolder.ScoreScoreView.setTextColor(0xFF000000);
        viewHolder.ScoreScoreView.setBackgroundColor(0xFFFFFFFF);
        return convertView;
    }

    public static class ViewHolder{
        public TextView ScoreNameView;
        public TextView ScoreCodeView;
        public TextView ScoreScoreView;
    }
}
