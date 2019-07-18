package com.team9.istudy.Activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.team9.istudy.Fragment.Frag_account;
import com.team9.istudy.R;

public class Balance extends AppCompatActivity {

    TextView tv_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_balance);

        tv_money =findViewById(R.id.balance_money);
        init();
    }

    private void init() {
        Log.e("",String.valueOf(Frag_account.getMoney()));
        tv_money.setText("￥ "+String.valueOf( Frag_account.getMoney()));
    }
}
