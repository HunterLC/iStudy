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

import com.team9.istudy.Model.Course;
import com.team9.istudy.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends ArrayAdapter {
    private int resourceId;
    public static List<Course> courseList =new ArrayList<Course>();

    public CourseAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Course buycourse=(Course) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);//实例化一个对象,使用Inflater对象来将布局文件解析成一个View
        TextView title=view.findViewById(R.id.course_title);
        ImageView img=view.findViewById(R.id.course_img);
        TextView inf=view.findViewById(R.id.course_inf);
        TextView author=view.findViewById(R.id.course_author);
        TextView price=view.findViewById(R.id.course_price);
        title.setText(buycourse.getCourse_title());
        img.setImageResource(R.drawable.jingpin);
        inf.setText(buycourse.getCourse_inf());
        author.setText(buycourse.getCourse_author());
        price.setText("$"+String.valueOf(buycourse.getCourse_price()));
        return view;
    }
}
