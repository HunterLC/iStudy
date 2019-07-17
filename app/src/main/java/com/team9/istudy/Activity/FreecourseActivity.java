package com.team9.istudy.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.team9.istudy.Adapter.CourseAdapter;
import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;
import com.team9.istudy.Util.MyUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FreecourseActivity extends AppCompatActivity {
    private ListView lv;
    private ImageView rtn;
    private AlertDialog alertDialog;
//    private QMUITipDialog tipDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freecourse);

        rtn=findViewById(R.id.rtn);
        rtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        handler.sendEmptyMessage(0x123);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(CourseAdapter.courseList.size()>0) {
                CourseAdapter.courseList.clear();
                Log.e("courseList","clear");
            }
            requestData();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CourseAdapter courseAdapter =new CourseAdapter(FreecourseActivity.this, R.layout.course_item, CourseAdapter.courseList);
            lv=findViewById(R.id.courselist);
            lv.setAdapter(courseAdapter);
            courseAdapter.notifyDataSetChanged();
        }
    };

    public void requestData(){
        alertDialog=new AlertDialog.Builder(this)   //等待提示框
                .setMessage("loading...")
                .create();
        alertDialog.show();
        String jingpinURL="http://192.168.43.212:8080/maven-ssm-web/personController//getAllnetclass";//免费课程
        HttpUtil.sendOkHttpRequest(jingpinURL, new Callback() {
            @Override
            public void onFailure( Call call,  IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(alertDialog!=null)
                            alertDialog.hide();
                        Toast.makeText(FreecourseActivity.this,"获取服务器信息失败，请检查网络状况",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse( Call call,  final Response response) throws IOException {
                String responseText = response.body().string();
                MyUtil.handleCourseResponse(responseText);
                if(CourseAdapter.courseList ==null){
                    Log.e("Jingpin","空");
                }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
