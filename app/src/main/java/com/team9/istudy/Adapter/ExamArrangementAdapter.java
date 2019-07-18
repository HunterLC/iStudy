package com.team9.istudy.Adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.team9.istudy.Model.ExamArrangement;
import com.team9.istudy.R;


public class ExamArrangementAdapter extends BaseAdapter {

    private List<ExamArrangement> list;
    private LayoutInflater inflater;
    //上下文
    private Context context;
    public List<ExamArrangement> getList() {
        return list;
    }
    public void setList(List<ExamArrangement> list) {
        this.list = list;
    }
    public ExamArrangementAdapter(Context context, List<ExamArrangement> list){
        this.list = list;
        inflater = LayoutInflater.from(context);
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ExamArrangement examArrangement = (ExamArrangement) this.getItem(position);

        ViewHolder viewHolder;

        if(convertView == null){

            viewHolder = new ViewHolder();
            context=parent.getContext();
            convertView = inflater.inflate(R.layout.kaoshilist_item, null);
            viewHolder.ExamNameView=convertView.findViewById(R.id.exam_name);
            viewHolder.ExamTimeView=convertView.findViewById(R.id.exam_time);
            viewHolder.ExamPlaceView=convertView.findViewById(R.id.exam_place);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //获取viewHolder实例
        viewHolder = (ViewHolder)convertView.getTag();
        //设置数据
        AssetManager mgr = context.getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/biaozhunkaitijian.ttf");//仿宋
        viewHolder.ExamNameView.setTypeface(tf);
        viewHolder.ExamTimeView.setTypeface(tf);
        viewHolder.ExamPlaceView.setTypeface(tf);
        viewHolder.ExamNameView.setText(examArrangement.getExam_name());
        viewHolder.ExamNameView.setTextSize(18);
        viewHolder.ExamTimeView.setText(examArrangement.getExam_time());
        viewHolder.ExamTimeView.setTextSize(18);
        viewHolder.ExamPlaceView.setText(examArrangement.getExam_place());
        viewHolder.ExamPlaceView.setTextSize(18);
        viewHolder.ExamNameView.setTextColor(0xFF000000);
        viewHolder.ExamNameView.setBackgroundColor(0xFFFFFFFF);
        viewHolder.ExamTimeView.setTextColor(0xFF000000);
        viewHolder.ExamTimeView.setBackgroundColor(0xFFFFFFFF);
        viewHolder.ExamPlaceView.setTextColor(0xFF000000);
        viewHolder.ExamPlaceView.setBackgroundColor(0xFFFFFFFF);

        return convertView;
    }

    public static class ViewHolder{
        public TextView ExamNameView;
        public TextView ExamTimeView;
        public TextView ExamPlaceView;
    }

}
