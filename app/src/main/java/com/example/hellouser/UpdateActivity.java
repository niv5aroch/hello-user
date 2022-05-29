package com.example.hellouser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.text.style.UpdateAppearance;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ImageView imgv;
    ListView thelistv;
    ArrayAdapter<String> arrayAdapter;
    Intent data;
    Intent menuIntent;
    String isadmin,name;
    String[]mess=new String[]{"I will arrive in5 min","I will be late in 5min","I am here!","open the door","are u ready","I will arrive in 10 min"};
    SQLiteDatabase db;
    DBHelper dbHelper;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    String sms="";
    String num="";
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        thelistv=findViewById(R.id.thelistv);
        thelistv.setOnItemClickListener(this);
        Intent intent=getIntent();
        name=intent.getExtras().getString("name");
        isadmin=intent.getExtras().getString("isadmin");
        imgv = findViewById(R.id.imgv);
        ActivityCompat.requestPermissions(UpdateActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
        arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mess);
        thelistv.setAdapter(arrayAdapter);

         dbHelper=new DBHelper(this);
         db=dbHelper.getReadableDatabase();
    }




    public void hehe(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    public void camera(View view) {
        if(checkAndRequestPermissions(this)){
            chooseImage(this);
        }
    }

    private void chooseImage(Context context){
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit" }; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(optionsMenu[i].equals("Take Photo")){
                    // Open the camera and get the photo
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else if(optionsMenu[i].equals("Choose from Gallery")){
                    // choose from  external storage
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                }
                else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }
    // function to check permission
    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) { listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) { listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        c = db.query(DBHelper.USERS_ADDTABLE, null, null, null, null, null, null);
        c.moveToFirst();
        num = c.getString(1);
        sms = arrayAdapter.getItem(position);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            Intent mySMS = new Intent(Intent.ACTION_SEND);
            mySMS.putExtra("text", mess);

            c.moveToFirst();
             num = c.getString(1);
             sms = arrayAdapter.getItem(position);

            smsManager.sendTextMessage(num, null, sms, null, null);
        }
        else{ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},100);
        }
        db.close();
    }
    // Handled permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            Intent mySMS = new Intent(Intent.ACTION_SEND);
            mySMS.putExtra("text", mess);
            Cursor c = db.query(DBHelper.USERS_ADDTABLE, null, null, null, null, null, null);
            c.moveToFirst();

            smsManager.sendTextMessage(num, null, sms, null, null);
        }
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Your Storage.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    chooseImage(this);
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imgv.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imgv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
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

}