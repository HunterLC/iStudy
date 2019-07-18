package com.team9.istudy.Model;

import com.google.gson.annotations.SerializedName;

public class Score {
    @SerializedName("classname")
    private String ScoreName;//课程名称
    @SerializedName("classnumber")
    private int ScoreCode;//课程编码
    @SerializedName("score")
    private int ScoreScore;//课程得分
    public Score(String scoreName, int scoreCode, int scoreScore) {
        ScoreName = scoreName;
        ScoreCode = scoreCode;
        ScoreScore = scoreScore;
    }
    public String getScoreName() {
        return ScoreName;
    }

    public void setScoreName(String scoreName) {
        ScoreName = scoreName;
    }

    public int getScoreCode() {
        return ScoreCode;
    }

    public void setScoreCode(int scoreCode) {
        ScoreCode = scoreCode;
    }

    public int getScoreScore() {
        return ScoreScore;
    }

    public void setScoreScore(int scoreScore) {
        ScoreScore = scoreScore;
    }

}
