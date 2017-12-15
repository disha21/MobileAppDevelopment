package edu.neu.madcourse.dishasoni.project.services;

/**
 * Created by dishasoni on 11/26/17.
 */
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.dishasoni.R;
import edu.neu.madcourse.dishasoni.project.GeofenceErrorMessages;



import static android.R.attr.mode;

public class GeofenceTransitionsIntentService  extends IntentService {
    private static final String TAG = "GeofenceTransitionsIS";
    Context context = this;
    int ringingModeVal = 0;
    /**
     * This constructor is required, and calls the super IntentService(String)
     * constructor with the name for a worker thread.
     */
    public GeofenceTransitionsIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);
        Toast.makeText(this, "IntentService Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    /**
     * Handles incoming intents.
     * @param intent sent by Location Services. This Intent is provided to Location
     *               Services (inside a PendingIntent) when addGeofences() is called.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG,"geofence intent");
        //android.os.Debug.waitForDebugger();
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        Bundle extras = intent.getExtras();
        if(extras!= null){
            ringingModeVal = Integer.parseInt(extras.get("ringingMode").toString());

        }
       //
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        Log.i(TAG,"after intent");

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            String geofenceTransitionDetails = getGeofenceTransitionDetails(geofenceTransition,
                    triggeringGeofences);
            Log.i(TAG,"before send");

            // Send notification and log the transition details.
            sendNotification(geofenceTransitionDetails,context);
            Log.d(TAG, geofenceTransitionDetails);
        } else {
          //   Log the error.
            Log.d(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition));
        }
    }



    /**
     * Gets transition details and returns them as a formatted string.
     *
     * @param geofenceTransition    The ID of the geofence transition.
     * @param triggeringGeofences   The geofence(s) triggered.
     * @return                      The transition details formatted as String.
     */
    private String getGeofenceTransitionDetails(
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {

      //  String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());

        }
        String triggeringGeofencesIdsString = TextUtils.join(", ",  triggeringGeofencesIdsList);

        return  triggeringGeofencesIdsString;
    }

    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
    private void sendNotification(String notificationDetails,Context context) {
        Log.i(TAG,"sendNotification");
        // Create an explicit content Intent that starts the main Activity.
        Log.i(TAG, notificationDetails);
        if(ringingModeVal == 0){
            NotificationManager nm = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            AudioManager audioManager = (AudioManager)context.getSystemService(AUDIO_SERVICE);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && nm.isNotificationPolicyAccessGranted())
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            else{
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        }
        if(ringingModeVal == 1){
            NotificationManager nm = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
            AudioManager audioManager = (AudioManager)context.getSystemService(AUDIO_SERVICE);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && nm.isNotificationPolicyAccessGranted())
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            else{
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        }

        //Check if the phone is running Marshmallow or above



    }

    /**
     * Maps geofence transition types to their human-readable equivalents.
     *
     * @param transitionType    A transition type constant defined in Geofence
     * @return                  A String indicating the type of transition
     */
//    private String getTransitionString(int transitionType) {
//        switch (transitionType) {
//            case Geofence.GEOFENCE_TRANSITION_ENTER:
//                return getString(R.string.geofence_transition_entered);
//            case Geofence.GEOFENCE_TRANSITION_EXIT:
//                return getString(R.string.geofence_transition_exited);
//            default:
//                return getString(R.string.unknown_geofence_transition);
//        }
//    }


}
