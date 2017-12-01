package edu.neu.madcourse.dishasoni.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.neu.madcourse.dishasoni.R;

public class ReceiveNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_notification);
    }


    public void GoLeader(View view) {
        Intent intent = new Intent(this, LeadeBoardActivity.class);
        startActivity(intent);
    }
}
