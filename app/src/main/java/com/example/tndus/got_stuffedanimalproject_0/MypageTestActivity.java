package com.example.tndus.got_stuffedanimalproject_0;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MypageTestActivity extends AppCompatActivity {

    String userID;
    String userPassword;
    String userName;
    String userAge;

    TextView idText,nameText,ageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_test);

        idText = (TextView)findViewById(R.id.idText);
        nameText = (TextView)findViewById(R.id.nameText);
        ageText = (TextView)findViewById(R.id.ageText);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userPassword = intent.getStringExtra("userPassword");

        //Log.d("ddddddd",userID);        //userID변수에 아이디가 정상으로 들어감
        //idText.setText("dfdfdf");

        Log.d("eeeeeee",userID);
        insertToDatabase();
//---------------------------------
        userName = intent.getStringExtra("userName");
        userAge = intent.getStringExtra("userAge");
        Log.d("tetstetstsetsetset23233",userName);

        Log.d("32222222",userName);
        Log.d("1111111",userAge);
        nameText.setText(userName);
        ageText.setText(userAge);


    }


        public void insertToDatabase(){

            Response.Listener<String> responseListener = new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    Log.d("66666666","666666666");
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){

                            String userID = jsonResponse.getString("userID");
                            String userPassword = jsonResponse.getString("userPassword");
                            String userName = jsonResponse.getString("userName");
                            String userAge = jsonResponse.getString("userAge");;

                            Log.d("userID",userID);
                            Log.d("userPassword",userPassword);
                            Log.d("userName",userName);
                            Log.d("userAge",userAge);


                            Intent intent = new Intent(getApplicationContext(), MypageTestActivity.class);

                            intent.putExtra("userID",userID);
                            intent.putExtra("userPassword",userPassword);
                            intent.putExtra("userName",userName);
                            intent.putExtra("userAge",userAge);

                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(intent);

                            finish();


                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(MypageTestActivity.this);
                            builder.setMessage("회원 정보를 가져오는데 실패했습니다.")
                                    .setNegativeButton("다시 시도",null)
                                    .create()
                                    .show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();

                    }
                }
            };
            //Log.d("77777777777",userID);  //userID 잘 받아옴 확인.
            LoginRequest loginRequest = new LoginRequest(userID,userPassword,responseListener);
            RequestQueue queue = Volley.newRequestQueue(MypageTestActivity.this);
            queue.add(loginRequest);
        }
    }

