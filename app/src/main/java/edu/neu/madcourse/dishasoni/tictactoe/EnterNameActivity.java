package edu.neu.madcourse.dishasoni.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.neu.madcourse.dishasoni.AboutMe;
import edu.neu.madcourse.dishasoni.MainActivity;
import edu.neu.madcourse.dishasoni.R;

import static edu.neu.madcourse.dishasoni.R.id.playerName;

public class EnterNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);

    }

    public void SubmitName(View view) {
        EditText player = (EditText) findViewById(R.id.playerName);
        Intent in =  new Intent(EnterNameActivity.this,WordGameActivity.class);
        in.putExtra("name", player.getText().toString());
        startActivity(in);
    }
}
