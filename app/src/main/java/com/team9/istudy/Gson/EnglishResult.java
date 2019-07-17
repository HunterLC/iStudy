package com.team9.istudy.Gson;

import com.google.gson.annotations.SerializedName;
import com.team9.istudy.Model.MyWord;

import java.util.ArrayList;
import java.util.List;

public class EnglishResult {
    @SerializedName("id")
    private int id;  //单词id

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

    public List<MyWord> getMyWordList() {
        return myWordList;
    }

    public void setMyWordList(List<MyWord> myWordList) {
        this.myWordList = myWordList;
    }

    @SerializedName("type")
    private String wordType;  //词频类型

    @SerializedName("english")
    private String wordName; //单词名

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

    @SerializedName("translate")
    private String translate; //单词翻译

    @SerializedName("example")
    private String example;  //单词例句

    public List<MyWord> myWordList;

    public EnglishResult(){
        myWordList = new ArrayList<>();
    }
}
