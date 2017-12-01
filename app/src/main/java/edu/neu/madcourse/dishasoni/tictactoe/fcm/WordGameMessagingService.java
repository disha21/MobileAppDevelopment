package edu.neu.madcourse.dishasoni.tictactoe.fcm;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import edu.neu.madcourse.dishasoni.R;
import edu.neu.madcourse.dishasoni.tictactoe.HelloMad;
import edu.neu.madcourse.dishasoni.tictactoe.LeadeBoardActivity;
import edu.neu.madcourse.dishasoni.tictactoe.ReceiveNotification;


public class WordGameMessagingService extends FirebaseMessagingService {
    private static final String TAG = WordGameMessagingService.class.getSimpleName();
    static AlertDialog alertBox2;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            // Note: We happen to be just getting the body of the notification and displaying it.
            // We could also get the title and other info and do different things.
            String msg = remoteMessage.getNotification().getBody();
            if(msg.startsWith("Congratulations"))
                sendNotification(msg);
            else
            checkApplicationStatus(msg);
           // sendNotification();
        }

    }

    public void checkApplicationStatus(String message){
        if(isAppRunning("edu.neu.madcourse.dishasoni") == "notRunning"){
            Log.d("app is","App is not running");
            sendNotification(message);
        }
        if(isAppRunning("edu.neu.madcourse.dishasoni") == "foregroundService") {
            Intent in = new Intent(this, ReceiveNotification.class);
            startActivity(in);
        }

        if(isAppRunning("edu.neu.madcourse.dishasoni") == "background") {
            Log.d("app is", "App is  running in background");
            sendNotification(message);
        }
        if(isAppRunning("edu.neu.madcourse.dishasoni") == "foreground"){
            Log.d("app is","App is not running");
            sendNotification(message);
        }
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     *
     *
     */
    private void sendNotification(String messageBody) {

        Intent intent = new Intent(this,LeadeBoardActivity.class);
//        intent.putExtra("name", "");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
        PendingIntent callIntent = PendingIntent.getActivity(this, (int)System.currentTimeMillis(),
                new Intent(this, LeadeBoardActivity.class), 0);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.home)
                .setContentTitle("Scroggle")
                .setContentText(messageBody)
                .addAction(R.drawable.home, "Go To LeaderBoard", callIntent).setContentIntent(pendingIntent).build();
              //  .setAutoCancel(true)
             //   .setSound(defaultSoundUri)
              //  .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder.flags |= Notification.FLAG_AUTO_CANCEL ;

        notificationManager.notify(0 /* ID of notification */, notificationBuilder);
    }



    public static String isAppRunning(final String packageName) {
        final ActivityManager activityManager = (ActivityManager) HelloMad.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        String status  = "";
        if (procInfos == null)
            status =  "notRunning";

            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                Log.d("processInfo",processInfo.toString());
                if (processInfo.importance == 100 && processInfo.processName.equals(packageName)) {
                    status =  "foreground";
                }else if (processInfo.importance == 400 && processInfo.processName.equals(packageName)) {
                    status =  "background";
                }
                else if (processInfo.importance == 125 && processInfo.processName.equals(packageName)) {
                    status =  "foregroundService";
                }
                else{
                    status =  "notRunning";
                }
//                if (processInfo.processName.equals(packageName)) {
//                    return true;
//                }
            }

return status;
    }

    }
