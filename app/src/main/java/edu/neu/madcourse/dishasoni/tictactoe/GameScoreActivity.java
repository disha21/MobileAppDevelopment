package edu.neu.madcourse.dishasoni.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.neu.madcourse.dishasoni.MainActivity;
import edu.neu.madcourse.dishasoni.R;
import edu.neu.madcourse.dishasoni.tictactoe.fcm.FCMActivity;
import edu.neu.madcourse.dishasoni.tictactoe.fcm.SendNotification;

//import static edu.neu.madcourse.dishasoni.tictactoe.ControlFragment.countDownTimer;
import static android.R.attr.data;
import static android.content.ContentValues.TAG;
import static edu.neu.madcourse.dishasoni.tictactoe.ControlFragment.notFoundWords;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.SetScore1;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.SetScore10;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.SetScore2;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.SetScore3;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.SetScore4;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.SetScore5;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.SetScore8;
import static edu.neu.madcourse.dishasoni.tictactoe.GameActivity.totalScorePhase1;
import static edu.neu.madcourse.dishasoni.tictactoe.ControlFragment.foundWords;
import static edu.neu.madcourse.dishasoni.tictactoe.Stage1Game.selectedWords;

public class GameScoreActivity extends Activity  {
    static Map<String,Integer> wordScoreMap = new HashMap<String, Integer>();
    int score = 0;
    int wordScore = 0;
    public static MediaPlayer mMediaPlayer;
    private DatabaseReference mDatabase;
    String user = "";
    int finalScore = 0;
    View type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_score);
        setTitle("Game Ended");
        Bundle bundle = getIntent().getExtras();
        user = bundle.getString("name", "");
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        mDatabase = FirebaseDatabase.getInstance().getReference();
      //  Toast.makeText(getApplicationContext(), "Subscribed !", Toast.LENGTH_SHORT).show();
        finalScore = totalScorePhase1;
        int wrong = notFoundWords.size();
        finalScore = finalScore - wrong * 2;
        Log.d("finalScore:", String.valueOf(finalScore));
        String right = "Right words:"+"\n";
        String wrongWords = "Wrong words:" +"\n";
        Iterator<String> itRight = foundWords.iterator();
        while(itRight.hasNext()){
            String  val  = itRight.next();
            calculateWordScore(val);
            right += val+ "\n";
        }
        Iterator<String> itWrong = notFoundWords.iterator();
        while(itWrong.hasNext()){
            wrongWords += itWrong.next() + "\n";
        }
        saveToDatabase();

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
        ControlFragment.foundWords.clear();
        ControlFragment.notFoundWords.clear();
        ControlFragment.searchWordInFile.clear();
        totalScorePhase1 = 0;
        selectedWords  = new String[]{"", "", "", "", "", "", "", "", ""};
        this.finish();
        Intent intent = new Intent(GameScoreActivity.this, WordGameActivity.class);
        startActivity(intent);
    }

    public void calculateWordScore(String word){
        wordScore = 0;
        if (word.length() == 9) {
            score = 20;
            wordScore += score;
        }
        for (char ch: word.toCharArray()) {
            if (SetScore1.contains(ch)) {
                score = 1;
                wordScore += score;
            }
            if (SetScore2.contains(ch)) {
                score = 3;
                wordScore += score;
            }
            if (SetScore3.contains(ch)) {
                score = 3;
                wordScore += score;
            }
            if (SetScore4.contains(ch)) {
                score = 4;
                wordScore += score;
            }
            if (SetScore5.contains(ch)) {
                score = 5;
                wordScore += score;
            }
            if (SetScore8.contains(ch)) {
                score = 8;
                wordScore += score;
            }
            if (SetScore10.contains(ch)) {
                score = 10;
                wordScore += score;
            }
        }
        wordScoreMap.put(word,wordScore);

    }

    protected String findWordWithMaxScore(){
        String word ="";

        int maxValueInMap=(Collections.max(wordScoreMap.values()));
        for (Map.Entry<String, Integer> entry : wordScoreMap.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                word = entry.getKey();

            }
        }

        return (word + ":" + maxValueInMap);
    }

    protected void onPause() {
        super.onPause();
        ControlFragment.foundWords.clear();
        ControlFragment.notFoundWords.clear();
        ControlFragment.searchWordInFile.clear();
        totalScorePhase1 = 0;
        selectedWords  = new String[]{"", "", "", "", "", "", "", "", ""};
        this.finish();
        Intent intent = new Intent(this, WordGameActivity.class);
        startActivity(intent);

    }

    protected void saveToDatabase(){
        Log.e("in data", "in save data");
        String word = findWordWithMaxScore();
        String[] val  = word.split(":");
        String gameDate =  new java.util.Date().toString();
        Score score = new Score(gameDate,finalScore,val[1],val[0]);
//        List<Score> userScores = new ArrayList<Score>();
//        userScores.add(score);
        String username  = user.substring(7).toLowerCase();
        User userData = new User(username,score);
        mDatabase.child("users").child(userData.getUserName()).push().setValue(userData.getScores());
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
////                String value = dataSnapshot.getValue(String.class);
////                Log.d(TAG, "Value is: " + value);
//
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Log.d("new user added","new user added");
                User userVal = dataSnapshot.getValue(User.class);
                SendNotification notify  = new SendNotification();
                notify.sendNotification();
             //


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });



    }


}
