package com.example.tndus.got_stuffedanimalproject_0;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CctvListActivity extends AppCompatActivity {

    String day;    //선택된 날짜
    String userID;  //해당 유저 아이디

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv_list);

        TextView selectDateText = (TextView)findViewById(R.id.textView);
        ListView cctvListView = (ListView)findViewById(R.id.cctvListView);

        Intent intent = getIntent();
        day = intent.getStringExtra("date");

        selectDateText.setText(day+"의 영상 목록");
        Log.d("777777777",day);

        insertToDatabase();

    }

    public void insertToDatabase(){

        Intent intent = getIntent();
        final String day = intent.getStringExtra("date");
        final String userID = intent.getStringExtra("userID");

        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    Log.d("777777777","333333333333");
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if(success){

                        String title = jsonResponse.getString("title");
                        String index = jsonResponse.getString("index");
                        String fileName = jsonResponse.getString("fileName");

                        Log.d("onsertwoTrhrer",title);
                        Log.d("odfjsoidf",index);
                        Log.d("djfdkjfsldkjfs",fileName);

                    }else{
                        String title = jsonResponse.getString("title");
                        Log.d("1234456678989",title);
                        AlertDialog.Builder builder = new AlertDialog.Builder(CctvListActivity.this);
                        builder.setMessage("DB에서 영상 목록 정보를 가져오는데 실패했습니다.")
                                .setNegativeButton("다시 시도",null)
                                .create()
                                .show();
                    }
                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        };
        CctvListRequest cctvListRequest = new CctvListRequest(day,userID,responseListener);
        RequestQueue queue = Volley.newRequestQueue(CctvListActivity.this);
        queue.add(cctvListRequest);
        Log.d("777777777","222222222");
        Log.d("777777777",day);
        Log.d("777777774561237",userID);
    }
}
