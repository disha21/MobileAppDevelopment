package edu.neu.madcourse.dishasoni;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import edu.neu.madcourse.dishasoni.project.ProjectMainActivity;
import edu.neu.madcourse.dishasoni.tictactoe.EnterNameActivity;
import edu.neu.madcourse.dishasoni.tictactoe.WordGameActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Disha Soni");
        checkPlayServices();

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
        Intent intent = new Intent(MainActivity.this, EnterNameActivity.class);
        startActivity(intent);
    }
    public void GeosilencerActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ProjectMainActivity.class);
        startActivity(intent);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


}
