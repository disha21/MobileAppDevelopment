package edu.neu.madcourse.dishasoni.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.neu.madcourse.dishasoni.R;
import edu.neu.madcourse.dishasoni.tictactoe.fcm.SendNotification;

public class LeadeBoardActivity extends AppCompatActivity {

    String userName = "";
    String token = "";
    Map<String, List<Score>> userScoreMapping = new HashMap<String, List<Score>>();
    int count = 0;
    String sender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leade_board);
//        Bundle bundle = getIntent().getExtras();
//        sender = bundle.getString("name", "");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/users/");

// Attach a listener to read the data at our posts reference

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    List<Score> userScoreList = new ArrayList<Score>();
                    for (DataSnapshot scoreSnapshot : userSnapshot.getChildren()) {
                        Score score = scoreSnapshot.getValue(Score.class);
                        userScoreList.add(score);
                    }
                    userScoreMapping.put(userSnapshot.getKey(), userScoreList);
                }
                createScoreTable();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });

    }

    protected void createScoreTable() {
        TableLayout userScores = (TableLayout) findViewById(R.id.table_leader_score_layout);
        Iterator it = userScoreMapping.entrySet().iterator();
        while (it.hasNext()) {
            count++;
            if (count < 11) {
                Map.Entry pair = (Map.Entry) it.next();
                userName = pair.getKey().toString();
                Score userMaxScore = findMaxUSerScore((List) pair.getValue());
                TableRow row = new TableRow(this);
                TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20, 20, 20, 20);
                layoutParams.weight = 1;
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                row.setLayoutParams(layoutParams);
                TextView tvUser = new TextView(this);
                TextView tv1 = new TextView(this);
                TextView tv2 = new TextView(this);
                TextView tv3 = new TextView(this);
                TextView tv4 = new TextView(this);
                ImageButton btn = new ImageButton(this);
                btn.setBackgroundResource(R.drawable.clap);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TableRow tablerow = (TableRow)v.getParent();
                        TextView sample = (TextView) tablerow.getChildAt(0);
                        String user=sample.getText().toString().trim();
                        SendMessage(user);



                    }


                });

                tvUser.setText(userName);
                tv1.setText(userMaxScore.getFinalScore().toString());
                tv2.setText(userMaxScore.getDate().substring(0, 10));
                tv3.setText(userMaxScore.getWord());
                tv4.setText(userMaxScore.getHighestWordScore());
                tvUser.setPadding(20, 20, 20, 20);
                tvUser.setGravity(Gravity.LEFT);
                tv1.setPadding(20, 20, 20, 20);
                tv1.setGravity(Gravity.LEFT);

                tv2.setPadding(20, 20, 20, 20);
                tv2.setGravity(Gravity.LEFT);

                tv3.setPadding(20, 20, 20, 20);
                tv3.setGravity(Gravity.LEFT);

                tv4.setPadding(20, 20, 20, 20);
                tv4.setGravity(Gravity.LEFT);

                row.addView(tvUser);
                row.addView(tv1);
                row.addView(tv2);
                row.addView(tv3);
                row.addView(tv4);
                row.addView(btn);

                userScores.addView(row);


            } else {
                break;
            }

        }
    }


    protected void SendMessage(String receiver) {
        final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference ref2 = database2.getReference("/registeredUsers/" + receiver);
        final SendNotification notify = new SendNotification();
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                        Str registerUser = dataSnapshot.getValue(RegisterUser.class);
//                        token  = registerUser.getRegistrationId();
                String token = (String)dataSnapshot.getValue();


                    SendNotification message = new SendNotification();
                    notify.sendToDevice(token);

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    protected Score findMaxUSerScore(List<Score> scoreList) {
        int maxScore = 0;
        int listIndex = 0;
        Score userMaxScore = new Score();

        for (int i = 0; i < scoreList.size(); i++) {
            int sc = scoreList.get(i).getFinalScore();
            if (sc > maxScore) {
                maxScore = sc;
                listIndex = i;
            }
            userMaxScore = new Score(scoreList.get(listIndex).getDate(),
                    scoreList.get(listIndex).getFinalScore(), scoreList.get(listIndex).getWord(), scoreList.get(listIndex).getHighestWordScore());


        }
        return userMaxScore;


    }
}
