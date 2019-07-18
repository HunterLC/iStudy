package com.team9.istudy.Util;

import android.util.Log;

import com.google.gson.Gson;
import com.team9.istudy.Gson.EnglishDefinition;
import com.team9.istudy.Gson.EnglishResult;
import com.team9.istudy.Gson.ExamArrangementResult;
import com.team9.istudy.Gson.ExampleResult;
import com.team9.istudy.Gson.KechengResult;
import com.team9.istudy.Gson.MaterialResult;
import com.team9.istudy.Gson.NoticeResult;
import com.team9.istudy.Gson.ScheduleResult;
import com.team9.istudy.Gson.ScoreResult;
import com.team9.istudy.Model.ExamArrangement;
import com.team9.istudy.Model.Kecheng;
import com.team9.istudy.Model.Material;
import com.team9.istudy.Model.MySubject;
import com.team9.istudy.Model.MyWord;
import com.team9.istudy.Model.Notice;
import com.team9.istudy.Model.Score;

import org.json.JSONArray;
import org.json.JSONException;
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

    public static EnglishResult handleWordListResponse(String response){
        try{
            Log.e("EnglishCET4Activity",response);
            JSONArray jsonArray = new JSONArray(response);
            EnglishResult englishResult = new EnglishResult();
            for(int i =0; i < jsonArray.length();i++){
                String content = jsonArray.getJSONObject(i).toString();
                MyWord myWord = new Gson().fromJson(content,MyWord.class);
                englishResult.myWordList.add(myWord);
            }
            return englishResult;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static EnglishDefinition handleEnglishDefinitionResponse(String response){
        try{
            Log.e("EnglishCET4Activity",response);
            JSONObject jsonObject = new JSONObject(response);
            String content = jsonObject.toString();
            return new Gson().fromJson(content,EnglishDefinition.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ExampleResult handleEnglishExampleResponse(String response){
        try{
            Log.e("EnglishActivity",response);
            JSONObject jsonObject = new JSONObject(response);
            String content = jsonObject.toString();
            return new Gson().fromJson(content,ExampleResult.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static NoticeResult handleMoticeListResponse(String response){
        try{
            JSONArray jsonArray = new JSONArray(response);
            NoticeResult scheduleResult = new NoticeResult();
            for(int i =0; i < jsonArray.length();i++){
                String content = jsonArray.getJSONObject(i).toString();
                Log.d("json值为=",content);
                Notice temp = new Gson().fromJson(content, Notice.class);
                scheduleResult.data.add(temp);
            }
            return scheduleResult;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将返回的json数据解析成为Material实体类
     * @return
     */
    public static Material handleMaterialResponse(){
        JSONArray jsonArray=new JSONArray();
        String content= null;
        try {
            content = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(content,Material.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static MaterialResult handleMaterialListResponse(String response){
        try{
            JSONArray jsonArray = new JSONArray(response);
            MaterialResult scheduleResult = new MaterialResult();
            for(int i =0; i < jsonArray.length();i++){
                String content = jsonArray.getJSONObject(i).toString();
                Log.d("json值为=",content);
                Material temp = new Gson().fromJson(content,Material.class);
                scheduleResult.data.add(temp);
            }
            return scheduleResult;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static ExamArrangementResult handleExamArrangementListResponse(String response){
        try{
            JSONArray jsonArray = new JSONArray(response);
            ExamArrangementResult scheduleResult = new ExamArrangementResult();
            for(int i =0; i < jsonArray.length();i++){
                String content = jsonArray.getJSONObject(i).toString();
                Log.d("json值为=",content);
                ExamArrangement temp = new Gson().fromJson(content,ExamArrangement.class);
                scheduleResult.data.add(temp);
            }
            return scheduleResult;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static ScoreResult handleScoreListResponse(String response){
        try{
            JSONArray jsonArray = new JSONArray(response);
            ScoreResult scheduleResult = new ScoreResult();
            for(int i =0; i < jsonArray.length();i++){
                String content = jsonArray.getJSONObject(i).toString();
                Log.d("json值为=",content);
                Score temp = new Gson().fromJson(content,Score.class);
                scheduleResult.data.add(temp);
            }
            return scheduleResult;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static KechengResult handleKechengListResponse(String response){
        try{
            JSONArray jsonArray = new JSONArray(response);
            KechengResult scheduleResult = new KechengResult();
            for(int i =0; i < jsonArray.length();i++){
                String content = jsonArray.getJSONObject(i).toString();
                Log.d("json值为=",content);
                Kecheng temp = new Gson().fromJson(content,Kecheng.class);
                scheduleResult.data.add(temp);
            }
            return scheduleResult;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
