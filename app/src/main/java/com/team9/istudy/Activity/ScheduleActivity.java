package com.team9.istudy.Activity;

import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.team9.istudy.Gson.ScheduleResult;
import com.team9.istudy.Model.MySubject;
import com.team9.istudy.Model.SubjectRepertory;
import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;
import com.team9.istudy.Util.Utility;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.listener.OnItemBuildAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.view.WeekView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.team9.istudy.Model.SubjectRepertory.getWeekList;

public class ScheduleActivity extends AppCompatActivity  implements View.OnClickListener{
    //控件
    private TimetableView mTimetableView;
    private WeekView mWeekView;
    private LinearLayout layout;
    private TextView titleTextView;
    private List<MySubject> mySubjects = new ArrayList<>();
    //记录切换的周次，不一定是当前周
    private int target = -1;
    private AlertDialog alertDialog;
    private QMUITopBarLayout topbar;
    public static int CURRENT_SCHEDULE_DAY = 0;
    public static String CURRENT_SCHEDULE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        initTopBar();
        //获得控件
        titleTextView = (TextView) findViewById(R.id.schedule_textview);
        layout = (LinearLayout) findViewById(R.id.schedule_layout);
        layout.setOnClickListener(this);
        initTimetableView();
        requestData();
    }

    /**
     * 初始化TopBar
     */
    private void initTopBar(){
        QMUIStatusBarHelper.translucent(this); //状态栏透明
        topbar = (QMUITopBarLayout) findViewById(R.id.schedule_topbar);
        topbar.setTitle("我的课程表").setTextColor(ContextCompat.getColor(this,R.color.qmui_config_color_white));
        topbar.setBackgroundColor(ContextCompat.getColor(this,R.color.qmui_btn_blue_bg));
        topbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScheduleActivity.this,"cancel",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 2秒后刷新界面，模拟网络请求
     */
    private void requestData() {
        if(mySubjects != null)
            mySubjects = null;
        alertDialog=new AlertDialog.Builder(this)
                .setMessage("正在获取课表信息...")
                .setTitle("Tips").create();
        alertDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    String getCourseScheduleUrl = "http://192.168.43.212:8080/maven-ssm-web/infoController/getSchedule?username=15520715516";//查询课表api
                    //String getCourseScheduleUrl = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=18883892238";
                    String json = "test";
                    HttpUtil.sendOkHttpRequest(getCourseScheduleUrl, json, new Callback() {
                        //链接失败
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(alertDialog!=null)
                                        alertDialog.hide();
                                    Toast.makeText(ScheduleActivity.this, "获取课程信息失败,请检查网络连接或者稍后重试", Toast.LENGTH_SHORT).show();
                                }
                            });
                           // mySubjects = SubjectRepertory.loadDefaultSubjects();
                           // handler.sendEmptyMessage(0x123);
                        }

                        //链接成功
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //mySubjects = SubjectRepertory.loadDefaultSubjects();
                            //handler.sendEmptyMessage(0x123);

                            String responseText = response.body().string();//获得返回的数据
                            Log.e("ScheduleActivity",responseText);
                            ScheduleResult scheduleResult = Utility.handleSubjectListResponse(responseText);
                            Log.d("ScheduleActivity",new Gson().toJson(scheduleResult));
                            mySubjects = scheduleResult.data;
                            if(mySubjects==null)
                                Log.d("ScheduleActivity","空");
                            //处理UI更新不安全问题
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                            handler.sendEmptyMessage(0x123);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(alertDialog!=null)
                alertDialog.hide();
            mWeekView.source(mySubjects).showView();
            mTimetableView.source(mySubjects).showView();
        }
    };
    /**
     * 初始化课程控件
     */
    private void initTimetableView() {
        //获取控件
        mWeekView = (WeekView)findViewById(R.id.schedule_weekview);
        mTimetableView = (TimetableView)findViewById(R.id.schedule_timetableView);

        //设置周次选择属性
        mWeekView.curWeek(1)
                .callback(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        int cur = mTimetableView.curWeek();
                        //更新切换后的日期，从当前周cur->切换的周week
                        mTimetableView.onDateBuildListener().onUpdateDate(cur, week);
                        mTimetableView.changeWeekOnly(week);
                    }
                })
                .callback(new IWeekView.OnWeekLeftClickedListener() {
                    @Override
                    public void onWeekLeftClicked() {
                        onWeekLeftLayoutClicked();
                    }
                })
                .isShow(false)//设置隐藏，默认显示
                .showView();

        mTimetableView.curWeek(1)
                .curTerm("大三下学期")
                .callback(new ISchedule.OnItemClickListener() {
                    @Override

                    //响应课程表的单击事件
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        //display(scheduleList);
                        showBottomSheetList(scheduleList);
                        //
                    }

                })
                .callback(new ISchedule.OnItemLongClickListener() {
                    @Override
                    //响应课程表的长按操作
                    public void onLongClick(View v, int day, int start) {
                        Toast.makeText(ScheduleActivity.this, "长按:周" + day  + ",第" + start + "节", Toast.LENGTH_SHORT).show();
                    }
                })
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    //响应修改周次
                    public void onWeekChanged(int curWeek) {
                        titleTextView.setText("第" + curWeek + "周");
                    }
                })
                .callback(new OnItemBuildAdapter(){
                    @Override
                    public void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView, Schedule schedule, GradientDrawable gd) {
                        super.onItemUpdate(layout, textView, countTextView, schedule, gd);
                        if(schedule.getName().equals("【广告】")){
                           // Glide.with(ScheduleActivity.this).load(url).into(imageView);
                        }
                    }
                })
                .showView();
    }

    /**
     * 生成不同类型的BottomSheet
     */
    private void showBottomSheetList(final List<Schedule> scheduleList) {
        CURRENT_SCHEDULE_DAY = scheduleList.get(0).getDay();
        CURRENT_SCHEDULE_NAME = scheduleList.get(0).getName();
        new QMUIBottomSheet.BottomListSheetBuilder(ScheduleActivity.this)
                .setTitle("课程信息")
                .addItem(scheduleList.get(0).getName())
                .addItem(scheduleList.get(0).getRoom())
                .addItem(scheduleList.get(0).getTeacher()+"老师")
                .addItem("星期"+scheduleList.get(0).getDay())
                .addItem("第"+scheduleList.get(0).getStart()+"节开始")
                .addItem("持续"+scheduleList.get(0).getStep()+"节")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        dialog.dismiss();
                        String itemTitle = null;//项目标题名
                        String itemContent = null;//项目内容
                        switch(position){
                            case 0:
                                itemTitle = "课程名";
                                itemContent = scheduleList.get(0).getName();
                                showScheduleDialog(itemTitle,itemContent);
                                break;
                            case 1:
                                itemTitle = "教室地点";
                                itemContent = scheduleList.get(0).getRoom();
                                showScheduleDialog(itemTitle,itemContent);
                                break;
                            case 2:
                                itemTitle = "教师名称";
                                itemContent = scheduleList.get(0).getTeacher();
                                showScheduleDialog(itemTitle,itemContent);
                                break;
                            case 3:
                                itemTitle = "星期";
                                itemContent = ""+scheduleList.get(0).getDay();
                                showScheduleDialog(itemTitle,itemContent);
                                break;
                            case 4:
                                itemTitle = "开始节次";
                                itemContent = ""+scheduleList.get(0).getStart();
                                showScheduleDialog(itemTitle,itemContent);
                                break;
                            case 5:
                                itemTitle = "持续节次";
                                itemContent = ""+scheduleList.get(0).getStep();
                                showScheduleDialog(itemTitle,itemContent);
                                break;
                        }
                        //Toast.makeText(ScheduleActivity.this, "Item " + (position + 1), Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .show();
    }

    private void showScheduleDialog(final String itemTitle, String itemContent) {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(ScheduleActivity.this);
        builder.setTitle(itemTitle)
                .setPlaceholder(itemContent)
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        if (text != null && text.length() > 0) {
                            //Toast.makeText(ScheduleActivity.this, "数据为: " + text, Toast.LENGTH_SHORT).show();
                            for(MySubject item:mySubjects){
                                if(item.getDay() == CURRENT_SCHEDULE_DAY && item.getName().equals(CURRENT_SCHEDULE_NAME))
                                    switch (itemTitle){
                                        case "课程名":
                                            item.setName(""+text);
                                            break;
                                        case "教室地点":
                                            item.setRoom(""+text);
                                            break;
                                        case "教师名称":
                                            item.setTeacher(""+text);
                                            break;
                                        case "星期":
                                            item.setDay(Integer.valueOf(""+text));
                                            break;
                                        case "开始节次":
                                            item.setStart(Integer.valueOf(""+text));
                                            break;
                                        case "持续节次":
                                            item.setStep(Integer.valueOf(""+text));
                                            break;
                                    }
                            }
                            dialog.dismiss();
                            updateSchedule();
                        } else {
                            Toast.makeText(ScheduleActivity.this, "请输入数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }

    /**
     * 更新课表信息
     */
    public void updateSchedule(){
        List<ScheduleResult> updateSubjects = new ArrayList<>();
        for (MySubject item:mySubjects){ //遍历课程信息
            ScheduleResult scheduleResult = new ScheduleResult();
            scheduleResult.setDay(""+item.getDay());
            scheduleResult.setId(""+item.getId());
            scheduleResult.setName(item.getName());
            scheduleResult.setRoom(item.getRoom());
            scheduleResult.setStart(""+item.getStart());
            scheduleResult.setStep(""+item.getStep());
            scheduleResult.setTeacher(item.getTeacher());
            scheduleResult.setUsername(item.getUsername());
            scheduleResult.setWeek(item.getWeek());
            updateSubjects.add(scheduleResult);
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        String updateScheduleJson = gson.toJson(updateSubjects);
        Log.e("更新课表信息",updateScheduleJson);
        String updateCourseScheduleUrl = "http://192.168.43.212:8080/maven-ssm-web/infoController/updateSchedule";//查询课表api
        HttpUtil.sendJsonOkHttpRequest(updateCourseScheduleUrl, updateScheduleJson, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(ScheduleActivity.this,"更新失败，请重试...",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        requestData();
                    }
                });
            }
        });

    }

    /**
     * 更新,防止因程序在后台时间过长（超过一天）而导致的日期或高亮不准确问题
     */
    @Override
    protected void onStart() {
        super.onStart();
        mTimetableView.onDateBuildListener().onHighLight();
    }

    /**
     * 周次选择布局的左侧被点击时回调
     * 对话框修改当前周次
     */
    protected void onWeekLeftLayoutClicked() {
        final String items[] = new String[20];
        int itemCount = mWeekView.itemCount();
        for (int i = 0; i < itemCount; i++) {
            items[i] = "第" + (i + 1) + "周";
        }
        target = -1;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置当前周");
        builder.setSingleChoiceItems(items, mTimetableView.curWeek() - 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        target = i;
                    }
                });
        builder.setPositiveButton("设置为当前周", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (target != -1) {
                    mWeekView.curWeek(target + 1).updateView();
                    mTimetableView.changeWeekForce(target + 1);
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    /**
     * 显示内容
     *
     * @param beans
     */
    protected void display(List<Schedule> beans) {
        String str = "";
        for (Schedule bean : beans) {
            str += bean.getName() + ","+bean.getWeekList().toString()+","+bean.getStart()+","+bean.getStep()+"\n";
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.schedule_layout: //如果周次选择已经显示了，那么将它隐藏，更新课程、日期
                if (mWeekView.isShowing())
                    hideWeekView();
                else    //否则，显示
                    showWeekView();
                break;
        }
    }

    /**
     * 隐藏周次选择，此时需要将课表的日期恢复到本周并将课表切换到当前周
     */
    public void hideWeekView(){
        mWeekView.isShow(false);
        titleTextView.setTextColor(getResources().getColor(R.color.app_course_textcolor_blue));
        int cur = mTimetableView.curWeek();
        mTimetableView.onDateBuildListener().onUpdateDate(cur, cur);
        mTimetableView.changeWeekOnly(cur);
    }

    /**
     * 显示周次选择
     */
    public void showWeekView(){
        mWeekView.isShow(true);
        titleTextView.setTextColor(getResources().getColor(R.color.app_red));
    }
}
