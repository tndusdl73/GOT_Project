package com.example.tndus.got_stuffedanimalproject_0;
/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.Set;

/**
 * This Activity appears as a dialog. It lists any paired devices and
 * devices detected in the area after discovery. When a device is chosen
 * by the user, the MAC address of the device is sent back to the parent
 * Activity in the result Intent.
 */
public class DeviceList_2_Activity extends Activity {

    /**
     * Tag for Log
     */
    private static final String TAG = "DeviceListActivity";

    private static boolean baby_flag = true;

    /**
     * Return Intent extra
     */
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    /**
     * Member fields
     */
    private BluetoothAdapter mBtAdapter;

    /**
     * Newly discovered devices
     */
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    SharedPreferences auto_login;
    SharedPreferences.Editor editor;

    public CheckThread thread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the window
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.device_list2);

        // Set result CANCELED in case the user backs out
        setResult(Activity.RESULT_CANCELED);

        auto_login = getSharedPreferences("auto", Activity.MODE_PRIVATE);   // 프리퍼런스를 생성하고 auto라는 이름을 붙혀준다
        editor = auto_login.edit();



        // Initialize array adapters. One for already paired devices and
        // one for newly discovered devices
        ArrayAdapter<String> pairedDevicesArrayAdapter =
                new ArrayAdapter<String>(this, R.layout.device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

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
        if (pairedDevices.size() > 0) {
//            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = getResources().getText(R.string.none_paired).toString();
            pairedDevicesArrayAdapter.add(noDevices);
        }

        setProgressBarIndeterminateVisibility(true);

        thread = new CheckThread();

        thread.start();


    }

    private class CheckThread extends Thread {
        private static final String TAG = "CheckThread";
        public CheckThread() {
            // 초기화 작업

        }
        public void run() {
            int second = 0;
            while (true) {
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
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        thread.interrupt();

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
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
                setTitle("미아방지모드 실행중...");
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
                    long millisecond = 200;  // 0.2초
                    vibrator.vibrate(millisecond);
                }
            }
        }
    };

}
