package com.example.hellouser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class
RegisterActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    String[] userInfo = new String[5];
    DBHelper dbHelper;

    SQLiteDatabase db;
    ContentValues contentValues = new ContentValues();
    EditText phone;
    EditText name;
    EditText pass;
    EditText id;
    RadioGroup rg;
    Button reg;
    RadioButton rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.pass);
        id = findViewById(R.id.id);
        reg = findViewById(R.id.reg);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        rg = findViewById(R.id.rg);
        // db.close();
        rg.setOnCheckedChangeListener(this);

    }

    public void onClick(View view) {


    }

    public boolean chackedemail(String mail) {
        Cursor cursor1 = db.query(DBHelper.USERS_TABLE, null, null, null, null, null, null);
        cursor1.moveToFirst();

        while (!cursor1.isAfterLast()) {
            int column = cursor1.getColumnIndex(DBHelper.USER_ID);
            String str = cursor1.getString(column);
            if (str.equals(mail)) {

                return true;
            }
            cursor1.moveToNext();
        }
        return false;
    }

    public void register(View view) {

        int user = rg.getCheckedRadioButtonId();
        rb = findViewById(user);

        userInfo[0] = id.getText().toString();
        if (chackedemail(userInfo[0])) {
            Toast.makeText(this, "email alrady exist", Toast.LENGTH_LONG).show();
            return;
        }
        userInfo[1] = pass.getText().toString();
        userInfo[2] = phone.getText().toString();
        userInfo[3] = name.getText().toString();
        userInfo[4] = rb.getText().toString();

        contentValues.put(DBHelper.USER_ID, userInfo[0]);
        contentValues.put(DBHelper.USER_PASS, userInfo[1]);
        contentValues.put(DBHelper.USER_PHONE, userInfo[2]);
        contentValues.put(DBHelper.USER_NAME, userInfo[3]);
        contentValues.put(DBHelper.USER_USER, userInfo[4]);

        db.insert(DBHelper.USERS_TABLE, null, contentValues);


        db.close();

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("name" ,userInfo[3]);
        startActivity(intent);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}