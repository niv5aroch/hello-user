package com.example.hellouser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_FILE = "allTables.db";//קובץ בו יישמרו כל הטבלאות
    public static final String USERS_TABLE = "USERS";//שם הטבלה
    public static final String USER_ID = "Email";
    public static final String USER_PASS = "Pass";
    public static final String USER_PHONE = "PHONE";
    public static final String USER_NAME = "NAME";
    public static final String USER_USER = "USER";
    public DBHelper(@Nullable Context context) {
        super(context, DB_FILE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String str = "CREATE TABLE " + USERS_TABLE;
        str += " ( " + USER_ID + " TEXT, ";
        str += USER_PASS + " TEXT, ";
        str += USER_PHONE + " TEXT, ";
        str += USER_NAME + " TEXT, ";
        str += USER_USER + " TEXT );";
       // String s = "CREATE TABLE "+USERS_TABLE+" ( "+USER_ID+" TEXT,"+USER_PASS+" TEXT,Id TEXT);";
        //String s = "CREATE TABLE Student ( Name TEXT,Group TEXT,Id TEXT);";
        db.execSQL(str);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
