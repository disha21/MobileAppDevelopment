package edu.neu.madcourse.dishasoni.project.services;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.dishasoni.project.DatabaseConnector;
import edu.neu.madcourse.dishasoni.project.ProjectMainActivity;
import edu.neu.madcourse.dishasoni.project.SettingsInfo;

/**
 * Created by dishasoni on 12/1/17.
 */

public class AddingGeofencesService extends IntentService implements GoogleApiClient.ConnectionCallbacks {

    List<SettingsInfo> locationItems = new ArrayList<SettingsInfo>();
    Geofence selectedGeofence;
    public FusedLocationProviderClient mFusedLocationProviderClient;
    public GeofencingClient mGeofencingClient;

    public AddingGeofencesService() {
        super("AddingGeofencesService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //Create geofences from SharedPreferences/network responses
        //Connect to location services
         DatabaseConnector dbConnector = new DatabaseConnector(this);
         dbConnector.open();
         dbConnector.listAllLocations();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mGeofencingClient = LocationServices.getGeofencingClient(this);

    }

    public void onConnected(Bundle bundle) {
        //Add geofences
      //  addGeofences();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }



//    private void buildGeofence(SettingsInfo locationItem) {
//        double lat = Double.valueOf(locationItem.getLATITUDE());
//        double longi = Double.valueOf(locationItem.getLONGITUDE());
//        int radius = Integer.parseInt(String.valueOf(locationItem.getGEOFENCE_RADIUS()));
//        LatLng coordinates = new LatLng(lat, longi);
//        selectedGeofence = (new Geofence.Builder()
//                // Set the request ID of the geofence. This is a string to identify this
//                // geofence.
//                .setRequestId(java.util.UUID.randomUUID().toString())
//
//                // Set the circular region of this geofence.
//                .setCircularRegion(
//                        coordinates.latitude,
//                        coordinates.longitude,
//                        radius
//                )
//
//                // Set the expiration duration of the geofence. This geofence gets automatically
//                // removed after this period of time.
//                .setExpirationDuration(Geofence.NEVER_EXPIRE)
//
//                // Set the transition types of interest. Alerts are only generated for these
//                // transition. We track entry and exit transitions in this sample.
//                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
//                )
//                // Create the geofence.
//                .build());
//    }
//
//
//    private void addGeofence() {
//        //  GeofencingClient geoClient = ((ProjectMainActivity) getActivity()).mGeofencingClient;
//        if (ContextCompat.checkSelfPermission(getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//           mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent());
//
//        }
//    }
//
//    private PendingIntent getGeofencePendingIntent() {
//        // Reuse the PendingIntent if we already have it.
//        // PendingIntent geofencePendingIntent = ((ProjectMainActivity) getActivity()).mGeofencePendingIntent;
//        if (((ProjectMainActivity) getActivity()).mGeofencePendingIntent != null) {
//            return ((ProjectMainActivity) getActivity()).mGeofencePendingIntent;
//        }
//        Intent intent = new Intent(getActivity(), GeofenceTransitionsIntentService.class);
//        intent.putExtra("ringingMode", ringingMode);
//        //  intent.putExtra("raidus", GEOFENCE_RADIUS);
//        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
//        // addGeofences() and removeGeofences().
//        return PendingIntent.getService(getActivity().getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }
//
//    private GeofencingRequest getGeofencingRequest() {
//        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
//
//        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
//        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
//        // is already inside that geofence.
//        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
//
//        // Add the geofences to be monitored by geofencing service.
//        // builder.addGeofences(((ProjectMainActivity) getActivity()).selectedGeofences);
//        builder.addGeofence(selectedGeofence);
//
//        // Return a GeofencingRequest.
//        return builder.build();
//    }
//
//
//    private void addGeofences() {
//        for (int i = 0; i < locationItems.size(); i++) {
//            buildGeofence(locationItems.get(i));
//
//
//        }
//    }

}
