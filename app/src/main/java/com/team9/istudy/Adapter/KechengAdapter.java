package com.team9.istudy.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.team9.istudy.Model.Kecheng;
import com.team9.istudy.R;

import java.util.List;

public class KechengAdapter extends BaseAdapter implements View.OnClickListener {
    //上下文
    private Context context;
    //数据项
    private List<Kecheng> data;
    public KechengAdapter(List<Kecheng> data){
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.class_cardview,null);
            viewHolder = new ViewHolder();
            viewHolder.classNameView = (TextView)view.findViewById(R.id.class_name);
            viewHolder.classTeacherView=view.findViewById(R.id.class_teacher);
            viewHolder.rightButtonView = (ImageButton)view.findViewById(R.id.class_righticon);
            view.setTag(viewHolder);
        }
        //获取viewHolder实例
        viewHolder = (ViewHolder)view.getTag();
        //设置数据
        AssetManager mgr = context.getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/FZSTK.TTF");//仿宋
        viewHolder.classNameView.setTypeface(tf);
        viewHolder.classTeacherView.setTypeface(tf);
        viewHolder.classNameView.setTag(R.id.class_name,i);//添加此代码
        viewHolder.classNameView.setText(data.get(i).getClassName());
        viewHolder.classTeacherView.setText(data.get(i).getTearcherName());
        //设置监听事件
        viewHolder.rightButtonView.setTag(R.id.class_righticon,i);
        viewHolder.rightButtonView.setOnClickListener(this);
        //设置数据
        viewHolder.rightButtonView.setOnClickListener(this);
        return view;
    }
    public void setData(List<Kecheng> data) {
        this.data = data;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.class_righticon:
                int t = (int)view.getTag(R.id.class_righticon);
                Toast.makeText(context,"我是按钮" + (t+1),Toast.LENGTH_SHORT).show();
                break;

        }
    }

    static class ViewHolder{
        TextView classNameView;
        TextView classTeacherView;
        ImageButton rightButtonView;
    }

}