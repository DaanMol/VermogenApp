package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String VERMOGEN_TABLE = "VERMOGEN_TABLE";
    public static final String COLUMN_NAAM = "NAAM";
    public static final String COLUMN_DATUM = "DATUM";
    public static final String COLUMN_OEFENING = "OEFENING";
    public static final String COLUMN_GEWICHT = "GEWICHT";
    public static final String COLUMN_POINTS = "POINTS";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "meting.db", null, 1);
    }

    // called whenever a new instance of the db is made
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + VERMOGEN_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAAM + " TEXT, " + COLUMN_DATUM + " TEXT, " + COLUMN_OEFENING + " TEXT, " + COLUMN_GEWICHT + " INT," + COLUMN_POINTS + " TEXT)";
        db.execSQL(createTableStatement);
    }

    // used when a newer db version is available
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(meting meet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAAM, meet.getNaam());
        cv.put(COLUMN_DATUM, meet.getDatum());
        cv.put(COLUMN_OEFENING, meet.getOefening());
        cv.put(COLUMN_GEWICHT, meet.getGewicht());
        cv.put(COLUMN_POINTS, meet.getPointList());

        long insert = db.insert(VERMOGEN_TABLE, null, cv);
        if (insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteOne(String id){
        //delete record if found in database
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + VERMOGEN_TABLE + " WHERE " + COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }
        else {
            return false;
        }

    }

    public List<meting> getEveryone(){
        List<meting> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + VERMOGEN_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through cursor, add to list
            do {
                int id = cursor.getInt(0);
                String naam = cursor.getString(1);
                String datum = cursor.getString(2);
                String oefening = cursor.getString(3);
                int gewicht = cursor.getInt(4);
                String pointList = cursor.getString(5);

                meting meting = new meting(id, naam, datum, oefening, gewicht, pointList);
                returnList.add(meting);

            } while (cursor.moveToNext());
        }
        else {
            // nothing to add
        }

        cursor.close();
        db.close();

        return returnList;
    }
}
