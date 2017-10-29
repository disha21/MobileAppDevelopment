package edu.neu.madcourse.dishasoni.tictactoe.fcm;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by dishasoni on 10/28/17.
 */

public class SendNotification {
    private static final String SERVER_KEY = "key=AAAAwH-DoI8:APA91bHVVFdeVlNakWkOxO-TD4JVFLIvRuDiX_6qdi-fSv2is8zxjiJzi-poDQYLc_00pbvZOQcfNeo78QU86I9FUs6YFrjxnr6LpD26Go5aX4W9KkAPx1xGn9UBcSCY_be2IwL-c2du";

    // This is the client registration token
   public SendNotification(){}

    public void sendNotification(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("in","in run");
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
//            h.post(new Runnable() {
//                @Override
//                public void run() {
//                    Log.e(TAG, "run: " + resp);
//                    Toast.makeText(FCMActivity.this,resp, Toast.LENGTH_LONG).show();
//                }
//            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }

}
