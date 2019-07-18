package com.team9.istudy.Activity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.team9.istudy.Adapter.KechengAdapter;
import com.team9.istudy.Gson.KechengResult;
import com.team9.istudy.Model.Kecheng;
import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;
import com.team9.istudy.Util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class KechengActivity extends AppCompatActivity {
    //ListView控件
    private ListView mList=null;
    //ListView数据源
    private List<Kecheng> data=new ArrayList<>();
    private KechengAdapter adapter=null;
    public Toolbar toolbar;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        toolbar = (Toolbar)findViewById(R.id.kecheng_toolbar);
        toolbar.setTitle("我的课程");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setBackgroundColor(0xFF008577);
        toolbar.setNavigationIcon(R.drawable.qmui_icon_topbar_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SIMYOU.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/
        requestData();
        mList = (ListView)findViewById(R.id.mList);
        adapter = new KechengAdapter(data);
        mList.setAdapter(adapter);

        //ListView item点击事件
        mList.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(KechengActivity.this,"我是item点击事件 i = " + i + "l = " + l,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void requestData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    String json = "15520715516";
                    String getMaterialUrl = "http://192.168.43.212:8080/maven-ssm-web/personController/getMyClass";//个人考试安排api
                    //String getCourseScheduleUrl = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=18883892238";
                    HttpUtil.sendOKHttpRequest1(getMaterialUrl, json, new Callback() {
                        //链接失败
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(KechengActivity.this, "获取课程信息失败,请检查网络连接或者稍后重试", Toast.LENGTH_SHORT).show();
                                    //defaultData();

                                }
                            });
                            //myMaterials = MaterialRepertory.loadDefaultSubjects();
                            //handler.sendEmptyMessage(0x123);
                        }
                        //链接成功
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseText = response.body().string();//获得返回的数据
                            Log.d("224",responseText);
                            KechengResult kechengResult = Utility.handleKechengListResponse(responseText);
                            Log.d("KechengActivity",new Gson().toJson(kechengResult));
                            data = kechengResult.data;
                            for(int i=0;i<data.size();i++)
                                Log.d("17313",data.get(i).getClassName());
                            //处理UI更新不安全问题
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                            handler.sendEmptyMessage(0x123);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.setData(data);

            mList.setAdapter(adapter);
            // courseView.setImageResource();
           /* mWeekView.source(mySubjects).showView();
            mTimetableView.source(mySubjects).showView();*/
        }
    };
}

