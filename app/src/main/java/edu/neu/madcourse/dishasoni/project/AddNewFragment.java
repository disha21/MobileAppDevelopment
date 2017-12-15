package edu.neu.madcourse.dishasoni.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.preference.PreferenceManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.internal.PlaceEntity;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.neu.madcourse.dishasoni.R;
import edu.neu.madcourse.dishasoni.project.services.GeofenceTransitionsIntentService;
import edu.neu.madcourse.dishasoni.project.ManageSettingsFragment.*;
import edu.neu.madcourse.dishasoni.tictactoe.MainFragment;

import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static edu.neu.madcourse.dishasoni.R.id.textViewLocation;



/**
 * Created by dishasoni on 11/19/17.
 */

public class AddNewFragment extends Fragment  implements GoogleMap.OnMyLocationButtonClickListener, OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    //   private static final int GOOGLE_API_CLIENT_ID = 0;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    MapView mMapView;

    private GoogleMap googleMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    boolean mLocationPermissionGranted;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private CameraPosition mCameraPosition;
    View view;
    Place selectedPlace;
    int ringingMode;
    static AlertDialog alertBoxSave;
    EditText textViewLocationName;
    String SpinnerRingingModeValue;
    LatLng selectedLocationCoordinates;
    Geofence selectedGeofence;


    int GEOFENCE_RADIUS = 500;
    private ArrayList<Geofence> mGeofenceList;

    //Map<> geofenceData = new HashMap<>()

    // bounds for the entire world
    private static final LatLngBounds BOUNDS = new LatLngBounds(new LatLng(-85, -180), new LatLng(85, 180));

    AddNewFragment fragmentContext = this;

    private ManageSettingsFragment managedSettingFragment;

    public void setManagedSettingFragment(ManageSettingsFragment msf){
        this.managedSettingFragment = msf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // super.onCreate(savedInstanceState);
       view = inflater.inflate(R.layout.add_new_fragment, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.getMapAsync(this);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        // needed to get the map to display immediately

//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        mMapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap mMap) {
//                googleMap = mMap;
//
//                // Prompt the user for permission.
//                getLocationPermission();
//
//                // Turn on the My Location layer and the related control on the map.
//                updateLocationUI();
//
//                // Get the current location of the device and set the position of the map.
//                getDeviceLocation();
//                mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){
//                    @Override
//                    public boolean onMyLocationButtonClick() {
//                        TextView textViewLocation = (TextView) view.findViewById(R.id.textViewLocation);
//                        String _Location = updateLocationAddress();
//                        textViewLocation.setText(_Location);
//                        return false;
//                    }
//
//
//
//                });
//            }
//        });

//        if (savedInstanceState != null) {
//            mLastLocation = savedInstanceState.getParcelable(KEY_LOCATION);
//            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
//        }

        SupportPlaceAutocompleteFragment autocompleteFragment =
                (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                googleMap.clear();
                // TODO: Get info about the selected place.
                TextView textViewLocation = (TextView) view.findViewById(R.id.textViewLocation);
                textViewLocation.setText(place.getAddress());
                final LatLng selectedLocation = place.getLatLng();
                selectedPlace = place;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(selectedLocation.latitude,
                                selectedLocation.longitude), 15));
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(selectedLocation.latitude, selectedLocation.longitude)
                        ));
                Log.e("Log", "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.e("Log", "An error occurred: " + status);
            }
        });

        Button save = (Button) view.findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                DatabaseConnector dbCon = ((ProjectMainActivity) getActivity()).dbCon;
                if (selectedPlace != null) {
                    selectedLocationCoordinates = selectedPlace.getLatLng();
                } else {
                    if (mLastLocation != null) {
                        selectedLocationCoordinates = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    } else {
                        selectedLocationCoordinates = mDefaultLocation;
                    }

                }
                TextView textViewLocation = (EditText) view.findViewById(R.id.viewTextLocationName);
                EditText geofenceRadius =  (EditText) view.findViewById(R.id.viewTextLocationRadius);
                GEOFENCE_RADIUS = Integer.parseInt(geofenceRadius.getText().toString());
                onRadioButtonClicked(view);
                if (SpinnerRingingModeValue.equalsIgnoreCase("SILENT"))
                    ringingMode = 0;
                else if (SpinnerRingingModeValue.equalsIgnoreCase("RINGING"))
                    ringingMode = 1;
                else
                    ringingMode = 0;
               // long existingId = dbCon.getLocation(selectedLocationCoordinates.latitude, selectedLocationCoordinates.longitude);
               // if (existingId == 0) {
                    long dbId = 0;
                    if (selectedPlace != null ) {
                        dbId = dbCon.InsertLocationDetails(
                                textViewLocation.getText().toString(),
                                selectedPlace.getName().toString(),
                                selectedLocationCoordinates.latitude,
                                selectedLocationCoordinates.longitude,
                                ringingMode,
                                GEOFENCE_RADIUS);
                    } else {
                         String loc  = updateLocationAddress();
                       // String loc = "abc";
                            dbId = dbCon.InsertLocationDetails(
                                textViewLocation.getText().toString(),
                                loc,
                                selectedLocationCoordinates.latitude,
                                selectedLocationCoordinates.longitude,
                                ringingMode,
                                    GEOFENCE_RADIUS);
                    }


                    if (dbId > 0) {
                        buildGeofence(selectedLocationCoordinates, dbId);
                        addGeofence();
                        final AlertDialog.Builder alertBoxSaveBuilder = new AlertDialog.Builder(getActivity());
                        alertBoxSaveBuilder.setTitle("Success");
                        alertBoxSaveBuilder.setMessage(SpinnerRingingModeValue + " mode is set up for your "
                                + textViewLocation.getText().toString() + " location")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        navigateAfterSave();
                                    }
                                });

                        alertBoxSave = alertBoxSaveBuilder.show();
                    } else {
                        final AlertDialog.Builder alertBoxSaveBuilder = new AlertDialog.Builder(getActivity());
                        alertBoxSaveBuilder.setTitle("Failed");
                        alertBoxSaveBuilder.setMessage("Could not save your location. Try Again");
                        alertBoxSave = alertBoxSaveBuilder.show();
                    }
//                } else {
//                    final AlertDialog.Builder alertBoxSaveBuilder = new AlertDialog.Builder(getActivity());
//                    alertBoxSaveBuilder.setTitle("Failed");
//                    alertBoxSaveBuilder.setMessage("The location you are trying to add already exists");
//                    alertBoxSave = alertBoxSaveBuilder.show();
               // }


              //  ((ProjectMainActivity) getActivity()).refreshSettingsTab();
            }
        });

        return view;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        TextView textViewLocation = (TextView) view.findViewById(R.id.textViewLocation);
        String _Location = updateLocationAddress();
        textViewLocation.setText(_Location);
        return false;
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
        googleMap.setOnMyLocationButtonClickListener(this);
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        RadioGroup radioGroupRingingMode = (RadioGroup) view.findViewById(R.id.ringing_mode);
        int selectedId = radioGroupRingingMode.getCheckedRadioButtonId();
        RadioButton radioRingingModeButton = (RadioButton)view.findViewById(selectedId);
        SpinnerRingingModeValue = radioRingingModeButton.getText().toString();

    }


    private void navigateAfterSave() {
        SupportPlaceAutocompleteFragment autocompleteFragment =
                (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);
        autocompleteFragment.setText("");
        TextView textViewLocation = (EditText) view.findViewById(R.id.viewTextLocationName);
        textViewLocation.setText("");

        selectedPlace = null;


//        Spinner SpinnerRingingMode = (Spinner) view.findViewById(R.id.spinner1);
//        SpinnerRingingMode.setSelection(0);
        TextView locationText = (TextView) view.findViewById(R.id.textViewLocation);
        locationText.setText("");
        TextView radiusText = (TextView) view.findViewById(R.id.viewTextLocationRadius);
        radiusText.setText("");
        RadioButton radioRingingModeButton = (RadioButton)view.findViewById(R.id.silent_radio);
        radioRingingModeButton.setChecked(true);
        getDeviceLocation();
        List<SettingsInfo> items = ((ProjectMainActivity) getActivity()).refreshSettingsTab();
        managedSettingFragment.refreshSettingsTab(items);
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        viewPager.setCurrentItem(0, true);


    }

    private void buildGeofence(LatLng coordinates, long geofenceId) {
        selectedGeofence = ((new Geofence.Builder()
       // ((ProjectMainActivity) getActivity()).selectedGeofences.add((new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId(String.valueOf(geofenceId))

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
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)


                // Create the geofence.
                .build()));


    }


    private void addGeofence() {
        //  GeofencingClient geoClient = ((ProjectMainActivity) getActivity()).mGeofencingClient;
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            ((ProjectMainActivity) getActivity()).mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent());

        }
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        // PendingIntent geofencePendingIntent = ((ProjectMainActivity) getActivity()).mGeofencePendingIntent;
        if (((ProjectMainActivity) getActivity()).mGeofencePendingIntent != null) {
            return ((ProjectMainActivity) getActivity()).mGeofencePendingIntent;
        }
        Intent intent = new Intent(getActivity(), GeofenceTransitionsIntentService.class);
        intent.putExtra("ringingMode", ringingMode);
      //  intent.putExtra("raidus", GEOFENCE_RADIUS);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        return PendingIntent.getService(getActivity().getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
        // is already inside that geofence.
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        // Add the geofences to be monitored by geofencing service.
       // builder.addGeofences(((ProjectMainActivity) getActivity()).selectedGeofences);
        builder.addGeofence(selectedGeofence);

        // Return a GeofencingRequest.
        return builder.build();
    }


    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            updateLocationUI();
            getDeviceLocation();
        }
    }

    private void getDeviceLocation() {
    /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = ((ProjectMainActivity) getActivity()).mFusedLocationProviderClient.getLastLocation();
                if (locationResult != null) {
                    locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(Task<Location> task) {
                            if (task.isSuccessful()) {
                                // Set the map's camera position to the current location of the device.
                                mLastLocation = task.getResult();
                                if (mLastLocation == null) {
                                    Log.d("locationUpdate", "location is null");
                                    startLocationUpdates();
                                } else {
                                    Log.d("locationUpdate", "location is not null");
                                     String _Location = updateLocationAddress();
                                TextView textViewLocation = (TextView) view.findViewById(R.id.textViewLocation);
                                textViewLocation.setText(_Location);
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastLocation.getLatitude(),
                                                mLastLocation.getLongitude()), 15));
                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())
                                        ).title("Current Location"));
                                }


                            } else {
                                Log.d("log", "Current location is null. Using defaults.");
                                Log.e("log", "Exception: %s", task.getException());
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                            }
                        }
                    });
                } else{
                    Log.d("locationUpdate", "location is null in else task");
                    startLocationUpdates();
                }


            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void startLocationUpdates() {
        final Location location = new Location("point A");
        location.setLatitude(1.2345d);
        location.setLongitude(1.2345d);
        mLastLocation = location;

//        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            ((ProjectMainActivity) getActivity()).mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
//                    new LocationCallback() {
//                        @Override
//                        public void onLocationResult(LocationResult locationResult) {
//                            for (Location location : locationResult.getLocations()) {
//                                mLastLocation = location;
//                            }
//                        }
//
//                    },
//                    null);
//        }
    }


    private String updateLocationAddress() {

        String _Location = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            if (mLastLocation != null) {
                double lat = mLastLocation.getLatitude();
                double longi = mLastLocation.getLongitude();
                List<Address> listAddresses = geocoder.getFromLocation(lat, longi, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    _Location = listAddresses.get(0).getAddressLine(0);

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return _Location;
    }

    private void updateLocationUI() {
        if (googleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


//    @Override
//    public boolean onMyLocationButtonClick() {
//        TextView textViewLocation = (TextView) view.findViewById(R.id.textViewLocation);
//        String _Location = updateLocationAddress();
//        textViewLocation.setText(_Location);
//        return false;
//    }

//    private void stopLocationUpdates() {
//        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
//    }
//    @Override
//    public void onComplete(@NonNull Task<Void> task) {
//        if (task.isSuccessful()) {
//            final AlertDialog.Builder alertBoxSaveBuilder = new AlertDialog.Builder(getActivity());
//            alertBoxSaveBuilder.setTitle("Success");
//            alertBoxSaveBuilder.setMessage(SpinnerRingingModeValue + "mode is set up for your "
//                    + textViewLocationName.getText().toString() + " location");
//            alertBoxSave = alertBoxSaveBuilder.show();
//        }else{
//            final AlertDialog.Builder alertBoxSaveBuilder = new AlertDialog.Builder(getActivity());
//            alertBoxSaveBuilder.setTitle("Failed");
//            alertBoxSaveBuilder.setMessage("Could not save your location. Try Again");
//            alertBoxSave = alertBoxSaveBuilder.show();
//        }
//    }


}
