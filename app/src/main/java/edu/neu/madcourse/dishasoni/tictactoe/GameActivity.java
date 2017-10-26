/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
 ***/
package edu.neu.madcourse.dishasoni.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import edu.neu.madcourse.dishasoni.R;
//import static edu.neu.madcourse.dishasoni.tictactoe.GameFragment.selectedWords;
import static edu.neu.madcourse.dishasoni.tictactoe.ControlFragment.countDownTimer;
import static edu.neu.madcourse.dishasoni.tictactoe.ControlFragment.foundWords;






public class GameActivity extends Activity {

    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    public static final String USER = "user";
    public static MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private GameFragment mGameFragment;
    private static ControlFragment controlFragment;
    public static Vibrator vibrator;
    static int score = 0;
    static int totalScorePhase1 = 0;
    static HashSet<Character> SetScore1 = new HashSet<Character>();
    static HashSet<Character> SetScore2 = new HashSet<Character>();
    static HashSet<Character> SetScore3 = new HashSet<Character>();
    static HashSet<Character> SetScore4 = new HashSet<Character>();
    static HashSet<Character> SetScore5 = new HashSet<Character>();
    static HashSet<Character> SetScore8 = new HashSet<Character>();
    static HashSet<Character> SetScore10 = new HashSet<Character>();
    long time_remaining = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddToSets();
        setContentView(R.layout.activity_game);
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("name", "");
        TextView tv = (TextView) findViewById(R.id.username);
        if(user != "")
            tv.setText("Welcome" + user);
        else
            tv.setText("Welcome Guest");
        mGameFragment = (GameFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_game);
        controlFragment =  (ControlFragment)getFragmentManager()
                .findFragmentById(R.id.fragment_game_controls);
        controlFragment.setGameFragement(mGameFragment);
       // controlFragment.setGameActivity(gameActivity);
        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
            if (gameData != null) {
                mGameFragment.putState(gameData);
            }
        }
        Log.d("UT3", "restore = " + restore);
    }

    public void AddToSets() {
        SetScore10.add('q');
        SetScore10.add('z');
        SetScore8.add('j');
        SetScore8.add('x');
        SetScore5.add('k');
        SetScore4.add('f');
        SetScore4.add('h');
        SetScore4.add('v');
        SetScore4.add('w');
        SetScore4.add('y');

        SetScore3.add('b');
        SetScore3.add('c');
        SetScore3.add('m');
        SetScore3.add('p');

        SetScore2.add('d');
        SetScore2.add('g');

        SetScore1.add('a');
        SetScore1.add('e');
        SetScore1.add('i');
        SetScore1.add('o');
        SetScore1.add('n');
        SetScore1.add('r');
        SetScore1.add('t');
        SetScore1.add('l');
        SetScore1.add('s');


    }


    public void restartGame() {
        mGameFragment.restartGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer = MediaPlayer.create(this, R.raw.asound_world);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(null);
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        countDownTimer.cancel();
        String gameData = mGameFragment.getState();
        getPreferences(MODE_PRIVATE).edit()
                .putString(PREF_RESTORE, gameData)
                .commit();

        Log.d("UT3", "state = " + gameData);
    }


    public static int calculateScore() {
        totalScorePhase1 = 0;
        for (String word : foundWords) {
            word = word.toLowerCase();
            if (word.length() == 9) {
                score = 20;
                totalScorePhase1 += score;
            }
            for (char ch: word.toCharArray()) {
                if (SetScore1.contains(ch)) {
                    score = 1;
                    totalScorePhase1 += score;
                }
                if (SetScore2.contains(ch)) {
                    score = 3;
                    totalScorePhase1 += score;
                }
                if (SetScore3.contains(ch)) {
                    score = 3;
                    totalScorePhase1 += score;
                }
                if (SetScore4.contains(ch)) {
                    score = 4;
                    totalScorePhase1 += score;
                }
                if (SetScore5.contains(ch)) {
                    score = 5;
                    totalScorePhase1 += score;
                }
                if (SetScore8.contains(ch)) {
                    score = 8;
                    totalScorePhase1 += score;
                }
                if (SetScore10.contains(ch)) {
                    score = 10;
                    totalScorePhase1 += score;
                }
            }
        }
        return totalScorePhase1;

    }
}


