package edu.neu.madcourse.dishasoni.tictactoe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dishasoni on 10/22/17.
 */

public class User {

    private String userName;
    private Score scores ;
    private User() {}

    public void setScores(Score scores) {
        this.scores = scores;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User(String userName, Score scores){
        this.userName = userName;
        this.scores = scores;

    }

    public String getUserName() {
        return userName;
    }

    public Score getScores() {
        return scores;
    }
}
