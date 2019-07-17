package com.team9.istudy.Model;

import com.google.gson.annotations.SerializedName;

public class Notice {
    @SerializedName("noticesource")
    private String noticeSource;
    @SerializedName("noticetime")
    private String noticeTime;
    public Notice(String noticeSource, String noticeTime) {
        this.noticeSource = noticeSource;
        this.noticeTime = noticeTime;
    }
    public String getNoticeSource() {
        return noticeSource;
    }

    public void setNoticeSource(String noticeSource) {
        this.noticeSource = noticeSource;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }
}

