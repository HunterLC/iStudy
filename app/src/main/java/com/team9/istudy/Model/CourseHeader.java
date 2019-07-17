package com.team9.istudy.Model;

public class CourseHeader {
    private String course_name;
    private int course_icon;

    public CourseHeader(String course_name, int course_icon){
        this.course_icon=course_icon;
        this.course_name=course_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getCourse_icon() {
        return course_icon;
    }

    public void setCourse_icon(int course_icon) {
        this.course_icon = course_icon;
    }
}
