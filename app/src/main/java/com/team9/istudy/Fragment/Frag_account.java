package com.team9.istudy.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team9.istudy.Activity.AboutUs;
import com.team9.istudy.Activity.Balance;
import com.team9.istudy.Activity.InformationManagementActivity;
import com.team9.istudy.Activity.LoginActivity;
import com.team9.istudy.CustomControl.RoundImageView;
import com.team9.istudy.Model.User;
import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;
import com.team9.istudy.Util.Utility;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class Frag_account extends Fragment {

    private View view;

    LinearLayout layout_head;
    LinearLayout layout_aboutus;
    LinearLayout layout_balance;

    FileInputStream f;

    TextView tv_name;
    TextView tv_slogan;
    TextView tv_money;
    RoundImageView riv_headIma;
    User user;
    public Button accountExit;

    public static int getMoney() {
        return money;
    }

    String signAccount;
    static int money;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_account, container, false);
        layout_head = view.findViewById(R.id.aim_layout_head);
        layout_aboutus = view.findViewById(R.id.aim_layout_aboutus);
        layout_balance = view.findViewById(R.id.ain_layout_balance);

        tv_name = view.findViewById(R.id.aim_tv_nickname);
        tv_slogan = view.findViewById(R.id.aim_tv_slogan);
        tv_money = view.findViewById(R.id.aim_tv_money);
        riv_headIma = view.findViewById(R.id.aim_head_ima);

        accountExit = view.findViewById(R.id.account_exit);
        accountExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().apply();
                Intent intentLogin = new Intent(getContext(), LoginActivity.class);
                startActivity(intentLogin);
                getActivity().finish();
            }
        });


        initData(view);
        return view;
    }

    private void initData(View view) {
        if(signAccount == null)
            signAccount = "15520715516";
        String getEnglishListURL = "http://192.168.43.212:8080/maven-ssm-web/infoController/getMoney?" + "username=" + signAccount;
        HttpUtil.sendOkHttpRequest(getEnglishListURL, new Callback() {
            @Override
            public void onResponse(Call call,Response response) throws IOException {
                String responseText = response.body().string();//获得返回的数据
                money = Integer.valueOf(responseText);
                Log.e("",Integer.toString(money)+tv_money.getText());

                tv_money.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_money.setText(String.valueOf(money));
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(getActivity(), "网络出小差了~，请稍后再试", Toast.LENGTH_LONG).show();
            }
        });

        loadData();
    }

    private void loadImage() {
        String path = "/storage/emulated/0/Pictures/知乎/v2-eacdddaad052bd66ed87c76ff34f565a.jpg";
        try{
            if(InformationManagementActivity.getFile() != null){
                f = new FileInputStream(InformationManagementActivity.getFile());
            }
            else{
                f = new FileInputStream(path);
            }
            if(f == null){
                Toast.makeText(getActivity(), "读取失败！", Toast.LENGTH_SHORT).show();
                return;
            }
            Bitmap bm = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            BufferedInputStream bis = new BufferedInputStream(f);
            bm = BitmapFactory.decodeStream(bis, null, options);
            if(options.outHeight > 1000 && options.outWidth > 1000){
                options.inSampleSize = 8;//图片的长宽都是原来的1/8
            }
            riv_headIma.setImageBitmap(bm);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String getEnglishListURL = "http://192.168.43.212:8080/maven-ssm-web/personController/GetstuByUN?" + "username=" + signAccount;
                HttpUtil.sendOkHttpRequest(getEnglishListURL, new Callback() {
                    @Override
                    public void onResponse(Call call,Response response) throws IOException {
                        String responseText = response.body().string();//获得返回的数据
                        user = Utility.handleUserResponse(responseText);
                        Log.e("",user.getUserSlogan() + user.getUserNickName());

                        tv_name.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_name.setText(user.getUserNickName());
                            }
                        });

                        tv_slogan.post(new Runnable() {
                            @Override
                            public void run() {
                                tv_slogan.setText(user.getUserSlogan());
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        //Toast.makeText(getActivity(), "网络出小差了~，请稍后再试", Toast.LENGTH_LONG).show();
                    }
                });
            }

        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadImage();
        loadData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layout_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"布局点击",Toast.LENGTH_SHORT).show();
                Intent intoInfo = new Intent(getActivity(), InformationManagementActivity.class);
                startActivity(intoInfo);
            }
        });

        layout_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutInfo = new Intent(getActivity(), AboutUs.class);
                startActivity(aboutInfo);
            }
        });

        layout_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutInfo = new Intent(getActivity(), Balance.class);
                startActivity(aboutInfo);
            }
        });
    }
}
