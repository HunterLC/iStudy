package com.team9.istudy.Gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.team9.istudy.Model.MySubject;

import java.util.ArrayList;
import java.util.List;

public class ScheduleResult {
    public List<MySubject> data;
    @SerializedName("week")
    @Expose
    private String week;
    /**
     * 周几上
     */
    @SerializedName("day")
    @Expose private String day;

    @SerializedName("id")
    @Expose private String id;

    @SerializedName("username")
    @Expose private String username;

    /**
     * 教师
     */
    @SerializedName("teachername")
    @Expose private String teacher;

    /**
     * 开始上课的节次
     */
    @SerializedName("jnum")
    @Expose private String start;

    /**
     * 上课节数
     */
    @SerializedName("cnum")
    @Expose private String step;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    /**
     * 课程名
     */
    @SerializedName("classname")
    @Expose private String name;

    /**
     * 教室
     */
    @SerializedName("classaddress")
    @Expose private String room;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }


    public ScheduleResult(){
        this.data = new ArrayList<>();
    }
}
