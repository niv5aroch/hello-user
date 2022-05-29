package com.example.hellouser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
EditText street;
EditText country;
String[] userInfo = new String[1];
EditText city;
    EditText name;
    EditText number;
Spinner spiner;
    ContentValues contentValues = new ContentValues();
SQLiteDatabase db1;
    DBHelper dbHelper;
    SQLiteDatabase db;
    List<String> names;
    ArrayAdapter<String> arrayAdapterSpinner;
    String deliver="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        street=findViewById(R.id.street);
        city=findViewById(R.id.city);
        country=findViewById(R.id.country);
        name=findViewById(R.id.name);
        number=findViewById(R.id.number);
        dbHelper=new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        db1 =dbHelper.getReadableDatabase();
        spiner=findViewById(R.id.spiner);
        Cursor cursor=db1.query(DBHelper.USERS_TABLE,null,null,null,null,null,null);
        cursor.moveToFirst();
        names=new ArrayList<>();
        while (!cursor.isAfterLast()){
            if (cursor.getString(4).matches("deliver"))
                names.add(cursor.getString(3));
            cursor.moveToNext();
        }
        arrayAdapterSpinner=new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice,names);
        spiner.setAdapter(arrayAdapterSpinner);
        spiner.setOnItemSelectedListener(this);
    }

    public void go(View view)
    {
        String str1=street.getText().toString();
        String str2=city.getText().toString();
        String str3=country.getText().toString();
        String geoUri = "geo:0,0?q="+str1+", "+str2+", "+str3+"&z=15";
        String str4=name.getText().toString();
        String str5=number.getText().toString();


        contentValues.put(DBHelper.USER_ADDRESS, geoUri);
        contentValues.put(DBHelper.USER_NAME, str4);
        contentValues.put(DBHelper.USER_PHONE, str5);
        contentValues.put(DBHelper.USER_DELIVERY,deliver);
        contentValues.put(DBHelper.USER_CHECKED, "FALSE");
        db.insert(DBHelper.USERS_ADDTABLE, null, contentValues);
        Intent intent = new Intent(this, FortodayActivity.class);
        intent.putExtra("isadmin" ,"admin");
        intent.putExtra("name",deliver);
        startActivity(intent);
        db.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        deliver=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}