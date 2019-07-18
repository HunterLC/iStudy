package com.team9.istudy.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.team9.istudy.Adapter.ExamArrangementAdapter;
import com.team9.istudy.Gson.ExamArrangementResult;
import com.team9.istudy.Model.ExamArrangement;
import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;
import com.team9.istudy.Util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ExamArrangementActivity extends AppCompatActivity {

    private ListView mListView = null;
    private ExamArrangementAdapter mAdapter = null;
    private List<ExamArrangement> myExams=new ArrayList<>();
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eaxm_arrangement);
       /* CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/biaozhunkaitijian.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/
        //设置表格标题的背景颜色
        ViewGroup tableTitle = (ViewGroup) findViewById(R.id.table_title);
        tableTitle.setBackgroundColor(Color.rgb(177, 173, 172));
        List<ExamArrangement> list = new ArrayList<ExamArrangement>();
        /*list.add(new ExamArrangement("高等数学","2019-03-22","X1231"));
        list.add(new ExamArrangement("英语","2019-03-22","X2312"));*/
        requestData();
        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new ExamArrangementAdapter(this, list);
        mListView.setAdapter(mAdapter);
    }
    public void requestData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    String json = "15520715516";
                    String getMaterialUrl = "http://192.168.43.212:8080/maven-ssm-web/infoController/" +
                            "exam_arrangement";//个人考试安排api
                    //String getCourseScheduleUrl = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=18883892238";
                    HttpUtil.sendOKHttpRequest1(getMaterialUrl, json, new Callback() {
                        //链接失败
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ExamArrangementActivity.this, "获取学习资料失败,请检查网络连接或者稍后重试", Toast.LENGTH_SHORT).show();
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
                            ExamArrangementResult examResult = Utility.handleExamArrangementListResponse(responseText);
                            Log.d("ExamArrangementActivity",new Gson().toJson(examResult));
                            myExams = examResult.data;
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
            mAdapter.setList(myExams);
            mListView.setAdapter(mAdapter);
        }
    };
}



