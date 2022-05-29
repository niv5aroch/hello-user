package com.example.hellouser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
Intent take;
TextView tvname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        take=getIntent();
        String name=take.getExtras().getString("name");
        tvname=findViewById(R.id.tvname);
        tvname.setText("hello "+ name);

    }

    public void fortoday(View view) {
        Intent intent = new Intent(this, FortodayActivity.class);
        startActivity(intent);
    }

    public void update(View view) {
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);
    }

    public void map(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void fff(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}