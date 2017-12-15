package edu.neu.madcourse.dishasoni.project;

/**
 * Created by dishasoni on 11/23/17.
 */

public class SettingsInfo {
    private String LOCATION_NAME;
    private String RINGING_MODE;
    private double LATITUDE;
    private double LONGITUDE;
    private String ADDRESS;
    private long GEOFENCE_ID;

    public double getGEOFENCE_RADIUS() {
        return GEOFENCE_RADIUS;
    }

    public void setGEOFENCE_RADIUS(double GEOFENCE_RADIUS) {
        this.GEOFENCE_RADIUS = GEOFENCE_RADIUS;
    }

    private double GEOFENCE_RADIUS;
    SettingsInfo(){}


    SettingsInfo(String locationName, String ringingMode, double latitude, double longitude, String address, long geofencId,double geofenceRadius )
    {
        this.LOCATION_NAME = locationName;
        this.RINGING_MODE = ringingMode;
        this.LATITUDE = latitude;
        this.LONGITUDE = longitude;
        this.ADDRESS =  address;
        this.GEOFENCE_ID  = geofencId;
        this.GEOFENCE_RADIUS =  geofenceRadius;
    }
    public void setLOCATION_NAME(String LOCATION_NAME) {
        this.LOCATION_NAME = LOCATION_NAME;
    }
    public String getLOCATION_NAME() {
        return LOCATION_NAME;
    }

    public long getGEOFENCE_ID() {
        return GEOFENCE_ID;
    }

    public void setGEOFENCE_ID(long GEOFENCE_ID) {
        this.GEOFENCE_ID = GEOFENCE_ID;
    }

    public void setRINGING_MODE(String RINGING_MODE) {
        this.RINGING_MODE = RINGING_MODE;
    }

    public String getRINGING_MODE() {
        return RINGING_MODE;
    }


    public double getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public double getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }
    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }
}
