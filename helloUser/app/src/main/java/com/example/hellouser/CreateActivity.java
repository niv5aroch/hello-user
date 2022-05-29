package com.example.hellouser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity {
EditText street;
EditText country;
String[] userInfo = new String[1];
EditText city;
    EditText name;
    EditText number;

    ContentValues contentValues = new ContentValues();

    DBHelper dbHelper;
    SQLiteDatabase db;
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
        contentValues.put(DBHelper.USER_CHECKED, "FALSE");
        db.insert(DBHelper.USERS_ADDTABLE, null, contentValues);
        Intent intent = new Intent(this, FortodayActivity.class);
        intent.putExtra("isadmin" ,"admin");
        startActivity(intent);


        db.close();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}