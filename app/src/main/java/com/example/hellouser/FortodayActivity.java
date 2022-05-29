package com.example.hellouser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FortodayActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String str1,str2,str3;
    Button btnCreate;
    ArrayList<String> list=new ArrayList<String>();
    ListView viewlist;
    ArrayAdapter<String> arrayAdapter;
    Intent intent=getIntent();
    Intent menuIntent;
    String isadmin,name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fortoday);
       viewlist=findViewById(R.id.viewlist);
        btnCreate=findViewById(R.id.btnCreate);
        btnCreate.setVisibility(View.INVISIBLE);
         intent=getIntent();
       viewlist.setOnItemClickListener(this);
        name=intent.getExtras().getString("name");
        isadmin=intent.getExtras().getString("isadmin");
        if (isadmin.equals("admin"))
        {
            btnCreate.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper=new DBHelper(this);
        db=dbHelper.getWritableDatabase();
        cursor=db.query(DBHelper.USERS_ADDTABLE,null,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {

            if (cursor.getString(2).equals(name)){
                str1=cursor.getString(0);
                str2=cursor.getString(1);
                str3=str1+"\n"+str2;
                list.add(str3);
            }

            cursor.moveToNext();

        }

        arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        viewlist.setAdapter(arrayAdapter);

    }

    //לקרוא את היעדים מהדטה בייס
    //להציג אותם בליסט וויו
    //להוסיף מאזין לליסט וויו שקשלוחצים עליו יציג מפה
    public void create(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String str=list.get(position);
        Uri geoUri=Uri.parse(str);
        Intent intent=new Intent(Intent.ACTION_VIEW,geoUri);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
switch (id)
{
    case R.id.main:
        menuIntent= new Intent(this, MainActivity.class);
        startActivity(menuIntent);
        break;
    case R.id.home:
        menuIntent = new Intent(this, HomeActivity.class);
        menuIntent.putExtra("isadmin" ,isadmin);
        menuIntent.putExtra("name",name);
        startActivity(menuIntent);
        break;
    case R.id.register:
        menuIntent= new Intent(this, RegisterActivity.class);
        startActivity(menuIntent);
        break;
}
        return super.onOptionsItemSelected(item);
    }

    public void remove(View view)
    {
        String str=list.get(0);
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
        dbHelper.deleteFirstRow();

        db.close();
        list.remove(0);
        arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        viewlist.setAdapter(arrayAdapter);
        finish();
        startActivity(getIntent());
    }
}