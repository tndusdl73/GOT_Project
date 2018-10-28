package com.example.tndus.got_stuffedanimalproject_0;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CctvListTestFinalActivity extends AppCompatActivity {

    String day;    //선택된 날짜
    String userID;  //해당 유저 아이디

    String videoFileName,videoTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv_list_test);
        //test

     }

    protected void onResume() {

        insertToDatabase();
        super.onResume();
    }

    public void insertToDatabase(){
        Intent intent = getIntent();
        final String day = intent.getStringExtra("date");
        final String userID = intent.getStringExtra("userID");

        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){


                        //Log.d("로그인 pre", userID);
/*
                        String userID = jsonResponse.getString("userID");
                        String userPassword = jsonResponse.getString("userPassword");
                        String userName = jsonResponse.getString("userName");       //null값......왜...?
  */
                        String fileName = jsonResponse.getString("fileName");
                        String title = jsonResponse.getString("title");

                        /*
                        Log.d("userID",userID);
                        Log.d("userPassword",userPassword);
                        Log.d("userName",userName);
                        Log.d("userAge",userAge);
*/
                        Log.d("fileName",fileName);
                        Log.d("title",title);

                        videoFileName = fileName;
                        videoTitle =title;

                        Log.d("videoFileName",videoFileName);
                        Log.d("videTitle",videoTitle);

                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(CctvListTestFinalActivity.this);
                        builder.setMessage("회원 정보를 가져오는데 실패하였습니다.")
                                .setNegativeButton("다시 시도",null)
                                .create()
                                .show();
                    }
                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        };


        CctvListTestFinalRequest loginRequest = new CctvListTestFinalRequest(day,userID,responseListener);
        RequestQueue queue = Volley.newRequestQueue(CctvListTestFinalActivity.this);
        queue.add(loginRequest);
    }


}
