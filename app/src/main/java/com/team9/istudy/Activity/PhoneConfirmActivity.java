package com.team9.istudy.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team9.istudy.R;
import com.team9.istudy.Util.CountDownTimerUtil;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class PhoneConfirmActivity extends AppCompatActivity {
    private String phonenum;
    private String code;
    private FloatingActionButton fab;
    private EditText phone;
    private EditText verification;
    private Button btn_verification;
    private Button btn_next;
    private CardView cv_verification;
    EventHandler eventHandler;
    private boolean flag=true;
    private int time=60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneconfirm);
        ShowEnterAnimation();
        initView();
        setListener();
        eventHandler=new EventHandler(){
            public void afterEvent(int event, int result, Object data) {
                Message msg=new Message();
                msg.arg1=event;
                msg.arg2=result;
                msg.obj=data;
                handler.sendMessage(msg);
            }
        };

        SMSSDK.registerEventHandler(eventHandler);
    }

    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event=msg.arg1;
            int result=msg.arg2;
            Object data=msg.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                if(result == SMSSDK.RESULT_COMPLETE) {
                    boolean smart = (Boolean)data;
                    if(smart) {
                        Toast.makeText(getApplicationContext(),"该手机号已经注册过，请重新输入", Toast.LENGTH_SHORT).show();
                        phone.requestFocus();
                        return;
                    }
                }
            }
            if(result==SMSSDK.RESULT_COMPLETE)
            {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Explode explode = new Explode();
                    explode.setDuration(500);

                    getWindow().setExitTransition(explode);
                    getWindow().setEnterTransition(explode);
                    ActivityOptionsCompat aoc = ActivityOptionsCompat.makeSceneTransitionAnimation(PhoneConfirmActivity.this);
                    Intent intent = new Intent(PhoneConfirmActivity.this, RegisterActivity.class);
                    intent.putExtra("username", phone.getText().toString());
                    startActivity(intent, aoc.toBundle());
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"验证码输入错误", Toast.LENGTH_SHORT).show();
                }
            }
//            else
//            {
//                if(flag)
//                {
//                    Toast.makeText(getApplicationContext(),"验证码获取失败请重新获取", Toast.LENGTH_LONG).show();
//                    verification.requestFocus();
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(),"验证码输入错误", Toast.LENGTH_LONG).show();
//                }
//            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    private void setListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
        btn_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonenum=phone.getText().toString();
                if(judgePhone()){
                    SMSSDK.getVerificationCode("86",phonenum);//获取验证码
                    verification.requestFocus();
                    CountDownTimerUtil cdtu=new CountDownTimerUtil(btn_verification,60000,1000);
                    cdtu.start();
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code=verification.getText().toString();
                if(judgeCode()){
                    SMSSDK.submitVerificationCode("86",phonenum,code);//验证码是否正确
                }
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

        fab = findViewById(R.id.fab);
        phone=findViewById(R.id.phone);
        verification=findViewById(R.id.verification);
        btn_verification=findViewById(R.id.btn_verification);
        btn_next=findViewById(R.id.btn_next);
        cv_verification = findViewById(R.id.cv_verification);
    }

    /**
     * 判断电话为空、位数、格式
     * @return
     */
    public boolean judgePhone( ){
        if(TextUtils.isEmpty(phonenum)){
            Toast.makeText(PhoneConfirmActivity.this,"请输入您的电话号码",Toast.LENGTH_SHORT).show();
            phone.requestFocus();
            return false;
        }
        else if(!isMatchLength(phonenum,11)){
            Toast.makeText(PhoneConfirmActivity.this,"请输入正确的电话号码",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!isMobileNO(phonenum)){
            Toast.makeText(PhoneConfirmActivity.this,"请输入正确的电话号码",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    /**
     * 判断验证码为空、位数
     * @return
     */
    public boolean judgeCode(){
        judgePhone();
        if(TextUtils.isEmpty(code)){
            Toast.makeText(PhoneConfirmActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
            phone.requestFocus();
            return false;
        }
        else if(!isMatchLength(code,4)){
            Toast.makeText(PhoneConfirmActivity.this,"请输入正确的验证码",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    /**
     * 判断一个字符串的位数
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        return str.length() == length ? true : false;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        String strTel = "[1][358]\\d{9}";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(strTel);
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cv_verification.setVisibility(View.GONE);
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
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cv_verification, cv_verification.getWidth()/2,0, fab.getWidth() / 2, cv_verification.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cv_verification.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cv_verification,cv_verification.getWidth()/2,0, cv_verification.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cv_verification.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                PhoneConfirmActivity.super.onBackPressed();
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
}
