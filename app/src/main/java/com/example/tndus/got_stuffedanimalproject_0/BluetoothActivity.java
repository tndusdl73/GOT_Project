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
import android.widget.Toast;

public class BluetoothActivity extends Activity implements OnClickListener {
    // Debugging
    private static final String TAG = "Main";

    // Intent request code
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout
    private Button btn_Connect;
    private Button btn_ble;
    private Button btn_Service_Start;
    private Button btn_Service_Stop;
    private Button btn_h_test;

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
        btn_ble = (Button) findViewById(R.id.btn_ble);
        btn_Service_Start = (Button) findViewById(R.id.btn_service_start);
        btn_Service_Stop = (Button) findViewById(R.id.btn_service_stop);
        btn_h_test = (Button) findViewById(R.id.btn_h_test);

        btn_Connect.setOnClickListener(this);
        btn_ble.setOnClickListener(this);
        btn_Service_Start.setOnClickListener(this);
        btn_Service_Stop.setOnClickListener(this);
        btn_h_test.setOnClickListener(this);

        // BluetoothService 클래스 생성
        if (btService == null) {
            btService = new BluetoothService(this, mHandler);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_ble:
                intent = new Intent(getApplicationContext(), BleActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_connect:
                if (btService.getDeviceState()) {
                    // 블루투스가 지원 가능한 기기일 때
                    btService.enableBluetooth();
                } else {
                    finish();
                }
                break;
            case R.id.btn_service_start:

                intent = new Intent(getApplicationContext(),//현재제어권자
                    Service.class); // 이동할 컴포넌트

                startService(intent); // 서비스 시작

                Toast.makeText(this, "Service Start", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_service_stop:
                intent = new Intent(getApplicationContext(),//현재제어권자
                        Service.class); // 이동할 컴포넌트

                stopService(intent); // 서비스 시작

                Toast.makeText(this, "Service Stop", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_h_test:
                intent = new Intent(getApplicationContext(), H_testActivity.class);
                startActivity(intent);
                break;

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