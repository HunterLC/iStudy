package com.team9.istudy.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("nickname")
    @Expose private String userNickName;     //昵称

    @SerializedName("username")
    @Expose private String userAccount;    //账号

    @SerializedName("gender")
    @Expose private String userSex;         //性别

    @SerializedName("e_mail")
    @Expose private String userMail;        //邮箱

    @SerializedName("sign")
    @Expose private String userSlogan;      //个性签名

    @SerializedName("avatar")
    @Expose private String userImage;       //头像

    @SerializedName("id")
    private String id;              //id

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }


    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserSlogan() {
        return userSlogan;
    }

    public void setUserSlogan(String userSlogan) {
        this.userSlogan = userSlogan;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
