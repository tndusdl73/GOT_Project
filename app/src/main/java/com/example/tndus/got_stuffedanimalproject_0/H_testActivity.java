package com.example.tndus.got_stuffedanimalproject_0;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class H_testActivity extends AppCompatActivity {
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private BluetoothAdapter mBluetoothAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_test);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "불가능", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }

    public void onStart() {
        super.onStart();

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }
    private void ensureDiscoverable(){
        if(mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }
}


//        if(mBlueToothAdapter == null){
//            // 만약 블루투스 adapter가 없으면, 블루투스를 지원하지 않는 기기이거나 블루투스 기능을 끈 기기이다.
//        }else{
//            // 블루투스 adapter가 있으면, 블루투스 adater에서 페어링된 장치 목록을 불러올 수 있다.
//            Set pairDevices = mBlueToothAdapter.getBondedDevices();
//
//            //페어링된 장치가 있으면
////            if(pairDevices.size()>0){
////                for(BluetoothDevice device : pairDevices){
////                    //페어링된 장치 이름과, MAC주소를 가져올 수 있다.
////                    Log.d("TEST", device.getName().toString() +" Device Is Connected!");
////                    Log.d("TEST", device.getAddress().toString() +" Device Is Connected!");
////                }
////            }else{
////                Toast.makeText(getApplicationContext(), "no Device", Toast.LENGTH_SHORT).show();
////            }
//        }

        //브로드캐스트리시버를 이용하여 블루투스 장치가 연결이 되고, 끊기는 이벤트를 받아 올 수 있다.
//        BroadcastReceiver bluetoothReceiver =  new BroadcastReceiver(){
//            public void onReceive(Context context, Intent intent) {
//                String action = intent.getAction();
//                //연결된 장치를 intent를 통하여 가져온다.
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//
//                //장치가 연결이 되었으면
//                if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
//                    Log.d("TEST", device.getName().toString() +" Device Is Connected!");
//                    //장치의 연결이 끊기면
//                }else if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
//                    Log.d("TEST", device.getName().toString() +" Device Is DISConnected!");
//
//                }
//            }
//        };
//
//        //MUST unregisterReceiver(bluetoothReceiver) in onDestroy()
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
//        registerReceiver(bluetoothReceiver, filter);
//        filter = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
//        registerReceiver(bluetoothReceiver, filter);


//
//    BluetoothAdapter mBluetoothAdapter;
//
//    //블루투스 요청 액티비티 코드
//    final static int BLUETOOTH_REQUEST_CODE = 100;
//
//    //UI
//    TextView txtState;
//    Button btnSearch;
//    CheckBox chkFindme;
//    ListView listPaired;
//    ListView listDevice;
//
//    //Adapter
//    SimpleAdapter adapterPaired;
//    SimpleAdapter adapterDevice;
//
//    //list - Device 목록 저장
//    List<Map<String, String>> dataPaired = new ArrayList<>();
//    List<Map<String, String>> dataDevice = new ArrayList<>();
//
////    private ArrayList<HashMap<String, String>> Data = new ArrayList<HashMap<String, String>>();
//
//    List<BluetoothDevice> bluetoothDevices;
//    int selectDevice;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_h_test);
//
//        txtState = (TextView) findViewById(R.id.txtState);
//        chkFindme = (CheckBox) findViewById(R.id.chkFindme);
//        btnSearch = (Button) findViewById(R.id.btnSearch);
//        listPaired = (ListView) findViewById(R.id.listPaired);
//        listDevice = (ListView) findViewById(R.id.listDevice);
//
//        adapterPaired = new SimpleAdapter(this, dataPaired, android.R.layout.simple_list_item_2, new String[]{"name", "address"}, new int[]{android.R.id.text1, android.R.id.text2});
//        listPaired.setAdapter(adapterPaired);
//
//        adapterDevice = new SimpleAdapter(this, dataDevice, android.R.layout.simple_list_item_2, new String[]{"name", "address"}, new int[]{android.R.id.text1, android.R.id.text2});
//        listDevice.setAdapter(adapterDevice);
//
//
//        //검색된 블루투스 디바이스 데이터
//        bluetoothDevices = new ArrayList<>();
//        //선택한 디바이스 없음
//        selectDevice = -1;
//
//        //블루투스 지원 유무 확인
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//        //블루투스를 지원하지 않으면 null을 리턴한다
//        if (mBluetoothAdapter == null) {
//            Toast.makeText(this, "블루투스를 지원하지 않는 단말기 입니다.", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//
//        //블루투스 브로드캐스트 리시버 등록
//        //리시버1
//        IntentFilter stateFilter = new IntentFilter();
//        stateFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED); //BluetoothAdapter.ACTION_STATE_CHANGED : 블루투스 상태변화 액션
//        registerReceiver(mBluetoothStateReceiver, stateFilter);
//        //리시버2
//        IntentFilter searchFilter = new IntentFilter();
//        searchFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED); //BluetoothAdapter.ACTION_DISCOVERY_STARTED : 블루투스 검색 시작
//        searchFilter.addAction(BluetoothDevice.ACTION_FOUND); //BluetoothDevice.ACTION_FOUND : 블루투스 디바이스 찾음
//        searchFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED); //BluetoothAdapter.ACTION_DISCOVERY_FINISHED : 블루투스 검색 종료
//        searchFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//        registerReceiver(mBluetoothSearchReceiver, searchFilter);
//        //리시버3
//        IntentFilter scanmodeFilter = new IntentFilter();
//        scanmodeFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
//        registerReceiver(mBluetoothScanmodeReceiver, scanmodeFilter);
//
//        //1. 블루투스가 꺼져있으면 활성화
////        if(!mBluetoothAdapter.isEnabled()){
////            mBluetoothAdapter.enable(); //강제 활성화
////        }
//
//        //2. 블루투스가 꺼져있으면 사용자에게 활성화 요청하기
//        if (!mBluetoothAdapter.isEnabled()) {
//            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(intent, BLUETOOTH_REQUEST_CODE);
//        } else {
//            GetListPairedDevice();
//        }
//
//        //검색된 디바이스목록 클릭시 페어링 요청
//        listDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                BluetoothDevice device = bluetoothDevices.get(position);
//
//                try {
//                    //선택한 디바이스 페어링 요청
//                    Method method = device.getClass().getMethod("createBond", (Class[]) null);
//                    method.invoke(device, (Object[]) null);
//                    selectDevice = position;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        listPaired.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
//    }
//
//
//    //블루투스 상태변화 BroadcastReceiver
//    BroadcastReceiver mBluetoothStateReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //BluetoothAdapter.EXTRA_STATE : 블루투스의 현재상태 변화
//            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
//
//            //블루투스 활성화
//            if (state == BluetoothAdapter.STATE_ON) {
//                txtState.setText("블루투스 활성화");
//            }
//            //블루투스 활성화 중
//            else if (state == BluetoothAdapter.STATE_TURNING_ON) {
//                txtState.setText("블루투스 활성화 중...");
//            }
//            //블루투스 비활성화
//            else if (state == BluetoothAdapter.STATE_OFF) {
//                txtState.setText("블루투스 비활성화");
//            }
//            //블루투스 비활성화 중
//            else if (state == BluetoothAdapter.STATE_TURNING_OFF) {
//                txtState.setText("블루투스 비활성화 중...");
//            }
//        }
//    };
//
//    //블루투스 검색결과 BroadcastReceiver
//    BroadcastReceiver mBluetoothSearchReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            switch (action) {
//                //블루투스 디바이스 검색 종료
//                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
//                    dataDevice.clear();
//                    bluetoothDevices.clear();
//                    Toast.makeText(H_testActivity.this, "블루투스 검색 시작", Toast.LENGTH_SHORT).show();
//                    break;
//                //블루투스 디바이스 찾음
//                case BluetoothDevice.ACTION_FOUND:
//                    //검색한 블루투스 디바이스의 객체를 구한다
//
//                    Log.d("Search", "founded");
//
//                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                    //데이터 저장
//                    Map map = new HashMap();
//                    map.put("name", device.getName()); //device.getName() : 블루투스 디바이스의 이름
//                    map.put("address", device.getAddress()); //device.getAddress() : 블루투스 디바이스의 MAC 주소
//
//                    Log.d("Search", "founded_2");
//
//                    dataDevice.add(map);
//                    //리스트 목록갱신
//                    Log.d("Search", "founded_3");
//                    adapterDevice.notifyDataSetChanged();
//                    Log.d("Search", "founded_4");
//
//                    //블루투스 디바이스 저장
//                    bluetoothDevices.add(device);
//                    break;
//                //블루투스 디바이스 검색 종료
//                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
//                    Toast.makeText(H_testActivity.this, "블루투스 검색 종료", Toast.LENGTH_SHORT).show();
//                    btnSearch.setEnabled(true);
//                    break;
//                //블루투스 디바이스 페어링 상태 변화
//                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
//                    BluetoothDevice paired = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                    if (paired.getBondState() == BluetoothDevice.BOND_BONDED) {
//                        //데이터 저장
//
//                        Log.d("Search", "change");
//
//                        Map map2 = new HashMap();
//                        map2.put("name", paired.getName()); //device.getName() : 블루투스 디바이스의 이름
//                        map2.put("address", paired.getAddress()); //device.getAddress() : 블루투스 디바이스의 MAC 주소
//                        dataPaired.add(map2);
//                        //리스트 목록갱신
//                        adapterPaired.notifyDataSetChanged();
//
//                        //검색된 목록
//                        if (selectDevice != -1) {
//                            bluetoothDevices.remove(selectDevice);
//
//                            dataDevice.remove(selectDevice);
//                            adapterDevice.notifyDataSetChanged();
//                            selectDevice = -1;
//                        }
//                    }
//                    break;
//            }
//        }
//    };
//
//    //블루투스 검색응답 모드 BroadcastReceiver
//    BroadcastReceiver mBluetoothScanmodeReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, -1);
//            switch (state) {
//                case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
//                case BluetoothAdapter.SCAN_MODE_NONE:
//                    chkFindme.setChecked(false);
//                    chkFindme.setEnabled(true);
//                    Toast.makeText(H_testActivity.this, "검색응답 모드 종료", Toast.LENGTH_SHORT).show();
//                    break;
//                case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
//                    Toast.makeText(H_testActivity.this, "다른 블루투스 기기에서 내 휴대폰을 찾을 수 있습니다.", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };
//
//
//    //블루투스 검색 버튼 클릭
//    public void mOnBluetoothSearch(View v) {
//        //검색버튼 비활성화
//        btnSearch.setEnabled(false);
//        //mBluetoothAdapter.isDiscovering() : 블루투스 검색중인지 여부 확인
//        //mBluetoothAdapter.cancelDiscovery() : 블루투스 검색 취소
//        if (mBluetoothAdapter.isDiscovering()) {
//            mBluetoothAdapter.cancelDiscovery();
//        }
//        //mBluetoothAdapter.startDiscovery() : 블루투스 검색 시작
//        mBluetoothAdapter.startDiscovery();
//    }
//
//
//    //검색응답 모드 - 블루투스가 외부 블루투스의 요청에 답변하는 슬레이브 상태
//    //BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE : 검색응답 모드 활성화 + 페이지 모드 활성화
//    //BluetoothAdapter.SCAN_MODE_CONNECTABLE : 검색응답 모드 비활성화 + 페이지 모드 활성화
//    //BluetoothAdapter.SCAN_MODE_NONE : 검색응답 모드 비활성화 + 페이지 모드 비활성화
//    //검색응답 체크박스 클릭
//    public void mOnChkFindme(View v) {
//        //검색응답 체크
//        if (chkFindme.isChecked()) {
//            if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) { //검색응답 모드가 활성화이면 하지 않음
//                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 60);  //60초 동안 상대방이 나를 검색할 수 있도록한다
//                startActivity(intent);
//            }
//        }
//    }
//
//    //이미 페어링된 목록 가져오기
//    public void GetListPairedDevice() {
//        Set<BluetoothDevice> pairedDevice = mBluetoothAdapter.getBondedDevices();
//
//        dataPaired.clear();
//        if (pairedDevice.size() > 0) {
//            for (BluetoothDevice device : pairedDevice) {
//                //데이터 저장
//                Map map = new HashMap();
//                map.put("name", device.getName()); //device.getName() : 블루투스 디바이스의 이름
//                map.put("address", device.getAddress()); //device.getAddress() : 블루투스 디바이스의 MAC 주소
//                dataPaired.add(map);
//            }
//        }
//        //리스트 목록갱신
//        adapterPaired.notifyDataSetChanged();
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case BLUETOOTH_REQUEST_CODE:
//                //블루투스 활성화 승인
//                if (resultCode == Activity.RESULT_OK) {
//                    GetListPairedDevice();
//                }
//                //블루투스 활성화 거절
//                else {
//                    Toast.makeText(this, "블루투스를 활성화해야 합니다.", Toast.LENGTH_SHORT).show();
//                    finish();
//                    return;
//                }
//                break;
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        unregisterReceiver(mBluetoothStateReceiver);
//        unregisterReceiver(mBluetoothSearchReceiver);
//        unregisterReceiver(mBluetoothScanmodeReceiver);
//        super.onDestroy();
//    }
//}