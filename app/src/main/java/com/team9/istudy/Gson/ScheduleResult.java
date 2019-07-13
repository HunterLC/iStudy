package com.team9.istudy.Gson;

import com.team9.istudy.Model.MySubject;

import java.util.ArrayList;
import java.util.List;

public class ScheduleResult {
    public List<MySubject> data;
    public ScheduleResult(){
        this.data = new ArrayList<>();
    }
}
