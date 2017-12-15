package edu.neu.madcourse.dishasoni.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import edu.neu.madcourse.dishasoni.project.services.AddingGeofencesService;
import edu.neu.madcourse.dishasoni.project.services.GeofenceTransitionsIntentService;

/**
 * Created by dishasoni on 11/28/17.
 */

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    boolean isGpsEnabled;
    boolean isNetworkEnabled;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("GeofenceReceiver","GeofenceBroadcastReceiver");

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGpsEnabled || isNetworkEnabled) {
                Intent startServiceIntent = new Intent(context, AddingGeofencesService.class);
                context.startService(startServiceIntent);
            }

    }
}