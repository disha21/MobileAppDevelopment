package edu.neu.madcourse.dishasoni.project;

/**
 * Created by dishasoni on 11/22/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

//import static edu.neu.madcourse.dishasoni.project.DatabaseConnector.LocationEntry.LOCATION_NAME;
//import static edu.neu.madcourse.dishasoni.project.DatabaseConnector.LocationEntry.TABLE_NAME;

public class DatabaseConnector {

    // Declare Variables
    private static final String DB_NAME = "GeoSilencerDb";

    private static final int DATABASE_VERSION = 2;
    private SQLiteDatabase database;
    private DatabaseHelper dbOpenHelper;

    public static class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "locationDetails";
        public static final String ID = "_id";
        public static final String LOCATION_NAME = "locationName";
        public static final String ADDRESS = "address";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String RINGING_MODE = "ringingMode";
        public static final String RADIUS = "radius";
    }



    public DatabaseConnector(Context context) {
        dbOpenHelper = new DatabaseHelper(context, DB_NAME, null,
                DATABASE_VERSION);

    }

    // Open Database function
    public void open() throws SQLException {
        // Allow database to be in writable mode
        database = dbOpenHelper.getWritableDatabase();
    }

    // Close Database function
    public void close() {
        if (database != null)
            database.close();
    }

    // Create Database function
    public long InsertLocationDetails(String locationName, String address, double latitude, double longitude, int ringingMode,double radius) {
        ContentValues newCon = new ContentValues();
        newCon.put(LocationEntry.LOCATION_NAME, locationName);
        newCon.put(LocationEntry.ADDRESS, address);
        newCon.put(LocationEntry.LATITUDE, latitude);
        newCon.put(LocationEntry.LONGITUDE, longitude);
        newCon.put(LocationEntry.RINGING_MODE, ringingMode);
        newCon.put(LocationEntry.RADIUS, radius);
        open();
        long rowId = database.insert(LocationEntry.TABLE_NAME, null, newCon);
        return rowId;

    }

    public boolean deleteLocation(long id){

            return database.delete(LocationEntry.TABLE_NAME, LocationEntry._ID + "=" + id, null) > 0;

    }

    public long getLocation(double latitude, double longitude)
    {
        long id = 0;
        Cursor c = database.rawQuery("SELECT  * FROM " +   LocationEntry.TABLE_NAME + " WHERE "
                + LocationEntry.LATITUDE + " = ?"  + " and "
                + LocationEntry.LONGITUDE +" = ?", new String[] {Double.valueOf(latitude).toString(), Double.valueOf(longitude).toString()});
        while (c.moveToNext()){
             id = c.getLong(c.getColumnIndexOrThrow(LocationEntry._ID));
        }


        return id;

    }

    public List<SettingsInfo> listAllLocations() {
        List<SettingsInfo> locationSettingsList = new ArrayList<SettingsInfo>();

        String[] projection = {
                LocationEntry._ID,
                LocationEntry.LOCATION_NAME,
                LocationEntry.RINGING_MODE,
                LocationEntry.LATITUDE,
                LocationEntry.LONGITUDE,
                LocationEntry.ADDRESS,
                LocationEntry.RADIUS

        };

// Filter results WHERE "title" = 'My Title'
//        String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
//        String[] selectionArgs = { "My Title" };
//
//// How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                LocationEntry._ID + " DESC";

        Cursor cursorLocations = database.query(
                LocationEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );



        while (cursorLocations.moveToNext())

        {
            String locationName = cursorLocations.getString(
                    cursorLocations.getColumnIndexOrThrow(LocationEntry.LOCATION_NAME));
            Long ringingModeId = cursorLocations.getLong(
                    cursorLocations.getColumnIndexOrThrow(LocationEntry.RINGING_MODE));
            Long dbID = cursorLocations.getLong(
                    cursorLocations.getColumnIndexOrThrow(LocationEntry._ID));
            String latitude = cursorLocations.getString(
                    cursorLocations.getColumnIndexOrThrow(LocationEntry.LATITUDE));
            String longitude = cursorLocations.getString(
                    cursorLocations.getColumnIndexOrThrow(LocationEntry.LONGITUDE));
            String address = cursorLocations.getString(
                    cursorLocations.getColumnIndexOrThrow(LocationEntry.ADDRESS));
            String radius  = cursorLocations.getString(
                    cursorLocations.getColumnIndexOrThrow(LocationEntry.RADIUS));
            String ringingMode = "";
            if(ringingModeId == 0){
                ringingMode =  "SILENT";
            }
            if(ringingModeId == 1){
                ringingMode =  "RINGING";
            }
            SettingsInfo settingsInfo = new SettingsInfo(locationName,ringingMode,Double.valueOf(latitude),Double.valueOf(longitude),address,dbID,Double.valueOf(radius));
            locationSettingsList.add(settingsInfo);
        }
        cursorLocations.close();
        return locationSettingsList;
    }

}
