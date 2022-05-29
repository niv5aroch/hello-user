package com.example.hellouser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText id;
    EditText pass;
    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    ContentValues contentValues = new ContentValues();
    String[] userInfo = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id=findViewById(R.id.id);
        pass=findViewById(R.id.pass);
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        //db.close();
    }
    //public void onClick(View view)
    {

       // if(view.equals(btn)) {

        //userInfo[0] = id.getText().toString();
       // userInfo[1] = pass.getText().toString();
//
            //contentValues.put(DBHelper.USER_ID, userInfo[0]);
           // contentValues.put(DBHelper.USER_PASS, userInfo[1]);
           // db.insert(DBHelper.USERS_TABLE, null, contentValues);


            //  db.close();
    }

    public void register(View view)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void done(View view)
    {
        String strEmail="";
        String strPass="";
        Intent intent = new Intent(this, HomeActivity.class);

        if (!id.getText().toString().matches("")&&!id.getText().toString().matches("")){
            strEmail=id.getText().toString();
            strPass=pass.getText().toString();
            cursor=db.query(dbHelper.USERS_TABLE,null,null,null,null,null,null);
            cursor.moveToNext();
            while(!cursor.isAfterLast()){
                String str1=cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.USER_ID));
                String str2=cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.USER_PASS));
                if(str1.matches(strEmail)&&str2.matches(strPass)){
                    intent.putExtra("name",cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.USER_NAME)));
                    startActivity(intent);
                    db.close();

                }
             cursor.moveToNext();
            }
        }
        Toast.makeText(this,"אימייל או סיסמה אינם נכונים",Toast.LENGTH_LONG).show();
    }

       // Toast.makeText(this,"השדות חסרים",Toast.LENGTH_LONG).show();

}

