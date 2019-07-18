package com.team9.istudy.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.team9.istudy.Activity.ExamArrangementActivity;
import com.team9.istudy.Activity.KechengActivity;
import com.team9.istudy.Activity.MyClassActivity;
import com.team9.istudy.Activity.MyScoreActivity;
import com.team9.istudy.R;

public class Frag_mysub extends Fragment {

    private ImageView myClassView1,myClassView2,myClassView3,myClassView4,myClassView5;
    private TextView myTextView1,myTextView2,myTextView3,myTextView4,myTextView5;
    private ImageButton rightButton1,rightButton2,rightButton3,rightButton4,rightButton5;

    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_mysub, container, false);
        myClassView1=view.findViewById(R.id.myclass_image1);
        myClassView2=view.findViewById(R.id.myclass_image2);
        myClassView3=view.findViewById(R.id.myclass_image3);
        myClassView4=view.findViewById(R.id.myclass_image4);
        myClassView5=view.findViewById(R.id.myclass_image5);
        myTextView1=view.findViewById(R.id.myclass_function1);
        myTextView2=view.findViewById(R.id.myclass_function2);
        myTextView3=view.findViewById(R.id.myclass_function3);
        myTextView4=view.findViewById(R.id.myclass_function4);
        myTextView5=view.findViewById(R.id.myclass_function5);
        rightButton1=view.findViewById(R.id.myclass_righticon1);
        rightButton2=view.findViewById(R.id.myclass_righticon2);
        rightButton3=view.findViewById(R.id.myclass_righticon3);
        rightButton4=view.findViewById(R.id.myclass_righticon4);
        rightButton5=view.findViewById(R.id.myclass_righticon5);
        myClassView1.setImageResource(R.drawable.pinglun);
        myClassView2.setImageResource(R.drawable.kaoshi);
        myClassView3.setImageResource(R.drawable.chengji);
        myClassView4.setImageResource(R.drawable.jihua);
        myClassView5.setImageResource(R.drawable.mycourse);
        myTextView1.setText("消息中心");
        myTextView2.setText("考试安排");
        myTextView3.setText("我的成绩");
        myTextView4.setText("我的计划");
        myTextView5.setText("我的课程");
        final Intent intent2=new Intent(getContext(), ExamArrangementActivity.class);
        final Intent intent3=new Intent(getContext(), MyScoreActivity.class);
        final Intent intent5=new Intent(getContext(), KechengActivity.class);
        rightButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"you have clicked Button1",Toast.LENGTH_SHORT).show();
            }
        });
        rightButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent2);
                Toast.makeText(getContext(),"you have clicked Button2",Toast.LENGTH_SHORT).show();
            }
        });
        rightButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
                Toast.makeText(getContext(),"you have clicked Button3",Toast.LENGTH_SHORT).show();
            }
        });
        rightButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"you have clicked Button4",Toast.LENGTH_SHORT).show();
            }
        });
        rightButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent5);
                Toast.makeText(getContext(),"you have clicked Button5",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}