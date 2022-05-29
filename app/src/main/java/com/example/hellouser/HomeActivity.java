package com.example.hellouser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
Intent take;
TextView tvname;
String isadmin;
    private BroadcastReceiver mNetworkReceiver;
    static TextView tv_check_connection;
    NetworkChangeReceiver networkChangeReceiver;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        take=getIntent();
         name=take.getExtras().getString("name");
         isadmin=take.getExtras().getString("isadmin");
        tvname=findViewById(R.id.tvname);
        tvname.setText("hello "+ name);
        networkChangeReceiver=new NetworkChangeReceiver();
        //isOnline(this);
        tv_check_connection=(TextView) findViewById(R.id.tv_check_connection);
        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();
    }
    public static void dialog(boolean value)
    {
        if(value)
        {
            tv_check_connection.setText("Online");
            tv_check_connection.setBackgroundColor(Color.GREEN);
            tv_check_connection.setTextColor(Color.WHITE);

            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable()
            {
                @Override
                public void run()
                {
                    tv_check_connection.setVisibility(View.GONE);
                }
            };
            handler.postDelayed(delayrunnable, 3000);
        }else
            {
            tv_check_connection.setVisibility(View.VISIBLE);
            tv_check_connection.setText("Could not Connect to internet");
            tv_check_connection.setBackgroundColor(Color.RED);
            tv_check_connection.setTextColor(Color.WHITE);
        }
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
    public void fortoday(View view) {
        Intent intent = new Intent(this, FortodayActivity.class);
        intent.putExtra("isadmin" ,isadmin);
        intent.putExtra("name",name);
        startActivity(intent);
    }

    public void update(View view) {
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra("isadmin" ,isadmin);
        intent.putExtra("name",name);
        startActivity(intent);
    }

    public void map(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("isadmin" ,isadmin);
        intent.putExtra("name",name);
        startActivity(intent);
    }


    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

   /* public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }*/
}
//“Yesterday is history,
//tomorrow is a mystery,
//and today is a gift...
//that's why they call it present”