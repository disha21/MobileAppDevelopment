package edu.neu.madcourse.dishasoni.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dishasoni on 11/22/17.
 */

public  class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "GeoSilencerDb";
    private static final int DATABASE_VERSION = 2;

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseConnector.LocationEntry.TABLE_NAME + " (" +
                    DatabaseConnector.LocationEntry._ID + " INTEGER PRIMARY KEY," +
                    DatabaseConnector.LocationEntry. LOCATION_NAME + " TEXT," +
                    DatabaseConnector.LocationEntry.ADDRESS + " TEXT," +
                    DatabaseConnector.LocationEntry.LATITUDE + " REAL," +
                    DatabaseConnector.LocationEntry.LONGITUDE + " REAL," +
                    DatabaseConnector.LocationEntry.RINGING_MODE + " INTEGER," +
                    DatabaseConnector.LocationEntry.RADIUS + " REAL)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseConnector.LocationEntry.TABLE_NAME;


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, DB_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a database table
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Database will be wipe on version change
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
