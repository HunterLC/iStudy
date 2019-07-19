package com.team9.istudy.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import com.team9.istudy.CustomControl.RoundImageView;
import com.team9.istudy.Model.User;
import com.team9.istudy.R;
import com.team9.istudy.Util.HttpUtil;
import com.team9.istudy.Util.Utility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InformationManagementActivity extends AppCompatActivity {

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    Button bn_ima_finish;
    Button bn_ima_change;
    EditText ed_ima_name;
    EditText ed_ima_sex;
    FileInputStream f;
    EditText ed_ima_mail;
    EditText ed_ima_slogan;
    RoundImageView riv_ima_headImage;
    User user;
    AlertDialog alertDialog;
    String result;
    String UserName;

    String filename;

    public static File getFile() {
        return file;
    }

    static File file;
    String ima_path;
    private static int RESULT_LOAD_IMAGE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_information_management);

        bn_ima_finish = findViewById(R.id.IMAbn_allFinish);
        bn_ima_change = findViewById(R.id.IMAbn_changeHeadImage);
        ed_ima_mail = findViewById(R.id.IMAET_mail);
        ed_ima_sex = findViewById(R.id.IMAET_sex);
        ed_ima_name = findViewById(R.id.IMAET_name);
        ed_ima_slogan = findViewById(R.id.IMAET_slogan);
        riv_ima_headImage = findViewById(R.id.IMA_RIV_headImage);

        initWindow();
        Log.e("测试", "页面创建！");
        event();
    }

    private void event() {
        bn_ima_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_ima_mail.getText().toString().isEmpty()) {
                    Toast.makeText(InformationManagementActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ed_ima_name.getText().toString().isEmpty()) {
                    Toast.makeText(InformationManagementActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ed_ima_sex.getText().toString().isEmpty()) {
                    Toast.makeText(InformationManagementActivity.this, "性别不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }


                Gson gson = new Gson();

                user.setUserMail(ed_ima_mail.getText().toString() );
                user.setUserNickName(ed_ima_name.getText().toString() );
                user.setUserSex(ed_ima_sex.getText().toString() );
                user.setUserSlogan(ed_ima_slogan.getText().toString() );
                List<User> users = new ArrayList<User>();
                users.add(user);

                String JsonObjcet = gson.toJson(users);
                Toast.makeText(InformationManagementActivity.this, JsonObjcet, Toast.LENGTH_LONG).show();

                HttpUtil.sendJsonOkHttpRequest("http://192.168.43.212:8080/maven-ssm-web/personController/updateInfo", JsonObjcet,
                        new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      Toast.makeText(InformationManagementActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                                  }
                                              }
                                );
                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(InformationManagementActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }
                        });
            }

        });

        bn_ima_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                //设定结果返回
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                Toast.makeText(InformationManagementActivity.this, "更换头像按钮点击", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            // 得到图片的全路径
            Uri uri = data.getData();
            Log.e("asd",handleImageOnKitKat(data));
            riv_ima_headImage.setImageURI(uri);
            file = new File(handleImageOnKitKat(data));

            sendStudentInfoToServer(file);
        }
    }

    private void sendStudentInfoToServer( File file) {
        //接口地址
        String urlAddress = "http://192.168.43.212:8080/maven-ssm-web/infoController/upload";
        if (file != null && file.exists()) {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("avatar", "avatar" + "_" + System.currentTimeMillis() + ".jpg",
                            RequestBody.create(MEDIA_TYPE_PNG, file));
            HttpUtil.sendMultipart(urlAddress, builder.build(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    Log.e("---", "onResponse: 成功上传图片之后服务器的返回数据：" + result);
                    //result就是图片服务器返回的图片地址。
                }
            });
        }
    }



    private void initWindow() {//初始化，将状态栏和标题栏设为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }


        SharedPreferences loginSP = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        UserName = loginSP.getString("account",null);
        //数据初始化
        ///storage/emulated/0/DCIM/Camera/IMG_20190709_151159.jpg
        //Uri initUri =  Uri.parse("content://com.miui.gallery.open/raw/%2Fstorage%2Femulated%2F0%2FDCIM%2FCamera%2FIMG_20190702_014838.jpg");
        //String realFilePath = handleImageOnKitKat(initUri);
        ///storage/emulated/0/Pictures/知乎/v2-eacdddaad052bd66ed87c76ff34f565a.jpg
//        Bitmap bitmap = getLoacalBitmap("/storage/emulated/0/DCIM/Camera/IMG_20190709_151159.jpg); //从本地取图片(在cdcard中获取");
        String path = "/storage/emulated/0/Pictures/知乎/v2-eacdddaad052bd66ed87c76ff34f565a.jpg";
        try{
            if(file != null){
                f = new FileInputStream(file);
            }
            else{
                f = new FileInputStream(path);
            }
            if(f == null){
                Toast.makeText(InformationManagementActivity.this, "读取失败！", Toast.LENGTH_SHORT).show();
            }
            Bitmap bm = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            BufferedInputStream bis = new BufferedInputStream(f);
            bm = BitmapFactory.decodeStream(bis, null, options);
            if(options.outHeight > 1000 && options.outWidth > 1000){
                options.inSampleSize = 8;//图片的长宽都是原来的1/8
                bm = rotateBimap(this, 90, bm);//旋转图片-90
            }
            riv_ima_headImage.setImageBitmap(bm);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        loadData();
    }

    private void loadData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String getEnglishListURL = "http://192.168.43.212:8080/maven-ssm-web/personController/GetstuByUN?"+"username=" + UserName;
                HttpUtil.sendOkHttpRequest(getEnglishListURL, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(alertDialog!=null)
                                    alertDialog.hide();
                                Toast.makeText(InformationManagementActivity.this,"服务器开小差了，稍后再试试？",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();//获得返回的数据
                        user = Utility.handleUserResponse(responseText);
                        if(user != null){
                            ed_ima_mail.setText(user.getUserMail());
                            ed_ima_name.setText(user.getUserNickName());
                            ed_ima_sex.setText(user.getUserSex());
                            ed_ima_slogan.setText(user.getUserSlogan());
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

    private String handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();

        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //Log.d(TAG, uri.toString());
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                //Log.d(TAG, uri.toString());
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //Log.d(TAG, "content: " + uri.toString());
            imagePath = getImagePath(uri, null);
        }
        return imagePath;
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 加载本地图片
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 旋转Bitmap图片
     *
     * @param context
     * @param degree 旋转的角度
     * @param srcBitmap 需要旋转的图片的Bitmap
     * @return
     */
    private Bitmap rotateBimap(Context context, float degree, Bitmap srcBitmap) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight()
                , matrix, true);
        return bitmap;
    }
}

