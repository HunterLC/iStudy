package com.team9.istudy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.team9.istudy.Model.Notice;
import com.team9.istudy.R;

import java.util.List;

public class NoticeAdapter extends BaseAdapter implements View.OnClickListener {
    //上下文
    private Context context;
    //数据项
    private List<Notice> data;
    public NoticeAdapter(List<Notice> data){
        this.data = data;
    }
    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(context == null)
            context = viewGroup.getContext();
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice_cardview,null);
            viewHolder = new ViewHolder();
            viewHolder.noticeNameView = (TextView)view.findViewById(R.id.notice_name);
            viewHolder.noticeTimeView=view.findViewById(R.id.notice_time);
            view.setTag(viewHolder);
        }
        //获取viewHolder实例
        viewHolder = (ViewHolder)view.getTag();
        //设置数据
        viewHolder.noticeNameView.setTag(R.id.notice_name,i);//添加此代码
        viewHolder.noticeNameView.setText(data.get(i).getNoticeSource());
        viewHolder.noticeTimeView.setText(data.get(i).getNoticeTime());
        //设置监听事件
        return view;
    }
    public void setData(List<Notice> data) {
        this.data = data;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
        }
    }

    static class ViewHolder{
        TextView noticeNameView;
        TextView noticeTimeView;
    }

}
