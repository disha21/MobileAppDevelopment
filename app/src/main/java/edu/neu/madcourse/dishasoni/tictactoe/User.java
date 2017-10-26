package edu.neu.madcourse.dishasoni.tictactoe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dishasoni on 10/22/17.
 */

public class User {

    private String userName;
   // public List<Score> scores = new ArrayList<Score>();
   private Score scores ;

    private User() {}

    public User(String userName,Score scores){
        this.userName = userName;
        this.scores = scores;
      //  this.scores = scores;

    }
    public String getUserName() {
        return userName;
    }

    public Score getScores() {
        return scores;
    }
}
