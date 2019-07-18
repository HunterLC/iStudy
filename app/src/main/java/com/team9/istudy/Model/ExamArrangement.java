package com.team9.istudy.Model;
import com.google.gson.annotations.SerializedName;
public class ExamArrangement {
    @SerializedName("classname")
    private String exam_name;
    @SerializedName("time")
    private String exam_time;
    @SerializedName("address")
    private String exam_place;
    public ExamArrangement(String exam_name, String exam_time, String exam_place) {
        this.exam_name = exam_name;
        this.exam_time = exam_time;
        this.exam_place = exam_place;
    }
    public ExamArrangement() {
        super();
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getExam_time() {
        return exam_time;
    }

    public void setExam_time(String exam_time) {
        this.exam_time = exam_time;
    }

    public String getExam_place() {
        return exam_place;
    }

    public void setExam_place(String exam_place) {
        this.exam_place = exam_place;
    }

}