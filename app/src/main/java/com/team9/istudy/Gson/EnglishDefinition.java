package com.team9.istudy.Gson;

import com.google.gson.annotations.SerializedName;

public class EnglishDefinition {

    @SerializedName("msg")
    public String message;

    @SerializedName("status_code")
    public int status;

    @SerializedName("data")
    public MoreData more;

    public class MoreData{
        @SerializedName("id")
        public int id;
        @SerializedName("definition")
        public String definition;
    }

}
