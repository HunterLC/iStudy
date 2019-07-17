package com.team9.istudy.Activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    public static SharedPreferences loginSP;//保存登录信息，只能被本应用所访问
    public static String username;
    private String password;
    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private CardView cv;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.LaunchScreen);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_CUI);
        setContentView(R.layout.activity_login);

        initView();
        setListener();
    }

    private void initView() {
        //初始化，将状态栏和标题栏设为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);
    }

    private void setListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidate()){
                    Login();
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, fab, fab.getTransitionName());
                startActivity(new Intent(LoginActivity.this, PhoneConfirmActivity.class), options.toBundle());
            }
        });
    }
    @SuppressLint("RestrictedApi")
    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }

    public void Login(){
        username=et_username.getText().toString();
        password=et_password.getText().toString();
        String loginURL="http://192.168.43.212:8080/maven-ssm-web/personController/isLogin";
        HttpUtil.sendOkHttpRequest(loginURL, username,password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      Toast.makeText(LoginActivity.this,"获取服务器信息失败，请检查网络状况",Toast.LENGTH_SHORT).show();
                                  }
                              }
                );
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.isSuccessful()){  //服务器应答成功
                            try {
                                if(response.body().string().equals("ok")){
                                    Explode explode = new Explode();
                                    explode.setDuration(500);

                                    getWindow().setExitTransition(explode);
                                    getWindow().setEnterTransition(explode);
                                    ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
                                    Intent i1 = new Intent(LoginActivity.this, stu_main.class);
                                    startActivity(i1, oc2.toBundle());
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this,"账号或密码有误",Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"获取服务器信息失败，请检查网络状况",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public boolean isValidate(){
        if(et_username.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this,"请输入账号",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(et_password.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }
}
