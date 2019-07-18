package com.team9.istudy.Model;

import com.google.gson.annotations.SerializedName;

public class Kecheng {
    @SerializedName("classname")
    private String className;
    @SerializedName("teachername")
    private String tearcherName;
    public Kecheng(String className, String tearcherName) {
        this.className = className;
        this.tearcherName = tearcherName;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getTearcherName() {
        return tearcherName;
    }
    public void setTearcherName(String tearcherName) {
        this.tearcherName = tearcherName;
    }
}
