package com.team9.istudy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team9.istudy.Model.Course;
import com.team9.istudy.R;

import java.util.ArrayList;
import java.util.List;

import cn.smssdk.gui.GroupListView;

public class NetcourseAdapter extends BaseExpandableListAdapter {
    private Context mcontext;
    public String[] head={"我的网课"};
    public static List<Course> netcourselist=new ArrayList<Course>();

    @Override
    public int getGroupCount() {
        return head.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return netcourselist.size();
    }

    @Override
    public Object getGroup(int i) {
        return head[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int headposition, boolean isExpanded, View convertview, ViewGroup parent) {
        GroupView group;
        if(convertview==null){
            convertview= LayoutInflater.from(parent.getContext()).inflate(R.layout.netcourseheader_item,parent,false);
            group=new GroupView();
            group.groupname=convertview.findViewById(R.id.group_name);
            group.img=convertview.findViewById(R.id.course_icon);
            convertview.setTag(group);
        }
        else{
            group=(GroupView) convertview.getTag();
        }
        group.groupname.setText(head[headposition]);
        group.img.setImageResource(R.drawable.wangke);
        return convertview;
    }

    @Override
    public View getChildView(int groupposition, int childposition, boolean isLastChild, View convertview, ViewGroup parent) {
        ChildView cv;
        if(convertview==null){
            convertview=LayoutInflater.from(parent.getContext()).inflate(R.layout.netcourse_item,parent,false);
            cv=new ChildView();
            cv.icon=convertview.findViewById(R.id.netcourse_img);
            cv.title=convertview.findViewById(R.id.netcourse_title);
            cv.teacher=convertview.findViewById(R.id.netcourse_teacher);
            cv.price=convertview.findViewById(R.id.netcourse_price);
            convertview.setTag(cv);
        }
        else{
            cv=(ChildView) convertview.getTag();
        }
        cv.title.setText(netcourselist.get(childposition).getCourse_title());
        cv.price.setText("￥"+String.valueOf(netcourselist.get(childposition).getCourse_price()));
        cv.teacher.setText(netcourselist.get(childposition).getCourse_author());
        cv.icon.setImageResource(R.drawable.wangke);
        return convertview;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    class GroupView{
        ImageView img;
        TextView groupname;
    }
    class ChildView{
        ImageView icon;
        TextView title;
        TextView teacher;
        TextView price;
    }
}

