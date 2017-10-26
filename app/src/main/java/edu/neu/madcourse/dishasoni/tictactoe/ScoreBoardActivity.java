package edu.neu.madcourse.dishasoni.tictactoe;

import android.app.DownloadManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.madcourse.dishasoni.R;

public class ScoreBoardActivity extends AppCompatActivity {

    TextView username;
    List<Score> userScoreList = new ArrayList<Score>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("name", "");
        username = (TextView) findViewById(R.id.table_user);
        username.setText(user);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/users/" + user);

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot scoreSnapshot : dataSnapshot.getChildren()) {
                    Score score = scoreSnapshot.getValue(Score.class);
                    userScoreList.add(score);
                    Log.d("score", score.getDate());
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
        TableLayout userScores = (TableLayout) findViewById(R.id.table_user_score_layout);
        for (int i = 0; i < userScoreList.size(); i++) {
            TableRow row = new TableRow(this);
            TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 20, 20, 20);
            layoutParams.weight = 1;
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            row.setLayoutParams(layoutParams);
            TextView tv1 = new TextView(this);
            TextView tv2 = new TextView(this);
            TextView tv3 = new TextView(this);
            TextView tv4 = new TextView(this);
            tv1.setText(userScoreList.get(i).getFinalScore().toString());
            tv2.setText(userScoreList.get(i).getDate().substring(0, 10));
            tv3.setText(userScoreList.get(i).getWord());
            tv4.setText(userScoreList.get(i).getHighestWordScore());
            tv1.setPadding(20, 20, 20, 20);
            tv1.setGravity(Gravity.CENTER);

            tv2.setPadding(20, 20, 20, 20);
            tv2.setGravity(Gravity.CENTER);

            tv3.setPadding(20, 20, 20, 20);
            tv3.setGravity(Gravity.CENTER);

            tv4.setPadding(20, 20, 20, 20);
            tv4.setGravity(Gravity.CENTER);
            row.addView(tv1);
            row.addView(tv2);
            row.addView(tv3);
            row.addView(tv4);
            userScores.addView(row, i);

        }
    }
}
