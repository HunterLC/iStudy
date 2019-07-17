package com.team9.istudy.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team9.istudy.Activity.EnglishCET4Activity;
import com.team9.istudy.Activity.EnglishCET6Activity;
import com.team9.istudy.Adapter.EnglishCET4Adapter;
import com.team9.istudy.Adapter.EnglishCET6Adapter;
import com.team9.istudy.Model.MyWord;
import com.team9.istudy.R;

import java.util.ArrayList;
import java.util.List;

public class EnglishCET6Fragment extends Fragment {

    private List<MyWord> wordList = new ArrayList<>(); //单词信息集合
    private int currentPosition;
    private static String MARK_1 = "mark_1";// 设置标记
    private static String MARK_2 = "mark_2";// 设置标记
    private static String MARK_3 = "mark_3";// 设置标记
    private static String MARK_4 = "mark_4";// 设置标记
    private static String CURRENT_POSITION_1 = "position_1" ;// 设置标记
    private static String CURRENT_POSITION_2 = "position_2" ;// 设置标记
    private static String CURRENT_POSITION_3 = "position_3" ;// 设置标记
    private static String CURRENT_POSITION_4 = "position_4" ;// 设置标记

    private static String MARK = "mark";// 设置标记
    private static String CURRENT_POSITION = "position" ;// 设置标记
    public static EnglishCET6Fragment newInstance(int position)
    {
        //将fragment绑定参数
        Bundle bundle = new Bundle();
        switch (position){
            case 0:
                bundle.putInt(CURRENT_POSITION_1,position);
                break;
            case 1:
                bundle.putInt(CURRENT_POSITION_2,position);
                break;
            case 2:
                bundle.putInt(CURRENT_POSITION_3,position);
                break;
            case 3:
                bundle.putInt(CURRENT_POSITION_4,position);
                break;
        }
        bundle.putInt(CURRENT_POSITION,position);
        EnglishCET6Fragment fragment = new EnglishCET6Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_fragment, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.english_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            currentPosition = bundle.getInt(CURRENT_POSITION);
            switch (currentPosition){
                case 0:
                    wordList.addAll(EnglishCET6Activity.myWordList1); //得到数据
                    break;
                case 1:
                    wordList.addAll(EnglishCET6Activity.myWordList2);//得到数据
                    break;
                case 2:
                    wordList.addAll(EnglishCET6Activity.myWordList3);//得到数据
                    break;
                case 3:
                    wordList.addAll(EnglishCET6Activity.myWordList4);//得到数据
                    break;
            }
        }
        recyclerView.setAdapter(new EnglishCET6Adapter(recyclerView,wordList,currentPosition));
        recyclerView.getAdapter().notifyDataSetChanged();

        return rootView;
    }


}
