package com.team9.istudy.Activity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.team9.istudy.Adapter.MaterialAdapter;
import com.team9.istudy.Gson.MaterialResult;
import com.team9.istudy.Model.Material;
import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;
import com.team9.istudy.Util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MaterialActivity extends AppCompatActivity implements OnChildClickListener {

    private ExpandableListView mListView = null;
    private MaterialAdapter mAdapter = null;
    private List<Material> myMaterials=new ArrayList<>();
    private AlertDialog alertDialog;
    private List<String> typelist=new ArrayList<String>();
    private List<List<Material>> mData = new ArrayList<List<Material>>();
    // public static String LOGIN_SUCCESS_TOKEN = null;  //全局使用的token

    /*private int[] mDetailIds = new int[]{
            R.array.tianlongbabu_detail,
            R.array.shediaoyingxiongzhuan_detail,
            R.array.shendiaoxialv_detail};
*/

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mListView = new ExpandableListView(this);
        mListView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        setContentView(mListView);

        mListView.setGroupIndicator(getResources().getDrawable(
                R.drawable.expander_floder));
        mAdapter = new MaterialAdapter(this, mData);
        mListView.setAdapter(mAdapter);
        mListView.setOnChildClickListener(this);
    }

    /*
     * ChildView 设置 布局很可能onChildClick进不来，要在 ChildView layout 里加上
     * android:descendantFocusability="blocksDescendants",
     * 还有isChildSelectable里返回true
     */
    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        // TODO Auto-generated method stub
        Material item = mAdapter.getChild(groupPosition, childPosition);
        new AlertDialog.Builder(this)
                .setTitle("你确定要下载吗？")
                .setIcon(android.R.drawable.ic_menu_more)
                .setPositiveButton("下载", new OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        Toast.makeText(MaterialActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();
                        //Intent intent=new Intent(MaterialActivity.this, MyDownloadActivity.class); startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.cancel,

                        new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // TODO Auto-generated method stub
                                Toast.makeText(MaterialActivity.this,"取消成功!",Toast.LENGTH_SHORT).show();

                            }

                        }
                ).create().show();

        return true;
    }
    public List<String> getTypelist() {
        return typelist;
    }

    public void setTypelist(List<String> typelist) {
        this.typelist = typelist;
    }
    private void defaultData(){
        myMaterials.add(new Material("考研数学","张宇","2019-03-22","数学"));
        myMaterials.add(new Material("学术英语","杨璐","2019-01-22","英语"));
        myMaterials.add(new Material("操作系统","胡小鹏","2019-06-11","计算机"));
        for(int i=0;i<myMaterials.size();i++){
            Log.d("331",myMaterials.get(i).toString());
            if(typelist.contains(myMaterials.get(i).getmType()))
                continue;
            typelist.add(myMaterials.get(i).getmType());
        }
        for(int i=0;i<typelist.size();i++) {
            List<Material> temp=new ArrayList<Material>();
            for (int j = 0; j < myMaterials.size(); j++)
                if(myMaterials.get(j).getmType().equals(typelist.get(i))){
                    Log.d("781",typelist.get(i));
                    temp.add(myMaterials.get(j));
                }

            mData.add(temp);
            for(int x=0;x<temp.size();x++)
                Log.d("值",temp.get(x).getmName());
        }
        mAdapter.setData(mData);
        mAdapter.setmGroupStrings(typelist);
        mListView.setAdapter(mAdapter);
        //myMaterials.add(new Material(materialName,tearcherName,startTime,materialType)));

    }
    private void initData() throws IOException {
        RequestMaterials();
        //defaultData();
        /*for (int i = 0; i < mGroupArrays.length; i++) {
            List<Item> list = new ArrayList<Item>();
            String[] childs = getStringArray(mGroupArrays[i]);
            String[] details = getStringArray(mDetailIds[i]);
            for (int j = 0; j < childs.length; j++) {
                Item item = new Item(mImageIds[i][j], childs[j], details[j]);
                list.add(item);
            }
            mData.add(list);
        }*/
        /*
        对资料进行分类
         */
        /*for(int i=0;i<myMaterials.size();i++){
            if(myMaterials.get(i).getmType().equals("数学"))
                mathMaterials.add(myMaterials.get(i));
            else if(myMaterials.get(i).getmType().equals("英语"))
                englishMaterials.add(myMaterials.get(i));
            else if(myMaterials.get(i).getmType().equals("计算机"))
                csMaterials.add(myMaterials.get(i));
        }
        mData.add(mathMaterials);
        mData.add(englishMaterials);
        mData.add(csMaterials);*/
        for(int i=0;i<typelist.size();i++)
            Log.d("typelist",typelist.get(i));
        for(int i=0;i<myMaterials.size();i++)
            Log.d("911",myMaterials.get(i).getmType());
        /*for(int i=0;i<typelist.size();i++) {
            List<Material> temp=new ArrayList<Material>();
            for (int j = 0; j < myMaterials.size(); j++)
                if(myMaterials.get(j).getmType().equals(typelist.get(i))){
                Log.d("781",typelist.get(i));
                    temp.add(myMaterials.get(j));
                }

            mData.add(temp);
            for(int x=0;x<temp.size();x++)
                Log.d("值",temp.get(x).getmName());
        }*/
    }

    private String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    private void RequestMaterials() throws IOException {//请求后台关于学习资料的信息
        alertDialog=new AlertDialog.Builder(this)
                .setMessage("正在获取资料信息...")
                .setTitle("Tips").create();
        alertDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    String getMaterialUrl = "http://192.168.43.212:8080/maven-ssm-web/infoController/getfile?sclass=all";//查询课表api
                    //String getCourseScheduleUrl = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=18883892238";
                    String json = "all";
                    HttpUtil.sendOkHttpRequestkang(getMaterialUrl, json, new Callback() {
                        //链接失败
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(alertDialog!=null)
                                        alertDialog.hide();
                                    Toast.makeText(MaterialActivity.this, "获取学习资料失败,请检查网络连接或者稍后重试", Toast.LENGTH_SHORT).show();
                                    //defaultData();

                                }
                            });
                            //myMaterials = MaterialRepertory.loadDefaultSubjects();
                            handler.sendEmptyMessage(0x123);
                        }

                        //链接成功
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseText = response.body().string();//获得返回的数据
                            Log.e("223",responseText);
                            //String responseText = json;

                            MaterialResult materialResult = Utility.handleMaterialListResponse(responseText);
                            Log.d("MaterialActivity",new Gson().toJson(materialResult));
                            myMaterials = materialResult.data;
                            for(int i=0;i<myMaterials.size();i++){
                                Log.d("331",myMaterials.get(i).toString());
                                if(typelist.contains(myMaterials.get(i).getmType()))
                                    continue;
                                typelist.add(myMaterials.get(i).getmType());
                            }

                            for(int i=0;i<myMaterials.size();i++)
                                Log.d("912",myMaterials.get(i).getmType());
                            for(int i=0;i<typelist.size();i++)
                                Log.d("241",typelist.get(i));
                            if(myMaterials==null)
                                Log.d("MaterialActivity","空");
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

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(alertDialog!=null)
                alertDialog.hide();
            for(int i=0;i<typelist.size();i++) {
                List<Material> temp=new ArrayList<Material>();
                for (int j = 0; j < myMaterials.size(); j++)
                    if(myMaterials.get(j).getmType().equals(typelist.get(i))){
                        Log.d("781",typelist.get(i));
                        temp.add(myMaterials.get(j));
                    }

                mData.add(temp);
                for(int x=0;x<temp.size();x++)
                    Log.d("值",temp.get(x).getmName());
            }
            mAdapter.setData(mData);
            mAdapter.setmGroupStrings(typelist);
            mListView.setAdapter(mAdapter);
            // courseView.setImageResource();
           /* mWeekView.source(mySubjects).showView();
            mTimetableView.source(mySubjects).showView();*/
        }
    };

}

