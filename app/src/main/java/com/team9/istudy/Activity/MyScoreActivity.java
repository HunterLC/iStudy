package com.team9.istudy.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.team9.istudy.Adapter.ScoreAdapter;
import com.team9.istudy.Gson.ScoreResult;
import com.team9.istudy.Model.Score;
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

public class MyScoreActivity extends AppCompatActivity {
    private List<Score> list = new ArrayList<Score>();
    ListView tableListView=null;
    ScoreAdapter adapter=null;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);
        /*CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/biaozhunkaitijian.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/

        Toolbar toolbar=findViewById(R.id.score_toolbar);
        toolbar.setTitle("我的成绩");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setBackgroundColor(0xFF008577);
        toolbar.setNavigationIcon(R.drawable.qmui_icon_topbar_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置表格标题的背景颜色
        //ViewGroup tableTitle = (ViewGroup) findViewById(R.id.table_title1);
        //tableTitle.setBackgroundColor(Color.rgb(177, 173, 172));
        tableListView = (ListView) findViewById(R.id.list1);
        adapter = new ScoreAdapter(this, list);
        tableListView.setAdapter(adapter);
        requestData();
    }
    public void requestData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    SharedPreferences loginSP = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                    String json = loginSP.getString("account",null);
                    String getMaterialUrl = "http://192.168.43.212:8080/maven-ssm-web/infoController/scoretable";//我的成绩api
                    //String getCourseScheduleUrl = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=18883892238";
                    HttpUtil.sendOKHttpRequest1(getMaterialUrl, json, new Callback() {
                        //链接失败
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyScoreActivity.this, "获取学习资料失败,请检查网络连接或者稍后重试", Toast.LENGTH_SHORT).show();
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
                            Log.d("223",responseText);
                            ScoreResult scoreResult = Utility.handleScoreListResponse(responseText);
                            Log.d("MyScoreActivity",new Gson().toJson(scoreResult));
                            list = scoreResult.data;
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
            adapter.setList(list);
            tableListView.setAdapter(adapter);
            // courseView.setImageResource();
           /* mWeekView.source(mySubjects).showView();
            mTimetableView.source(mySubjects).showView();*/
        }
    };
}

