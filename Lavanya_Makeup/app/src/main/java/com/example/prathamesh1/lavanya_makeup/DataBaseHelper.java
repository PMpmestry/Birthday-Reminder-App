package com.example.prathamesh1.lavanya_makeup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HI on 5/10/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    private static final String DATABASE_NAME = "Lavanya.db";
    private static final int DATABASE_VERSION = 1;
    //Table
    private static final String TABLE_CUSTOMER = "Customer";
    //Column Names
    public static final String KEY_NAME = "name";
    public static final String KEY_MOBILENO = "mobileNo";
    public static final String KEY_EMAILID = "emailId";
    public static final String KEY_DOB = "dob";
    public static final String KEY_DOA = "doa";
    public static final String KEY_LLN = "lln";
    //Table Creation
    private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE " + TABLE_CUSTOMER + "( "
            + KEY_MOBILENO + " NUMBER PRIMARY KEY, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_EMAILID + " TEXT NOT NULL, "
            + KEY_DOB + " DATE NOT NULL," + KEY_DOA + " DATE, "
            + KEY_LLN + " NUMBER)";

    public void openDB() {
        try {
            db = this.getWritableDatabase();
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
    }

    public void closeDB() {
        try {
            this.close();
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
    }

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("DataBaseHelper", "Database Created!!");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CUSTOMER);
        Log.d("DataBaseHelper", "Tables Created!!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        onCreate(db);
    }

    public long addInformation(String mobileNo, String name, String emailId, String dob, String doa, String lln) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MOBILENO, mobileNo);
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_EMAILID, emailId);
        contentValues.put(KEY_DOB, dob);
        contentValues.put(KEY_DOA, doa);
        contentValues.put(KEY_LLN, lln);

        long k = db.insert(TABLE_CUSTOMER, null, contentValues);
        return k;
    }

    public Cursor getInformation(String searchText) {
        Log.d("MyApp", "getInformation()called");
        String[] columns = {KEY_MOBILENO, KEY_NAME, KEY_EMAILID, KEY_DOB, KEY_DOA, KEY_LLN};
        Cursor cursor;
        if (searchText != null && searchText.length() > 0) {
            String searchQuery = "SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + KEY_NAME + " LIKE '%" + searchText + "%'";
            cursor = db.rawQuery(searchQuery, null);

        } else {
//            String query = "SELECT * FROM "+ TABLE_CUSTOMER +" ORDER BY "+ KEY_DOB +" DESC";
            String query = "SELECT " + KEY_NAME + "," + KEY_MOBILENO + "," + KEY_DOB + "," + KEY_EMAILID + "," + KEY_DOA + "," + KEY_LLN + "," +
                    "date(strftime('%Y', 'now','localtime')||strftime('-%m-%d',"+ KEY_DOB+")) as currbirthday, " +
                    "date(strftime('%Y', 'now','localtime')||strftime('-%m-%d',"+ KEY_DOB+"),'+1 YEAR') as nextbirthday FROM " + TABLE_CUSTOMER +
                    " ORDER BY CASE WHEN currbirthday >= CURRENT_TIMESTAMP THEN currbirthday ELSE nextbirthday END;";
            cursor = db.rawQuery(query, null);
//            cursor = db.query(TABLE_CUSTOMER, columns, null, null, null, null, KEY_DOB);
        }
        return cursor;
    }

    public Cursor getInfo(String MobileNo) {
        Cursor cursor;
        String query = "SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + KEY_MOBILENO + " LIKE '%" + MobileNo + "%'";
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    public int updateInformation(String old_mobNo, String name, String new_mobile, String emailId, String dob, String doa, String lln) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MOBILENO, new_mobile);
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_EMAILID, emailId);
        contentValues.put(KEY_DOB, dob);
        contentValues.put(KEY_DOA, doa);
        contentValues.put(KEY_LLN, lln);
        String selection = KEY_MOBILENO + " = ?";
        String[] whereClause = {old_mobNo};
        int status = db.update(TABLE_CUSTOMER, contentValues, selection, whereClause);

        return status;
    }

    public Cursor getBirthdays(){
        Cursor cursor;
        String query = "SELECT  " + KEY_NAME + "," + KEY_MOBILENO + "," + KEY_DOB + "," + KEY_EMAILID + "," + KEY_DOA + "," + KEY_LLN + "," +
                "date(strftime('%Y', 'now','localtime')||strftime('-%m-%d',"+ KEY_DOB+"),'-7 day') as currbirthday " +
                "FROM " + TABLE_CUSTOMER + " WHERE currbirthday == date('now')";
        cursor = db.rawQuery(query, null);
        return cursor;
    }
}

