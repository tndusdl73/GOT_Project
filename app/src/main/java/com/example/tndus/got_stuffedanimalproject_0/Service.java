package com.example.tndus.got_stuffedanimalproject_0;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Service extends android.app.Service {
    private static final String TAG = "Service";
    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d(TAG, "서비스의 onCreate");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        Log.d(TAG, "서비스의 onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행
        Log.d(TAG, "서비스의 onDestroy");
        Toast.makeText(this, "아이가 멀어졌어요!!", Toast.LENGTH_SHORT).show();


//        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);// (Context.VIBRATE_SERVICE)
//        long millisecond = 1000;  // 1초
//        vibrator.vibrate(millisecond);
//
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//
//        // 제목셋팅
//        alertDialogBuilder.setTitle("미아방지모드 실행");
//
//        // AlertDialog 셋팅
//        alertDialogBuilder
//                .setMessage("아이가 멀어졌어요!!!!")
//                .setCancelable(false);
//
//        // 다이얼로그 생성
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        // 다이얼로그 보여주기
//        alertDialog.show();



    }

}
