package com.team9.istudy.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.team9.istudy.Fragment.EnglishCET4Fragment;
import com.team9.istudy.Fragment.EnglishCET6Fragment;
import com.team9.istudy.Model.MyWord;
import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;
import com.team9.istudy.Util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EnglishCET6Activity extends AppCompatActivity {
    private static final String[] TAB_TITLES = {"高频", "中频", "低频", "零频"};
    public static List<MyWord> myWordList1 = new ArrayList<>();
    public static List<MyWord> myWordList2 = new ArrayList<>();
    public static List<MyWord> myWordList3 = new ArrayList<>();
    public static List<MyWord> myWordList4 = new ArrayList<>();
    public Toolbar toolbar;
    private AlertDialog alertDialog;
    List<EnglishCET6Fragment> mFragment = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_cet6);
        ViewPager viewPager = (ViewPager) findViewById(R.id.english_cet6_view_pager);
        viewPager.setAdapter(new simpleAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.english_cet6_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        initData();
        initToolBar();
    }

    public void initData(){

        alertDialog=new AlertDialog.Builder(this)
                .setMessage("正在获取单词信息...")
                .setTitle("Tips")
                .create();
        alertDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String getEnglishListURL = "http://192.168.43.212:8080/maven-ssm-web/infoController/getCET6";
                HttpUtil.sendOkHttpRequest(getEnglishListURL, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(alertDialog!=null)
                                    alertDialog.hide();
                                Toast.makeText(EnglishCET6Activity.this,"服务器开小差了，稍后再试试？",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();//获得返回的数据
                        List<MyWord> englishResult = Utility.handleWordListResponse(responseText).getMyWordList();
                        if(englishResult==null)
                            Log.e("EnglishCET4Activity","空");
                        for(MyWord item:englishResult){
                            switch(item.getWordType()){
                                case "checkWord_list_high":
                                    myWordList1.add(item);
                                    break;
                                case "checkWord_list_middle":
                                    myWordList2.add(item);
                                    break;
                                case "checkWord_list_low":
                                    myWordList3.add(item);
                                    break;
                                case "checkWord_list_zero":
                                    myWordList4.add(item);
                                    break;
                            }
                        }
                        //处理UI更新不安全问题
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(alertDialog!=null)
                                    alertDialog.hide();
                            }
                        });
                    }
                });
            }
        }).start();

    }

    /**
     * 初始化状态栏
     */
    public void initToolBar(){
        toolbar = (Toolbar)findViewById(R.id.english_cet6_toolbar);
        toolbar.setTitle("六级词汇");
        toolbar.setNavigationIcon(R.drawable.qmui_icon_topbar_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class simpleAdapter extends FragmentPagerAdapter {
        public simpleAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return TAB_TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            EnglishCET6Fragment fragment = null;
            if(mFragment.size() > position){
                fragment = mFragment.get(position);
                if( fragment != null)
                    return fragment;
            }
            if(mFragment.size() <= position)
                mFragment.add(null);
            fragment = EnglishCET6Fragment.newInstance(position);
            mFragment.set(position,fragment);
            return fragment;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLES[position];
        }
    }
}
