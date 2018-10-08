package com.example.tndus.got_stuffedanimalproject_0;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    SharedPreferences sp_id;
    SharedPreferences.Editor editor;

    private static final String TAG = "Main";

    // Intent request code
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout
    private Button btn_Connect;
//    private Button btn_Service;
//    private Button service_off;

    private Switch baby_service;

    private BluetoothService btService = null;

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    };



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

        baby_service = (Switch) findViewById(R.id.switch1);

        TextView tv_connect = (TextView) findViewById(R.id.tv_connect);

        String Macadd = sp_id.getString("Mac", "");

        if(Macadd != null){
            tv_connect.setText(Macadd + "기기가 등록되어있습니다.");
        }

        // BluetoothService 클래스 생성
        if (btService == null) {
            btService = new BluetoothService(this, mHandler);
        }


        readBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readBookIntent = new Intent(MenuActivity.this, AudioRecordActivity.class);
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
                if (btService.getDeviceState()) {
                    // 블루투스가 지원 가능한 기기일 때
                    btService.enableBluetooth();
                    btService.scanDevice();
                } else {
                    finish();
                }
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


        baby_service.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){  //  스위치가 체크되어있으면 ,, 미아방지모드가 켜지면
                    if (btService.getDeviceState()) {
                        // 블루투스가 지원 가능한 기기일 때
                        btService.enableBluetooth();
//                    btService.checkDevice();
                        Intent serviceIntent = new Intent(getApplicationContext(), FindingService.class);
                        startService(serviceIntent);

                    } else {
                        finish();
                    }
                }
                else{           //스위치가 체크 안돼있으면,, 미아방지모드가 꺼지면
                    Intent serviceIntent = new Intent(getApplicationContext(), FindingService.class);
                    stopService(serviceIntent);
                }
            }
        });



    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult, resultCode " + resultCode);
        Log.d(TAG, "onActivityResult, requestCode " + requestCode);

        switch (requestCode) {

            /** 추가된 부분 시작 **/
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    btService.getDeviceInfo(data);
                }
                break;
            /** 추가된 부분 끝 **/
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Next Step
                    btService.scanDevice();
                } else {

                    Log.d(TAG, "Bluetooth is not enabled");
                }
                break;
        }
    }
}