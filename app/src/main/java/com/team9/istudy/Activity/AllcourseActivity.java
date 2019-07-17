package com.team9.istudy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

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

public class AllcourseActivity extends AppCompatActivity {
    private ListView lv;
    private List<CourseHeader> courseHeaderList =new ArrayList<CourseHeader>();
    private ExpandableListView expandableListView;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcourse);

        initCourse();
        CourseHeaderAdapter adapter=new CourseHeaderAdapter(AllcourseActivity.this,R.layout.courseheader_item, courseHeaderList);
        lv=findViewById(R.id.lv);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CourseHeader courseHeader = courseHeaderList.get(i);
                switch (i){
                    case 0:
                        Intent in1=new Intent(AllcourseActivity.this,JingpincourseActivity.class);
                        startActivity(in1);
                        break;
                    case 1:
                        Intent in2=new Intent(AllcourseActivity.this,FreecourseActivity.class);
                        startActivity(in2);
                        break;
                    case 2:
                        Intent in3=new Intent(AllcourseActivity.this,TuijianActivity.class);
                        startActivity(in3);
                        break;
                }
            }
        });

    }

    private void initCourse() {
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
        expandableListView=findViewById(R.id.netcourse);
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
        alertDialog=new AlertDialog.Builder(this)   //等待提示框
                .setMessage("loading...")
                .create();
        alertDialog.show();
        String jingpinURL="http://192.168.43.212:8080/maven-ssm-web/personController//getVideo";//推荐课程
        String uname=LoginActivity.username;
        HttpUtil.sendOkHttpRequest(jingpinURL, uname ,new Callback() {
            @Override
            public void onFailure( Call call,  IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(alertDialog!=null)
                            alertDialog.hide();
                        Toast.makeText(AllcourseActivity.this,"获取服务器信息失败，请检查网络状况",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse( Call call,  final Response response) throws IOException {
                String responseText = response.body().string();
                MyUtil.handleNetCourseResponse(responseText);
                runOnUiThread(new Runnable() {
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
    protected void onDestroy() {
        super.onDestroy();
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
    }
}
