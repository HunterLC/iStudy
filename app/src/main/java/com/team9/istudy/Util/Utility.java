package com.team9.istudy.Util;

import com.google.gson.Gson;
import com.team9.istudy.Gson.ScheduleResult;
import com.team9.istudy.Model.MySubject;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.team9.istudy.Model.SubjectRepertory.getWeekList;

public class Utility {
    /**
     * 检查json中是否有BOM头，如果有则剔除utf-8的BOM头
     * @param in
     * @return
     */
    public static String JSONTokener(String in) {
        // consume an optional byte order mark (BOM) if it exists
        if (in != null && in.startsWith("\ufeff")) {    //剔除utf-8的BOM头
            in = in.substring(1);
        }
        return in;
    }

    /**
     * 将返回的json数据解析成为MySubject实体类
     * @param response
     * @return
     */
    public static MySubject handleSubjectResponse(String response){
        try{
            /*JSONObject jsonObject = new JSONObject(JSONTokener(response));
            JSONArray jsonArray = jsonObject.getJSONArray("ScheduleResult");*/
            JSONArray jsonArray = new JSONArray(response);
            String content = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(content,MySubject.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ScheduleResult handleSubjectListResponse(String response){
        try{
            JSONArray jsonArray = new JSONArray(response);
            ScheduleResult scheduleResult = new ScheduleResult();
            for(int i =0; i < jsonArray.length();i++){
                String content = jsonArray.getJSONObject(i).toString();
                MySubject mySubject = new Gson().fromJson(content,MySubject.class);
                mySubject.setWeekList(getWeekList(mySubject.getWeek()));
                scheduleResult.data.add(mySubject);
            }
            return scheduleResult;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}