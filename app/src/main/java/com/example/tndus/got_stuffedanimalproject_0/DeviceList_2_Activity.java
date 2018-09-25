package com.example.tndus.got_stuffedanimalproject_0;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;

public class DeviceList_2_Activity extends Activity {
    private ArrayAdapter<String> mPairedDeviceArrauAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdaoter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

    }
}
