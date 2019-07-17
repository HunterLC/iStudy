package com.team9.istudy.Util;

import com.google.gson.Gson;
import com.team9.istudy.Adapter.CourseAdapter;
import com.team9.istudy.Adapter.NetcourseAdapter;
import com.team9.istudy.Model.Course;

import org.json.JSONArray;
import org.json.JSONException;

public class MyUtil {
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

    public static void handleCourseResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                String content = jsonArray.getJSONObject(i).toString();
                Course course = new Gson().fromJson(content, Course.class);
                CourseAdapter.courseList.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void handleNetCourseResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                String content = jsonArray.getJSONObject(i).toString();
                Course course = new Gson().fromJson(content, Course.class);
                NetcourseAdapter.netcourselist.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
