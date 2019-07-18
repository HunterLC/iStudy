package com.team9.istudy.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MaterialRepertory {
    /**
     * 对json字符串进行解析
     * @param parseString
     * @return
     */
    public static List<Material> parse(String parseString) throws JSONException {
        List<Material> materials = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(parseString);
            for(int i=0;i<array.length();i++){
                JSONArray array2=array.getJSONArray(i);
                String materialName=array2.getString(0);
                String tearcherName=array2.getString(1);
                String startTime=array2.getString(2);
                String materialType=array2.getString(3);
                materials.add(new Material(materialName,tearcherName,startTime,materialType));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return materials;
    }

    public static List<Integer> getWeekList(String weeksString){
        List<Integer> weekList=new ArrayList<>();
        if(weeksString==null||weeksString.length()==0) return weekList;

        weeksString=weeksString.replaceAll("[^\\d\\-\\,]", "");
        if(weeksString.indexOf(",")!=-1){
            String[] arr=weeksString.split(",");
            for(int i=0;i<arr.length;i++){
                weekList.addAll(getWeekList2(arr[i]));
            }
        }else{
            weekList.addAll(getWeekList2(weeksString));
        }
        return weekList;
    }

    public static List<Integer> getWeekList2(String weeksString){
        List<Integer> weekList=new ArrayList<>();
        int first=-1,end=-1,index=-1;
        if((index=weeksString.indexOf("-"))!=-1){
            first=Integer.parseInt(weeksString.substring(0,index));
            end=Integer.parseInt(weeksString.substring(index+1));
        }else{
            first=Integer.parseInt(weeksString);
            end=first;
        }
        for(int i=first;i<=end;i++)
            weekList.add(i);
        return weekList;
    }

}
