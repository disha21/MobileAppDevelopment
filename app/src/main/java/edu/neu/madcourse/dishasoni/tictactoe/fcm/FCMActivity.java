package edu.neu.madcourse.dishasoni.tictactoe.fcm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import edu.neu.madcourse.dishasoni.R;

public class FCMActivity extends AppCompatActivity {

    private static final String TAG = FCMActivity.class.getSimpleName();

    // Please add the server key from your firebase console in the follwoing format "key=<serverKey>"
    private static final String SERVER_KEY = "key=AAAAwH-DoI8:APA91bHVVFdeVlNakWkOxO-TD4JVFLIvRuDiX_6qdi-fSv2is8zxjiJzi-poDQYLc_00pbvZOQcfNeo78QU86I9FUs6YFrjxnr6LpD26Go5aX4W9KkAPx1xGn9UBcSCY_be2IwL-c2du";

    // This is the client registration token
    private static String CLIENT_REGISTRATION_TOKEN ="d-ElbpQZXUg:APA91bFsVoGKyBoQFSpUXK_QhQfRs8FDN0UyLHQ2BD56h_lMDzUGfIqRNdO4jXhl5zCLYHGzxnMtoD7ynrCwmxCU6fdHhRg94QT-DcpvKF_atR41mJd8IGYOGX8cYeGiMc8y1tUich-K";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);
        String token = FirebaseInstanceId.getInstance().getToken();
        CLIENT_REGISTRATION_TOKEN =  token;
        String msg = getString(R.string.msg_token_fmt, token);
//                Log.d(TAG, msg);
//                Toast.makeText(FCMActivity.this, msg, Toast.LENGTH_SHORT).show();

//        Button logTokenButton = (Button) findViewById(R.id.logTokenButton);
//        logTokenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get token
//
//
//                // Log and toast
//                String msg = getString(R.string.msg_token_fmt, token);
//                Log.d(TAG, msg);
//                Toast.makeText(FCMActivity.this, msg, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void subscribeToNews(View view){
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        // [END subscribe_topics]

        // Log and toast
        String msg = getString(R.string.msg_subscribed);
        Log.d(TAG, msg);
        Toast.makeText(FCMActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Button Handler; creates a new thread that sends off a message
     * @param type
     */
    public void sendMessageToNews(View type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendMessageToNews();
            }
        }).start();
    }

    public void sendMessageToNews(){
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        try {
            jNotification.put("message", "A new player has been added to the leaderboard");
            jNotification.put("body", "Check the new score");
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            jNotification.put("click_action", "Go To LeaderBoard");

            // Populate the Payload object.
            // Note that "to" is a topic, not a token representing an app instance
            jPayload.put("to", "/topics/news");
            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);

            // Open the HTTP connection and send the payload
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", SERVER_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());
            outputStream.close();

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: " + resp);
                    Toast.makeText(FCMActivity.this,resp, Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToDevice(View type) {

        final String targetToken = CLIENT_REGISTRATION_TOKEN;
                //((EditText)findViewById(R.id.tokenEditText)).getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                sendMessageToDevice(targetToken);
            }
        }).start();
    }

    /**
     * Pushes a notification to a given device-- in particular, this device,
     * because that's what the instanceID token is defined to be.
     */
    private void sendMessageToDevice(String targetToken) {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        try {
            jNotification.put("title", "Mod 6 Demo");
            jNotification.put("body", "Hi from me :) ");
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            /*
            // We can add more details into the notification if we want.
            // We happen to be ignoring them for this demo.
            jNotification.put("click_action", "OPEN_ACTIVITY_1");
            */

            /***
             * The Notification object is now populated.
             * Next, build the Payload that we send to the server.
             */

            // If sending to a single client
            jPayload.put("to", targetToken); // CLIENT_REGISTRATION_TOKEN);

            /*
            // If sending to multiple clients (must be more than 1 and less than 1000)
            JSONArray ja = new JSONArray();
            ja.put(CLIENT_REGISTRATION_TOKEN);
            // Add Other client tokens
            ja.put(FirebaseInstanceId.getInstance().getToken());
            jPayload.put("registration_ids", ja);
            */

            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);


            /***
             * The Payload object is now populated.
             * Send it to Firebase to send the message to the appropriate recipient.
             */
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", SERVER_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());
            outputStream.close();

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: " + resp);
                    Toast.makeText(FCMActivity.this,resp, Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper function
     * @param is
     * @return
     */
    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }



}
