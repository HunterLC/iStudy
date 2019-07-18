package com.team9.istudy.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.team9.istudy.Activity.EnglishSearchActivity;
import com.team9.istudy.Activity.MaterialActivity;
import com.team9.istudy.Activity.ScheduleActivity;
import com.team9.istudy.Activity.stu_main;
import com.team9.istudy.Adapter.Image_adapter;
import com.team9.istudy.Adapter.NoticeAdapter;
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

public class Frag_main extends Fragment {


    private List<View> views;
    private LayoutInflater mInflater;
    private ViewPager mViewPager;
    //   private LinearLayout mLinearLayout;
    private List<ImageView> mPoints;
    private NoticeAdapter nAdapter=null;
    private ImageButton stu_main_image_resource,stu_main_image_sublist,stu_main_image_sign,stu_main_image_english,stu_main_image_more;

    private Button stu_main_resource,stu_main_sublist,stu_main_sign,stu_main_english,stu_main_more;
    private List<Notice> data=new ArrayList<>();
    private ListView mList=null;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_main, container, false);


        init(view);
        init_event(view);

        mList=view.findViewById(R.id.noticelist);
        requestData();
        nAdapter=new NoticeAdapter(data);
        if(mList!=null)
            Log.d("721","311300");
        if(nAdapter!=null)
            Log.d("723","411222");
        mList.setAdapter(nAdapter);
        nAdapter.notifyDataSetChanged();
        isRunning = true;
        handler.sendEmptyMessageDelayed(0, 2000);
        return view;
    }

    private void init_event(View view) {

        //资源
        stu_main_image_resource=view.findViewById(R.id.stu_main_image_resource);
        stu_main_resource=view.findViewById(R.id.stu_main_resource);

        stu_main_image_resource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MaterialActivity.class));
            }
        });
        stu_main_resource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MaterialActivity.class));
            }
        });

        //课表
        stu_main_image_sublist=view.findViewById(R.id.stu_main_image_sublist);
        stu_main_sublist=view.findViewById(R.id.stu_main_sublist);

        stu_main_image_sublist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ScheduleActivity.class));
            }
        });
        stu_main_sublist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ScheduleActivity.class));
            }
        });
        //签到
        stu_main_image_sign=view.findViewById(R.id.stu_main_image_sign);
        stu_main_sign=view.findViewById(R.id.stu_main_sign);
        stu_main_image_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        stu_main_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //英语
        stu_main_image_english=view.findViewById(R.id.stu_main_image_english);
        stu_main_english=view.findViewById(R.id.stu_main_english);

        stu_main_image_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EnglishSearchActivity.class));
            }
        });
        stu_main_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EnglishSearchActivity.class));
            }
        });
        //更多
        stu_main_image_more=view.findViewById(R.id.stu_main_image_more);
        stu_main_more=view.findViewById(R.id.stu_main_more);

        stu_main_image_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        stu_main_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private boolean isRunning = false;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            // 滑动到下一页
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);

            if(isRunning) {
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        };
    };

    private void init(View view) {


        // mLinearLayout= (LinearLayout) view.findViewById(R.id.linearlayout);
        mViewPager= (ViewPager) view.findViewById(R.id.viewpager);

        views=new ArrayList<View>();
        mInflater=getLayoutInflater();

        View v1=mInflater.inflate(R.layout.main_image_1,null);
        View v2=mInflater.inflate(R.layout.main_image_2,null);
        View v3=mInflater.inflate(R.layout.main_image_3,null);
        views.add(v1);
        views.add(v2);
        views.add(v3);
        mPoints=new ArrayList<>();
        for (int i=0;i<3;i++){
            ImageView iv=new ImageView(getActivity());
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            iv.setBackgroundResource(R.drawable.state);
            mPoints.add(iv);
            //mLinearLayout.addView(iv);
        }

        mPoints.get(0).setBackgroundResource(R.drawable.state1);
        Image_adapter i_adapter=new Image_adapter(views);
        mViewPager.setAdapter(i_adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (ImageView iv:mPoints){
                    iv.setBackgroundResource(R.drawable.state);
                }
                mPoints.get(position%views.size()).setBackgroundResource(R.drawable.state1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(Integer.MAX_VALUE/2-Integer.MAX_VALUE%views.size());


    }
    public void requestData(){
        String getMaterialUrl = "http://192.168.43.212:8080/maven-ssm-web/infoController/noticetable";//个人考试安排api
        //String getCourseScheduleUrl = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=18883892238";
        HttpUtil.sendOkHttpRequest(getMaterialUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                //myMaterials = MaterialRepertory.loadDefaultSubjects();
                //handler.sendEmptyMessage(0x123);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();//获得返回的数据
                Log.d("224",responseText);
                NoticeResult courseResult = Utility.handleMoticeListResponse(responseText);
                Log.d("CourseActivity",new Gson().toJson(courseResult));
                data.addAll(courseResult.data);
                for(int i=0;i<data.size();i++)
                    Log.d("17313",data.get(i).getNoticeSource());
                //处理UI更新不安全问题
                handler1.sendEmptyMessage(0x123);
            }
        });



    }
    @SuppressLint("HandlerLeak")
    Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            nAdapter.setData(data);
            mList.setAdapter(nAdapter);
            // courseView.setImageResource();
           /* mWeekView.source(mySubjects).showView();
            mTimetableView.source(mySubjects).showView();*/
        }
    };

}
