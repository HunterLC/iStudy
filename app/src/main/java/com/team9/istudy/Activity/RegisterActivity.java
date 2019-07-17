package com.team9.istudy.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private String username;
    private EditText password;
    private EditText repeat_password;
    private EditText email;
    private EditText nickname;
    private EditText ID;
    private RadioGroup identity;
    private RadioGroup gender;
    private RadioButton sex;
    private RadioButton iden;
    private Button btn_register;
    private CardView cv_Add;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        ShowEnterAnimation();
        initView();
        setListener();
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cv_Add.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cv_Add, cv_Add.getWidth()/2,0, fab.getWidth() / 2, cv_Add.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cv_Add.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cv_Add,cv_Add.getWidth()/2,0, cv_Add.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cv_Add.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    private void setListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateRevealClose();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
    }

    private void Register() {
        String nick=nickname.getText().toString();
        String em=email.getText().toString();
        String se=sex.getText().toString();
        String ide=iden.getText().toString();
        String pswd=password.getText().toString();
        String rp_pswd=repeat_password.getText().toString();
        String id=ID.getText().toString();
        String registerURL="http://192.168.43.212:8080/maven-ssm-web/personController/addstudent";
        HttpUtil.sendOkHttpRequest(registerURL,username, nick,id,em, se, ide, pswd, new Callback() {
            @Override
            public void onFailure( Call call,  IOException e) {
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      Toast.makeText(RegisterActivity.this,"获取服务器信息失败，请检查网络状况",Toast.LENGTH_SHORT).show(); }
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
                                if(response.body().string().equals("ok")) {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    RegisterActivity.super.onBackPressed();
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this, "该号码已注册", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"获取服务器信息失败，请检查网络状况",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
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

        nickname=findViewById(R.id.nickname);
        ID=findViewById(R.id.ID);
        email=findViewById(R.id.email);
        gender=findViewById(R.id.gender);
        identity=findViewById(R.id.occupation);
        sex=findViewById(gender.getCheckedRadioButtonId());
        iden=findViewById(identity.getCheckedRadioButtonId());
        password=findViewById(R.id.password);
        repeat_password=findViewById(R.id.repeat_password);
        btn_register=findViewById(R.id.btn_register);
        fab=findViewById(R.id.fab);
        cv_Add=findViewById(R.id.cv_add);
    }

    public boolean isValidate(){
        if(nickname.equals("")){
            Toast.makeText(RegisterActivity.this,"请输入昵称",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(email.equals("")){
            Toast.makeText(RegisterActivity.this,"请输入邮箱",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.equals("")){
            Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(password.length()<6){
            Toast.makeText(RegisterActivity.this,"密码至少6位",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!password.equals(repeat_password)){
            Toast.makeText(RegisterActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
