package com.team9.istudy.Model;

import android.widget.SearchView;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Course {

    @SerializedName("teacher")
    public String course_author;

    @SerializedName("classdetail")
    public String course_inf;

    @SerializedName("cost")
    public int course_price;

    //@SerializedName("classimage")
    public String course_img;

    @SerializedName("videoName")
    public String course_title;

    public Course(){};

    public Course(String course_title, String course_img, String course_inf, String course_author, int course_price){
        this.course_title=course_title;
        this.course_img=course_img;
        this.course_inf=course_inf;
        this.course_author=course_author;
        this.course_price=course_price;
    }


    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getCourse_img() {
        return course_img;
    }

    public void setCourse_img(String course_img) {
        this.course_img = course_img;
    }

    public String getCourse_inf() {
        return course_inf;
    }

    public void setCourse_inf(String course_inf) {
        this.course_inf = course_inf;
    }

    public String getCourse_author() {
        return course_author;
    }

    public void setCourse_author(String course_author) {
        this.course_author = course_author;
    }

    public int getCourse_price() {
        return course_price;
    }

    public void setCourse_price(int course_price) {
        this.course_price = course_price;
    }
}
