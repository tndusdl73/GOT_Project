package com.example.tndus.got_stuffedanimalproject_0;

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Set;

public class FindingService extends Service {

    private static final String TAG = "Service";
    private static boolean baby_flag = true;


    /**
     * Member fields
     */
    private static BluetoothAdapter mBtAdapter;

    /**
     * Newly discovered devices
     */
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    SharedPreferences auto_login;
    SharedPreferences.Editor editor;

    public bt_CheckThread thread = new bt_CheckThread();

    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        Log.d("Service", "서비스의 onBind");

        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("Service", "서비스의 onCreate");


        auto_login = getSharedPreferences("auto", Activity.MODE_PRIVATE);   // 프리퍼런스를 생성하고 auto라는 이름을 붙혀준다
        editor = auto_login.edit();



        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
//        ArrayAdapter<String> pairedDevicesArrayAdapter =
//                new ArrayAdapter<String>(this, R.layout.device_name);
//        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
//        if (pairedDevices.size() > 0) {
////            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
//            for (BluetoothDevice device : pairedDevices) {
//                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//            }
//        } else {
//            String noDevices = getResources().getText(R.string.none_paired).toString();
//            pairedDevicesArrayAdapter.add(noDevices);
//        }

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        Log.d("Service", "서비스의 onStartCommand");


        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // 서비스가 종료될 때 실행
//
//        thread.interrupt();
//
//        Log.d("Service", "서비스의 onDestroy");
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        Log.d("service", "디스트로이이이이잉니ㅣㅣㅣ이이ㅣ시바라랄ㄹㄹㄹ");
//        thread.notify();

//        thread.interrupt();
        thread.threadStop(true);

//        thread.stop();
//        thread.interrupt();
//        stopSelf();

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }


    public static class bt_CheckThread extends Thread {
        private static final String TAG = "CheckThread";
        private boolean stop;
        public bt_CheckThread() {
            // 초기화 작업
            this.stop = false;
        }
        public void run() {
            int second = 0;
//            while (!Thread.currentThread().isInterrupted()) {
            while(!stop){
                second++;
                try {
                    // 스레드에게 수행시킬 동작들 구현

                    baby_flag = false;

                    Log.d("ScanDevice", "쓰레드 시작,, flag는 " + baby_flag);

                    doDiscovery();

                    Thread.sleep(15000); // 10초간 Thread를 잠재운다

                }

                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.i("경과된 시간 : ", Integer.toString(second));
            }
            Log.d(TAG, "쓰레드 실행 종료");
        }
        public void threadStop(boolean stop){
            this.stop = stop;
        }

    }





    /**
     * Start device discover with the BluetoothAdapter
     */
    private static void doDiscovery() {
        Log.d(TAG, "doDiscovery()");

        // Indicate scanning in the title
//        setTitle("미아방지모드 작동중...");

        // Turn on sub-title for new devices
//        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }


    /**
     * The BroadcastReceiver that listens for discovered devices and changes the title when
     * discovery is finished
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            Log.d("Broadcasttt", "도착");

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    if(device.getAddress().equals(auto_login.getString("Mac", ""))){
                        baby_flag = true;

                        Log.d("ScanDevice", "둘이 똑같애!!");
                    }
                    Log.d("ScanDevice", device.getAddress() + "   " + auto_login.getString("Mac", ""));
                }
                // When discovery is finished, change the Activity title
            }

            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//                setProgressBarIndeterminateVisibility(false);
//                if (mNewDevicesArrayAdapter.getCount() == 0) {
//                    String noDevices = getResources().getText(R.string.none_found).toString();
//                    mNewDevicesArrayAdapter.add(noDevices);
//
//                }
                Log.d("ScanDevice", String.valueOf(baby_flag));

                if(baby_flag == true){
                    Toast.makeText(context, "근처에 아이가 있어요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("ScanDevice", "애가 없어졌어요");
                    Toast.makeText(context, "아이가 없어졌어요!!!", Toast.LENGTH_SHORT).show();
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);// (Context.VIBRATE_SERVICE)
                    long millisecond = 1000;  // 0.2초
                    vibrator.vibrate(millisecond);
                }
            }
        }
    };



}
