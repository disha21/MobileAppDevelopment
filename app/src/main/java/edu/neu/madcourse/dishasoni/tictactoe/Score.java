package edu.neu.madcourse.dishasoni.tictactoe;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by dishasoni on 10/22/17.
 */

public class Score implements Comparator<Score>{
    private String date;
    private Integer finalScore;
    private String word;
    private String highestWordScore;

    public Score(){}

    public Score(String date, Integer finalScore,  String highestWordScore,String word){
        this.date = date;
        this.finalScore = finalScore;
        this.word =  word;
        this.highestWordScore = highestWordScore;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public void setFinalScore(Integer finalScore) {
        this.finalScore = finalScore;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setHighestWordScore(String highestWordScore) {
        this.highestWordScore = highestWordScore;
    }




    public String getDate() {
        return date;
    }

    public Integer getFinalScore() {
        return finalScore;
    }

    public String getWord() {
        return word;
    }

    public String getHighestWordScore() {
        return highestWordScore;
    }

    @Override
    public int compare(Score score1, Score score2) {

       return (score2.getFinalScore() - score1.getFinalScore());

    }



}
