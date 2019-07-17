package com.team9.istudy.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.team9.istudy.Gson.EnglishDefinition;
import com.team9.istudy.Gson.ExampleResult;
import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;
import com.team9.istudy.Util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EnglishSearchActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public CollapsingToolbarLayout collapsingToolbar;
    public ImageView roomImageView;
    public TextView translateContent,exampleContent;
    public EditText searchContent;
    public FloatingActionButton floatActionButton;
    public Button cet4Button,cet6Button;
    private AlertDialog alertDialog;
    public EnglishDefinition englishDefinition;
    public ExampleResult exampleResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_search);

        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        initComponent();//初始化控件

    }


    /**
     * 执行单词查询
     */
    public void doSearchWord(){
        if(searchContent.getText().toString() == null){
            searchContent.requestFocus();
            Toast.makeText(EnglishSearchActivity.this,"还没有输入单词哦",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            alertDialog=new AlertDialog.Builder(this)
                    .setMessage("正在查询单词...")
                    .setTitle("Tips")
                    .create();
            alertDialog.show();
            Log.e("查询1","111");
            Log.e("查询2","222");
            String searchWordURL = "https://api.shanbay.com/api/v1/bdc/search/?word="+searchContent.getText().toString();
            HttpUtil.sendOkHttpRequest(searchWordURL, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(EnglishSearchActivity.this,"网络开小差了，请稍后重试",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    Log.e("查询单词1"+searchContent.getText().toString(),responseText);
                    englishDefinition = Utility.handleEnglishDefinitionResponse(responseText);
                    Log.e("查询单词2"+searchContent.getText().toString(),englishDefinition.more .toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("查询单词3"+searchContent.getText().toString(),englishDefinition.more.id+"");
                            if(englishDefinition.more.id == 0){
                                Toast.makeText(EnglishSearchActivity.this,"没有查询到单词"+searchContent.getText().toString(),Toast.LENGTH_SHORT).show();
                            }
                            else{
                                doSearchExample();//查询翻译
                            }
                            translateContent.setText(englishDefinition.more.definition);
                            if(alertDialog!=null)
                                alertDialog.hide();
                        }
                    });
                }

            });
            Log.e("查询4","444");
        }
    }

    public void doSearchExample(){
        Log.e("查询3","333");
        String searchExampleURL = "https://api.shanbay.com/api/v1/bdc/example/?vocabulary_id="+englishDefinition.more.id;
        //String searchExampleURL = "https://api.shanbay.com/api/v1/bdc/example/?vocabulary_id=918";//+englishDefinition.more.id;
        HttpUtil.sendOkHttpRequest(searchExampleURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(EnglishSearchActivity.this,"网络开小差了，请稍后重试",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                exampleResult = Utility.handleEnglishExampleResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String exampleSentence = exampleResult.exampleDataList.get(0).annotation;
                        Log.e("查询3",exampleSentence);
                        exampleSentence = exampleSentence.replaceAll("<vocab>","");
                        exampleSentence = exampleSentence.replaceAll("</vocab>","");
                        exampleContent.setText(exampleSentence+"\n"+exampleResult.exampleDataList.get(0).translation);
                        if(alertDialog!=null)
                            alertDialog.hide();
                    }
                });
            }
        });
    }
    /**
     * 初始化控件
     */
    public void initComponent(){
        //获得控件对象
        toolbar = (Toolbar)findViewById(R.id.english_search_toolbar);
        collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.english_search_collapsing_toolbar);
        roomImageView = (ImageView)findViewById(R.id.english_search_image_view);

        floatActionButton = (FloatingActionButton) findViewById(R.id.english_search_floatButton) ;
        floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(EnglishSearchActivity.this,"你点击了搜索按钮！",Toast.LENGTH_SHORT).show();
                doSearchWord();
            }
        });
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("英语");
        // Glide.with(this).load("http://192.168.43.212:8080/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/webapps/maven-ssm-web/avatar/head1.jpg").into(roomImageView);
        Glide.with(this).load("http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg").into(roomImageView);
        searchContent = (EditText)findViewById(R.id.english_search_content);
        translateContent = (TextView)findViewById(R.id.english_search_content_translate);
        exampleContent = (TextView)findViewById(R.id.english_search_content_example);

        //cet4入口
        cet4Button = (Button)findViewById(R.id.english_search_CET4);
        cet4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnglishSearchActivity.this,EnglishCET4Activity.class));
            }
        });

        //cet6入口
        cet6Button = (Button)findViewById(R.id.english_search_CET6);
        cet6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnglishSearchActivity.this,EnglishCET6Activity.class));
            }
        });
    }
}
