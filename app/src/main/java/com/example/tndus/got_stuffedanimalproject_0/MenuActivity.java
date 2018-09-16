package com.example.tndus.got_stuffedanimalproject_0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button readBookButton = (Button) findViewById(R.id.readBookButton);
        Button cctvButton = (Button) findViewById(R.id.cctvButton);
        Button mypageButton = (Button) findViewById(R.id.mypageButton);
        Button settingButton = (Button) findViewById(R.id.settingButton);

        readBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readBookIntent = new Intent(MenuActivity.this, ReadBookActivity.class);
                MenuActivity.this.startActivity(readBookIntent);
            }
        });

        cctvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cctvIntent = new Intent(MenuActivity.this, CctvActivity.class);
                MenuActivity.this.startActivity(cctvIntent);
            }
        });

        mypageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypageIntent = new Intent(MenuActivity.this, MypageActivity.class);
                MenuActivity.this.startActivity(mypageIntent);
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingIntent = new Intent(MenuActivity.this, SettingActivity.class);
                MenuActivity.this.startActivity(settingIntent);
            }
        });

    }
}
