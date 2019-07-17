package com.team9.istudy.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team9.istudy.Model.CourseHeader;
import com.team9.istudy.R;

import java.util.List;

public class CourseHeaderAdapter extends ArrayAdapter {
    private int resourceId;

    public CourseHeaderAdapter(@NonNull Context context, int resource, @NonNull List<CourseHeader> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CourseHeader courseHeader = (CourseHeader) getItem(position);//获取Course实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);//实例化一个对象,使用Inflater对象来将布局文件解析成一个View
        ImageView course_icon = view.findViewById(R.id.course_icon);//获取该布局内的图片视图
        TextView course_name = view.findViewById(R.id.course_name);//获取该布局内的文本视图
        ImageView expand_icon = view.findViewById(R.id.expand_icon);
        course_icon.setImageResource(courseHeader.getCourse_icon());//为图片视图设置图片资源
        course_name.setText(courseHeader.getCourse_name());//为文本视图设置文本内容
        expand_icon.setImageResource(R.drawable.zhankai);
        return view;
    }
}

