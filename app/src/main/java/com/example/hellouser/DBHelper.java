package com.example.hellouser;

import android.content.Context;
import android.database.Cursor;
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
    public static final String USER_DELIVERY = "DE";
    public static final String USERS_ADDTABLE = "USERSADD";//שם הטבלה
    public static final String USER_ADDRESS = "ADDRESS";
    public static final String USER_CHECKED= "CHECKED";
    SQLiteDatabase db;

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
        String str2 = " CREATE TABLE " + USERS_ADDTABLE;
        str2 +=" ( " + USER_ADDRESS + " TEXT, ";
        str2 += USER_PHONE + " TEXT, ";
        str2 += USER_DELIVERY + " TEXT, ";
        str2 += USER_NAME + " TEXT, ";
        str2 += USER_CHECKED + " TEXT );";
        db.execSQL(str);
        db.execSQL(str2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void deleteFirstRow()
    {
        db=this.getWritableDatabase();

        Cursor cursor = db.query(USERS_ADDTABLE, null, null, null, null, null, null);

        if(cursor.moveToFirst()) {
            String rowId = cursor.getString(0);
     String name= cursor.getString(2);;
            db.delete(USERS_ADDTABLE, USER_ADDRESS + " =? AND "+USER_DELIVERY+ " =?",  new String[]{rowId,name});
            return;
        }
        db.close();
    }

}
