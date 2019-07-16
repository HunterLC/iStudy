package com.team9.istudy.Activity;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.team9.istudy.R;

public class EnglishSearchActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public CollapsingToolbarLayout collapsingToolbar;
    public ImageView roomImageView;
    public TextView roomContentText;
    public FloatingActionButton floatActionButton;
    public QMUIGroupListView mGroupListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_search);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        //获得控件对象
        toolbar = (Toolbar)findViewById(R.id.english_search_toolbar);
        collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.english_search_collapsing_toolbar);
        roomImageView = (ImageView)findViewById(R.id.english_search_image_view);
        roomContentText = (TextView)findViewById(R.id.english_search_content_text);

        floatActionButton = (FloatingActionButton) findViewById(R.id.english_search_floatButton) ;
        floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EnglishSearchActivity.this,"你点击了搜索按钮！",Toast.LENGTH_SHORT).show();
            }
        });
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("英语");
        Glide.with(this).load(R.drawable.ic_launcher).into(roomImageView);
        mGroupListView=findViewById(R.id.english_search_groupListView);
        QMUICommonListItemView normalItem = mGroupListView.createItemView("Item 1");
        normalItem.setOrientation(QMUICommonListItemView.VERTICAL); //默认文字在左边

        QMUICommonListItemView itemWithDetail = mGroupListView.createItemView("Item 2");
        itemWithDetail.setDetailText("在右方的详细信息");//默认文字在左边   描述文字在右边
        QMUICommonListItemView itemWithChevron = mGroupListView.createItemView("Item 4");
        itemWithChevron.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);//默认文字在左边   右侧更多按钮
    }
}
