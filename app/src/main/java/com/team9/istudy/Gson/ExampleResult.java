package com.team9.istudy.Gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleResult {

    @SerializedName("msg")
    public String message;

    @SerializedName("status_code")
    public int status;

    @SerializedName("data")
    public List<ExampleData> exampleDataList;
}
