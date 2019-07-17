package com.team9.istudy.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.team9.istudy.Activity.AllcourseActivity;
import com.team9.istudy.Activity.FreecourseActivity;
import com.team9.istudy.Activity.JingpincourseActivity;
import com.team9.istudy.Activity.LoginActivity;
import com.team9.istudy.Activity.TuijianActivity;
import com.team9.istudy.Adapter.CourseHeaderAdapter;
import com.team9.istudy.Adapter.NetcourseAdapter;
import com.team9.istudy.Model.CourseHeader;
import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;
import com.team9.istudy.Util.MyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Frag_allsub extends Fragment {

    private ListView lv;
    private List<CourseHeader> courseHeaderList =new ArrayList<CourseHeader>();
    private ExpandableListView expandableListView;
    private AlertDialog alertDialog;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_allsub, container, false);
        initCourse(view);
        CourseHeaderAdapter adapter=new CourseHeaderAdapter(getContext(),R.layout.courseheader_item, courseHeaderList);
        lv=view.findViewById(R.id.lv);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CourseHeader courseHeader = courseHeaderList.get(i);
                switch (i){
                    case 0:
                        Intent in1=new Intent(getContext(), JingpincourseActivity.class);
                        startActivity(in1);
                        break;
                    case 1:
                        Intent in2=new Intent(getContext(), FreecourseActivity.class);
                        startActivity(in2);
                        break;
                    case 2:
                        Intent in3=new Intent(getContext(), TuijianActivity.class);
                        startActivity(in3);
                        break;
                }
            }
        });
        return view;
    }

    private void initCourse(View view) {
        CourseHeader jingpin=new CourseHeader("精品课程",R.drawable.jingpin);
        courseHeaderList.add(jingpin);
        CourseHeader mianfei=new CourseHeader("免费课程",R.drawable.mianfei);
        courseHeaderList.add(mianfei);
        CourseHeader tuijian=new CourseHeader("推荐课程",R.drawable.recommendcourse);
        courseHeaderList.add(tuijian);

        requestData();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        expandableListView=view.findViewById(R.id.netcourse);
        expandableListView.setAdapter(new NetcourseAdapter());
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                return false;
            }
        });
    }

    public void requestData() {
        alertDialog=new AlertDialog.Builder(getContext())   //等待提示框
                .setMessage("loading...")
                .create();
        alertDialog.show();
        String jingpinURL="http://192.168.43.212:8080/maven-ssm-web/personController//getVideo";//推荐课程
        String uname= LoginActivity.username;
        HttpUtil.sendOkHttpRequest(jingpinURL, uname ,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(alertDialog!=null)
                            alertDialog.hide();
                        Toast.makeText(getContext(),"获取服务器信息失败，请检查网络状况",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse( Call call,  final Response response) throws IOException {
                String responseText = response.body().string();
                MyUtil.handleNetCourseResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(alertDialog!=null)
                            alertDialog.hide();
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
    }
}