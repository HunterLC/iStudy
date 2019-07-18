package com.team9.istudy.Model;
import com.google.gson.annotations.SerializedName;
public class Material {
    /*
       资料编号
     */
    private int mId;
    /*
    资料名称
     */
    @SerializedName("file_name")
    private String mName;
    /*
    教师名称
     */
    @SerializedName("upload_people")
    private String mTeacher;
    /*
    开始时间
     */
    @SerializedName("upload_time")
    private String mStartTime;
    /*
        资料种类
         */
    @SerializedName("college")
    private String mType;
    private int resId;
    public Material(int mId, String mName, String mTeacher, String mStartTime) {
        this.mId = mId;
        this.mName = mName;
        this.mTeacher = mTeacher;
        this.mStartTime = mStartTime;
    }

    public Material(String materialName, String teacherName, String startTime,String MaterialType) {
        this.mName=materialName;
        this.mTeacher=teacherName;
        this.mStartTime=startTime;
        this.mType=MaterialType;
    }

    public Material() {

    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmTeacher() {
        return mTeacher;
    }

    public void setmTeacher(String mTeacher) {
        this.mTeacher = mTeacher;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }
    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }
    public void setImageId(int resId) {
        this.resId  = resId;
    }

    public int getImageId() {
        return resId;
    }
    public String toString(){
        return this.getmName()+" "+this.getmTeacher()+"  "+this.getmStartTime()+this.getmType();
    }
}
