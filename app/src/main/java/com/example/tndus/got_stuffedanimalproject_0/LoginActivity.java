package com.example.tndus.got_stuffedanimalproject_0;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

     EditText idText;
     EditText passwordText;

     Button loginButton;
     Button btn_test;                             //for test

     TextView registerButton;

    CheckBox ckbox_autologin;


    SharedPreferences auto_login;
    SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //test


         idText = (EditText)findViewById(R.id.idText);
         passwordText = (EditText)findViewById(R.id.passwordText);

         loginButton = (Button)findViewById(R.id.loginButton);
         btn_test= (Button)findViewById(R.id.btn_test);                             //for test

         registerButton = (TextView)findViewById(R.id.registerButton);

         ckbox_autologin = (CheckBox)findViewById(R.id.ckbox_autologin);

        auto_login = getSharedPreferences("auto", Activity.MODE_PRIVATE);   // 프리퍼런스를 생성하고 auto라는 이름을 붙혀준다
        editor = auto_login.edit();



        if(auto_login.getBoolean("Auto_Login_enabled", false)){ // auto_login이  가지고있는 "Auto_Login_enable" 항목(자동로그인) 이 false이면 => (자동로그인이 체크돼있으면)

            idText.setText(auto_login.getString("ID", ""));
            passwordText.setText(auto_login.getString("PW", ""));    //id, password에 auto_login이 가지고 있는 프리퍼런스 값을 넣는다
            ckbox_autologin.setChecked(true);   //  체크박스도 체크해둔다

            insertToDatabase();     // id 체크
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertToDatabase();
//                final String userID = idText.getText().toString();
//                final String userPassword = passwordText.getText().toString();
//
//                Response.Listener<String> responseListener = new Response.Listener<String>(){
//
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//                            if(success){
//                                String userID = jsonResponse.getString("userID");
//                                String userPassword = jsonResponse.getString("userPassword");
//                                Intent intent = new Intent(LoginActivity.this,  MenuActivity.class);
//                                intent.putExtra("userID",userID);
//                                intent.putExtra("userPassword",userPassword);
//                                LoginActivity.this.startActivity(intent);
//
//                            }else{
//                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                builder.setMessage("로그인에 실패하였습니다.")
//                                        .setNegativeButton("다시 시도",null)
//                                        .create()
//                                        .show();
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//
//                        }
//                    }
//                };
//                LoginRequest loginRequest = new LoginRequest(userID,userPassword,responseListener);
//                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                queue.add(loginRequest);
            }
        });



        btn_test.setOnClickListener(new View.OnClickListener() {                // for test
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
                startActivity(intent);

            }
        });


    }

    public void insertToDatabase(){
        final String userID = idText.getText().toString();
        final String userPassword = passwordText.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){

                        String ID = idText.getText().toString();

                        if(ckbox_autologin.isChecked()){    //자동로그인이 눌려있으면

                            String PW = passwordText.getText().toString();

                            editor.putString("PW", PW);     //preference에 password 값과 자동로그인을 넣어둔다
                            editor.putBoolean("Auto_Login_enabled", true);
                        }else{
                            editor.clear();     //자동로그인이 안눌려져있으면 자동로그인 정보들을 지운다.
                        }

                        Log.d("로그인 pre", ID);
                        editor.putString("ID", ID);
                        editor.commit();


                        String userID = jsonResponse.getString("userID");
                        String userPassword = jsonResponse.getString("userPassword");

                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);

                        intent.putExtra("userID",userID);
                        intent.putExtra("userPassword",userPassword);

                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);

                        finish();


                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("로그인에 실패하였습니다.")
                                .setNegativeButton("다시 시도",null)
                                .create()
                                .show();
                    }
                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(userID,userPassword,responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }


}
