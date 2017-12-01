package edu.neu.madcourse.dishasoni.project;

/**
 * Created by dishasoni on 11/23/17.
 */

public class SettingsInfo {
    private String LOCATION_NAME;
    private String RINGING_MODE;
    private double LATITUDE;
    private double LONGITUDE;
    SettingsInfo(){}
    SettingsInfo(String locationName,String ringingMode,double latitude,double longitude)
    {
        this.LOCATION_NAME = locationName;
        this.RINGING_MODE = ringingMode;
        this.LATITUDE = latitude;
        this.LONGITUDE = longitude;
    }
    public void setLOCATION_NAME(String LOCATION_NAME) {
        this.LOCATION_NAME = LOCATION_NAME;
    }

    public void setRINGING_MODE(String RINGING_MODE) {
        this.RINGING_MODE = RINGING_MODE;
    }

    public String getLOCATION_NAME() {
        return LOCATION_NAME;
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
}
