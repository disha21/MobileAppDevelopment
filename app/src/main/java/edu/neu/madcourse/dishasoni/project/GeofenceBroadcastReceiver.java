package edu.neu.madcourse.dishasoni.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import edu.neu.madcourse.dishasoni.project.services.GeofenceTransitionsIntentService;

/**
 * Created by dishasoni on 11/28/17.
 */

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("inReceiver","inReceiver:");
        Intent startServiceIntent = new Intent(context, GeofenceTransitionsIntentService.class);
        Bundle extras = intent.getExtras();
        int ringingModeVal = extras.getInt("ringingMode");
        startServiceIntent.putExtra("ringingMode",ringingModeVal);
        context.startService(startServiceIntent);
    }
}