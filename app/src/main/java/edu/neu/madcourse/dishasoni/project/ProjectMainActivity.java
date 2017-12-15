package edu.neu.madcourse.dishasoni.project;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.neu.madcourse.dishasoni.R;




public class ProjectMainActivity extends AppCompatActivity  {
    public DatabaseConnector dbCon;
    List<SettingsInfo> locationItems = new ArrayList<SettingsInfo>();
    static AlertDialog alertBox;
    private Object ArrayAdapter;
    List<Geofence> selectedGeofences = new ArrayList<Geofence>();
    int GEOFENCE_RADIUS = 100;
    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
    private SettingsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public FusedLocationProviderClient mFusedLocationProviderClient;

    private ProjectMainActivity activityReference = this;




//    @Override
//    public void onRowLongClicked(final int position) {
//        Log.d("pos", "pos" + position);
//        final int pos = position;
//        final AlertDialog.Builder alertDelete = new AlertDialog.Builder(this);
//        alertDelete.setTitle("Delete Geofence");
//        alertDelete.setMessage("Do you really want to delete this location")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        SettingsInfo location = locationItems.get(pos);
//                        String geofenceId = String.valueOf(location.getGEOFENCE_ID());
//                        List<String> geofenceIds = new ArrayList<String>();
//                        geofenceIds.add(geofenceId);
//                        mGeofencingClient.removeGeofences(geofenceIds);
//                        dbCon.deleteLocation(location.getGEOFENCE_ID());
//                        mAdapter.removeLocation(position);
//                        mAdapter.notifyDataSetChanged();
//                      //  refreshSettingsTab();
//
//                    }
//                });
//        alertDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.dismiss();
//            }
//        });


//
//        alertBox = alertDelete.show();
//        Button nbutton = alertBox.getButton(DialogInterface.BUTTON_NEGATIVE);
//        nbutton.setTextColor(Color.RED);
//    }





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
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mGeofencingClient = LocationServices.getGeofencingClient(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // new ConnectTodB().execute((Object[]) null);
        dbCon =  new DatabaseConnector(getApplicationContext());
        dbCon.open();
        locationItems = dbCon.listAllLocations();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Settings"));
        tabLayout.addTab(tabLayout.newTab().setText("Add New"));
        //  tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("tabselected","tabselected");
//                if(tab.getPosition() == 0)
//                manageSettingsFragment.refreshSettingsTab(refreshSettingsTab());
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //If the permission is not granted, launch an inbuilt activity to grant permission
            if (!nm.isNotificationPolicyAccessGranted()) {
                startActivity(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
            }
        }


    }
    @Override
    protected void onDestroy(){
        Log.d("in destroy","destroy");
        if(dbCon != null)
        dbCon.close();
        super.onDestroy();


    }

    @Override
    protected void onStart(){
         super.onStart();

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
            dbCon = result;
            locationItems = dbCon.listAllLocations();


            //add a fragment

          //  mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
//            mLayoutManager = new LinearLayoutManager(getApplicationContext());
//            mRecyclerView.setLayoutManager(mLayoutManager);
//            mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
//            mAdapter = new SettingsAdapter(getApplicationContext(), locationItems, activityReference);
          //  mRecyclerView.setAdapter(mAdapter);




        }




    }

    public List<SettingsInfo> refreshSettingsTab(){
        Log.d("refreshItemsActivity","refreshItemsActivity");
        List<SettingsInfo>items = dbCon.listAllLocations();

        Log.d("refreshItemsActivity", items.toString());
//        mAdapter.refreshList(locationItems);
//        mAdapter.notifyDataSetChanged();
//        //  createLocationSettingsTable();
        return items;

    }

    public void deleteGeofence(SettingsInfo location) {
        String geofenceId = String.valueOf(location.getGEOFENCE_ID());
        List<String> geofenceIds = new ArrayList<String>();
        geofenceIds.add(geofenceId);
        mGeofencingClient.removeGeofences(geofenceIds);
        dbCon.deleteLocation(location.getGEOFENCE_ID());

    }

}
