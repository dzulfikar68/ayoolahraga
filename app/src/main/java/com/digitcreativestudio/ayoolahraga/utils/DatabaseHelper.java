package com.digitcreativestudio.ayoolahraga.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.digitcreativestudio.ayoolahraga.model.Venue;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ayoolahraga_db";
    private Context context;

    public static final String TABLE_NAME = "venue";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_IMAGE + " TEXT,"
                    + COLUMN_ADDRESS + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertVenue(int id,
                            String title,
                            String image,
                            String address) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_ID, id);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_ADDRESS, address);

        // insert row
        long id_new = db.insert(TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id_new;
    }

    public void deleteVenue(Venue venue) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(venue.getId_venue())});
        db.close();
    }

    public ArrayList<Venue> getAllVenue() {
        ArrayList<Venue> movies = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
//                " WHERE "+ COLUMN_TYPE + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Venue venue = new Venue();
                venue.setId_venue(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                venue.setName_venue(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                venue.setPhoto(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
                venue.setAddress_venue(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));

                movies.add(venue);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return movies;
    }

    public boolean existCheck(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID,
                        COLUMN_TITLE,
                        COLUMN_IMAGE,
                        COLUMN_ADDRESS},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        boolean result = false;

        try {
            Venue data = new Venue();
            data.setId_venue(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));

            if(data.getId_venue() == id){
                result = true;
                Log.e("ADA", "FAVORITE");
            }
        }catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
            Log.e("TIDAK ADA", e.toString());
        }

        cursor.close();
        return result;
    }
}