package edu.neu.madcourse.dishasoni.tictactoe;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Iterator;

import edu.neu.madcourse.dishasoni.MainActivity;
import edu.neu.madcourse.dishasoni.R;

import static edu.neu.madcourse.dishasoni.tictactoe.ControlFragment.notFoundWords;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.totalScorePhase1;
import static edu.neu.madcourse.dishasoni.tictactoe.ControlFragment.foundWords;

public class GameScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_score);
        setTitle("Game Ended");
        int finalScore = totalScorePhase1;
        int wrong = notFoundWords.size();
        finalScore = finalScore - wrong * 2;
        Log.d("finalScore:", String.valueOf(finalScore));
        String right = "Right words:";
        String wrongWords = "Wrong words:";
        Iterator<String> itRight = foundWords.iterator();
        while(itRight.hasNext()){
            right += itRight.next() + "\n";
        }
        Iterator<String> itWrong = notFoundWords.iterator();
        while(itWrong.hasNext()){
            wrongWords += itWrong.next() + "\n";
        }

        String outputString = wrongWords + "\n" + right + "\n" + "Your Current Score :" + finalScore + "\n" + "\n" +
                "Score Rules :" + "\n" + "\n" +
                "Words Starting with" +
                "1 point: E, A, I, O, N, R, T, L, S" + "\n" +
                "2 points: D, G " + "\n" +
                "3 points: B, C, M, P " + "\n" +
                "4 points: F, H, V, W, Y " + "\n" +
                "5 points: K; 8 points: J, X; " + "\n" +
                "10 points: Q, Z" + "\n" +
                "20points: 9 letter word" + "\n"+
                "Wrong Word; -2";

        TextView out = (TextView)findViewById(R.id.output);
        out.setText(outputString.toString());



    }

    public void mainView(View view) {
        Intent intent = new Intent(GameScoreActivity.this, GameActivity.class);
        startActivity(intent);
    }


}
