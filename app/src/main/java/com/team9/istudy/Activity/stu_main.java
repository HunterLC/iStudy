package com.team9.istudy.Activity;

import android.annotation.SuppressLint;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.Handler;
import com.google.gson.Gson;
import com.team9.istudy.Adapter.NoticeAdapter;
import com.team9.istudy.Adapter.Stu_adapter;
import com.team9.istudy.Fragment.Frag_account;
import com.team9.istudy.Fragment.Frag_allsub;
import com.team9.istudy.Fragment.Frag_main;
import com.team9.istudy.Fragment.Frag_mysub;
import com.team9.istudy.Gson.NoticeResult;
import com.team9.istudy.Model.Notice;
import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;
import com.team9.istudy.Util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class stu_main extends AppCompatActivity {



    RadioGroup mybutton;
    RadioButton stu_main_main,stu_main_allsub,stu_main_mysub,stu_main_account;
    ViewPager view_pager;
    Stu_adapter adapter;

    FragmentManager fm;

    Frag_main frag_main=new Frag_main();
    Frag_mysub frag_mysub=new Frag_mysub();
    Frag_allsub frag_allsub=new Frag_allsub();
    Frag_account frag_account=new Frag_account();
    List<Fragment> list=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //event();


    }





    private void init() {
        stu_main_main=findViewById(R.id.stu_main_main);
        stu_main_allsub=findViewById(R.id.stu_main_allsub);
        stu_main_mysub=findViewById(R.id.stu_main_mycsub);
        stu_main_account=findViewById(R.id.stu_main_account);
        mybutton=findViewById(R.id.mybutton);
        mybutton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.stu_main_main:
                        view_pager.setCurrentItem(0);
                        break;
                    case R.id.stu_main_allsub:
                        view_pager.setCurrentItem(1);
                        break;
                    case R.id.stu_main_mycsub:
                        view_pager.setCurrentItem(2);
                        break;
                    case R.id.stu_main_account:
                        view_pager.setCurrentItem(3);
                        break;
                }

            }
        });

        view_pager=findViewById(R.id.view_pager);
        fm=this.getSupportFragmentManager();

        list.add(frag_main);
        list.add(frag_allsub);
        list.add(frag_mysub);
        list.add(frag_account);

        adapter=new Stu_adapter(fm,list);
        view_pager.setAdapter(adapter);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        stu_main_main.setChecked(true);
                        break;
                    case 1:
                        stu_main_allsub.setChecked(true);
                        break;
                    case 2:
                        stu_main_mysub.setChecked(true);
                        break;
                    case 3:
                        stu_main_account.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        view_pager.setCurrentItem(0,false);
        view_pager.setOffscreenPageLimit(list.size());
    }

    @SuppressWarnings("deprecation")
    private void event() {



    }

}
