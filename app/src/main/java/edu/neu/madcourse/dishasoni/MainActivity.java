package edu.neu.madcourse.dishasoni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.neu.madcourse.dishasoni.tictactoe.EnterNameActivity;
import edu.neu.madcourse.dishasoni.tictactoe.WordGameActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Disha Soni");
    }

    public void AboutMe(View view) {
        Intent in =  new Intent(MainActivity.this,AboutMe.class);
        startActivity(in);
    }
    public void GenerateError(View view) {
        throw new RuntimeException("Crash!");
    }

    public void DictionaryActivity(View view) {
        Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
        startActivity(intent);
    }
    public void ScroggleActivity(View view) {
        Intent intent = new Intent(MainActivity.this, WordGameActivity.class);
        startActivity(intent);
    }


}
