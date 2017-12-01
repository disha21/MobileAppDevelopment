package edu.neu.madcourse.dishasoni.project;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.neu.madcourse.dishasoni.R;
import edu.neu.madcourse.dishasoni.tictactoe.Score;


public class ProjectMainActivity extends AppCompatActivity {
    public  DatabaseConnector dbCon;
    List<SettingsInfo> locationItems = new ArrayList<SettingsInfo>();
    private Object ArrayAdapter;
    List<Geofence> selectedGeofences = new ArrayList<Geofence>();
    int GEOFENCE_RADIUS = 100;

    private enum PendingGeofenceTask {
        ADD, REMOVE, NONE
    }

    /**
     * Provides access to the Geofencing API.
     */
    public GeofencingClient mGeofencingClient;

    /**
     * The list of geofences used in this sample.
     */
   // private ArrayList<Geofence> mGeofenceList;

    /**
     * Used when requesting to add or remove geofences.
     */
    public PendingIntent mGeofencePendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_main);
        this.setTitle("Geo Silencer");
        mGeofencingClient = LocationServices.getGeofencingClient(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new ConnectTodB().execute((Object[]) null);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Settings"));
        tabLayout.addTab(tabLayout.newTab().setText("Add New"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
         viewPager.setAdapter(adapter);
         viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
         tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            //If the permission is not granted, launch an inbuilt activity to grant permission
            if (!nm.isNotificationPolicyAccessGranted()) {
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
            }
        }


   }

    protected void createLocationSettingsTable() {
        TableLayout locationSettingsTable = (TableLayout) findViewById(R.id.location_settings_layout);
        for (int i = 0; i < locationItems.size(); i++) {
            TableRow row = new TableRow(this);
            TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 20, 20, 20);
            layoutParams.weight = 1;
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            row.setLayoutParams(layoutParams);
            TextView tv1 = new TextView(this);
            String tv1Val = locationItems.get(i).getLOCATION_NAME() + locationItems.get(i).getRINGING_MODE();
            tv1.setText(tv1Val);
            tv1.setBackgroundResource(R.color.available_color);
            ImageButton btn = new ImageButton(this);
           // btn.setBackgroundResource(R.drawable.delete);
            Bitmap bmp= BitmapFactory.decodeResource(getResources(), R.drawable.delete);
            int width=50;
            int height=20;
            Bitmap resizedbitmap=Bitmap.createScaledBitmap(bmp, width, height, true);
            Canvas canvas = new Canvas(resizedbitmap);
//            canvas.drawColor(Color.TRANSPARENT);
//            canvas.drawBitmap(image, 0, 0, null);
          //  btn.setImageBitmap(canvas);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), resizedbitmap);

            btn.setBackgroundDrawable(bitmapDrawable);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TableRow tablerow = (TableRow)v.getParent();
                    TextView sample = (TextView) tablerow.getChildAt(0);
                    String user=sample.getText().toString().trim();




                }


            });

           // tv1.setPadding(20, 20, 20, 20);
            tv1.setGravity(Gravity.CENTER);


            row.addView(tv1);
            row.addView(btn);

            locationSettingsTable.addView(row, i);

        }
    }
    private void getGeofences(){
        for (int i = 0; i < locationItems.size(); i++) {
            double lat  = Double.valueOf(locationItems.get(i).getLATITUDE());
            double longi = Double.valueOf(locationItems.get(i).getLONGITUDE());
            LatLng coordinates = new LatLng(lat,longi);
            selectedGeofences.add(new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this
                    // geofence.
                    .setRequestId(java.util.UUID.randomUUID().toString())

                    // Set the circular region of this geofence.
                    .setCircularRegion(
                            coordinates.latitude,
                            coordinates.longitude,
                            GEOFENCE_RADIUS
                    )

                    // Set the expiration duration of the geofence. This geofence gets automatically
                    // removed after this period of time.
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)

                    // Set the transition types of interest. Alerts are only generated for these
                    // transition. We track entry and exit transitions in this sample.
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
                            )
                    // Create the geofence.
                    .build());

        }
    }



    public class ConnectTodB extends AsyncTask<Object, Object, DatabaseConnector> {

        public DatabaseConnector dbConnector = new DatabaseConnector(getApplicationContext());


        @Override
        protected DatabaseConnector doInBackground(Object... params) {
            // Open the database
            dbConnector.open();
            return dbConnector;
        }


        protected void onPostExecute(DatabaseConnector result) {
            dbCon =  result;
            locationItems =  dbCon.listAllLocations();
            createLocationSettingsTable();
            getGeofences();

          //  noteAdapter.changeCursor(result);

            // Close Database
          //  dbConnector.close();
        }



    }




}
