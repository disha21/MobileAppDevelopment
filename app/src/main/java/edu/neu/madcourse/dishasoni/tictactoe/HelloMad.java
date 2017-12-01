package edu.neu.madcourse.dishasoni.tictactoe;

import android.app.Application;
import android.content.Context;

/**
 * Created by dishasoni on 10/28/17.
 */

public class HelloMad extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        HelloMad.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return HelloMad.context;
    }
}