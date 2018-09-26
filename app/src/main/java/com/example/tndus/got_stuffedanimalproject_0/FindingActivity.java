package com.example.tndus.got_stuffedanimalproject_0;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class FindingActivity extends AppCompatActivity {

    private BluetoothService btService = null;

    SharedPreferences auto_login;
    SharedPreferences.Editor editor;


    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding);
        // BluetoothService 클래스 생성

        auto_login = getSharedPreferences("auto", Activity.MODE_PRIVATE);   // 프리퍼런스를 생성하고 auto라는 이름을 붙혀준다
        editor = auto_login.edit();

        String Macadd = auto_login.getString("Mac", "");

        if (btService == null) {
            btService = new BluetoothService(this, mHandler);
        }

        if (btService.getDeviceState()) {
            // 블루투스가 지원 가능한 기기일 때
            btService.enableBluetooth();
        }
        btService.checkDevice();
    }


//        CheckThread thread = new CheckThread();
//
//        thread.start();

//    }

//    private class CheckThread extends Thread {
//        private static final String TAG = "ExampleThread";
//        public CheckThread() {
//            // 초기화 작업
//        }
//        public void run() {
//            int second = 0;
//            while (true) {
//                second++;
//                try {
//                    // 스레드에게 수행시킬 동작들 구현
//
//                    btService.checkDevice();
//
//                    Thread.sleep(1000); // 1초간 Thread를 잠재운다
//
//                    Log.d("Finding", String.valueOf(second));
//                }
//
//                catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                Log.i("경과된 시간 : ", Integer.toString(second));
//            }
//        }
//    }

}
