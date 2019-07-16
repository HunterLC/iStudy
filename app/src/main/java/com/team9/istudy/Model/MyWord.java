package com.team9.istudy.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyWord implements Serializable {

    @SerializedName("id")
    private int id;  //单词id

    @SerializedName("type")
    private String wordType;  //词频类型

    @SerializedName("english")
    private String wordName; //单词名

    private String translate; //单词翻译
    private String example;  //单词例句


    public MyWord(String wordName,String translate,String example){
        this.wordName = wordName;
        this.translate = translate;
        this.example = example;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }


}
