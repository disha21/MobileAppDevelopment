package edu.neu.madcourse.dishasoni.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.neu.madcourse.dishasoni.R;

public class Phase2Activity extends AppCompatActivity {
    String[] wordsForPhase2 = new String[]{"q","a","b","n","m","r","o","l","u"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phase2);
        setTitle("Welcome to Word Game Phase 2");
    }
}
