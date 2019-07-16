package com.team9.istudy.Gson;

import com.google.gson.annotations.SerializedName;

public class ExampleData {

    @SerializedName("vocabulary_id")
    public int id;

    @SerializedName("translation")
    public String translation;

    @SerializedName("annotation")
    public String annotation;

}
