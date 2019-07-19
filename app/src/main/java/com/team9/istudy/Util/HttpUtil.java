package com.team9.istudy.Util;

import android.util.Log;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType LOGIN=MediaType.parse("text/plain;charset=utf-8");

    /**
     * 与服务器进行交互，发起一条HTTP请求只调用该方法，传入请求地址并注册一个回调来处理服务器响应
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendJsonOkHttpRequest(String address,String json,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(address).post(requestBody).build();
        Log.d("URL11111111",request.headers().toString());
        client.newCall(request).enqueue(callback);
    }

    public static void sendOKHttpRequest1(String address,String username,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder()
                .add("username",username)
                .build();
        Request request=new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 与服务器进行交互，发起一条HTTP请求只调用该方法，传入请求地址并注册一个回调来处理服务器响应
     * 登录
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequest(String address,String username,String password,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        //RequestBody requestBody = RequestBody.create(JSON,json);
        RequestBody requestBody = new FormBody.Builder()
                .add("username",username)
                .add("pswd",password)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 注册账号
     * @param address
     * @param username
     * @param id
     * @param nickname
     * @param email
     * @param gender
     * @param identity
     * @param password
     * @param callback
     */
    public static void sendOkHttpRequest(String address,String username,String id,String nickname,String email,String gender,String identity,String password,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        //RequestBody requestBody = RequestBody.create(JSON,json);
        RequestBody requestBody = new FormBody.Builder()
                .add("username",username)
                .add("pswd",password)
                .add("studentnumber",id)
                .add("nickname",nickname)
                .add("e_mail",email)
                .add("gender",gender)
                .add("identity",identity)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 根据账号请求推荐课程
     * @param address
     * @param username
     * @param callback
     */
    public static void sendOkHttpRequest(String address,String username,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("username",username)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequestkang(String address, String json, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendMultipart(String urlAddress, RequestBody requestBody, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().header("Authorization", "Client-ID " + "...").url(urlAddress).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

}
