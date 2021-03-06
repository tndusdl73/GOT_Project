package com.example.tndus.got_stuffedanimalproject_0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class BluetoothActivity extends Activity implements OnClickListener {
    // Debugging
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
        Log.e(TAG, "onCreate");

        setContentView(R.layout.activity_bluetooth);

        /** Main Layout **/
        btn_Connect = (Button) findViewById(R.id.btn_connect);
//        btn_Service = (Button) findViewById(R.id.btn_service);
//        service_off = (Button) findViewById(R.id.service_off);

        baby_service = (Switch) findViewById(R.id.switch1);

        btn_Connect.setOnClickListener(this);
//        btn_Service.setOnClickListener(this);
//        service_off.setOnClickListener(this);

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

        // BluetoothService 클래스 생성
        if (btService == null) {
            btService = new BluetoothService(this, mHandler);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_connect:
                if (btService.getDeviceState()) {
                    // 블루투스가 지원 가능한 기기일 때
                    btService.enableBluetooth();
                    btService.scanDevice();
                } else {
                    finish();
                }
                break;

//            case R.id.btn_service:
//                if (btService.getDeviceState()) {
//                    // 블루투스가 지원 가능한 기기일 때
//                    btService.enableBluetooth();
////                    btService.checkDevice();
//                    Intent serviceIntent = new Intent(getApplicationContext(), FindingService.class);
//                    startService(serviceIntent);
//
//                } else {
//                    finish();
//                }
//                break;
//
//            case R.id.service_off:
//                Intent serviceIntent = new Intent(getApplicationContext(), FindingService.class);
//                stopService(serviceIntent);
//                break;

        }

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