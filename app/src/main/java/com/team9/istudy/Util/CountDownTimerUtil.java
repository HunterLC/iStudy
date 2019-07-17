package com.team9.istudy.Util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

public class CountDownTimerUtil extends CountDownTimer {
    private Button btn_veri;

    public CountDownTimerUtil(Button btn_veri,long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.btn_veri=btn_veri;
    }

    @Override
    public void onTick(long l) {
        btn_veri.setClickable(false);
        btn_veri.setText(l/1000+"秒后可重新发送");
        btn_veri.setBackgroundColor(Color.parseColor("#cccccc"));
    }

    @Override
    public void onFinish() {
        btn_veri.setText("获取验证码");
        btn_veri.setClickable(true);//重新获得点击
        btn_veri.setBackgroundColor(Color.parseColor("#ffffff"));  //还原背景色
    }
}
