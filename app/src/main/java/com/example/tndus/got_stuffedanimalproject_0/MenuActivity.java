package com.example.tndus.got_stuffedanimalproject_0;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    SharedPreferences sp_id;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");
        String message = "환영합니다," + userID + "님!";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        sp_id = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        editor = sp_id.edit();


        Button logoutButton= (Button) findViewById(R.id.button_logout);
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

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                editor.clear();
                editor.commit();

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);

                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                startActivity(intent);

                finish();

            }
        });


    }
}
