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

public class MypageActivity extends AppCompatActivity {

    TextView idText;
    TextView nameText;
    TextView ageText;
    Button editButton;

    String userId,userPw,userNa,userAg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        //test
        idText = (TextView)findViewById(R.id.idText);
        nameText = (TextView)findViewById(R.id.nameText);
        ageText=(TextView)findViewById(R.id.ageText);
        editButton=(Button)findViewById(R.id.editButton);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(MypageActivity.this,EditUserInfoActivity.class);
                editIntent.putExtra("userID",userId);
                editIntent.putExtra("userPassword",userPw);
                editIntent.putExtra("userName",userNa);
                editIntent.putExtra("userAge",userAg);
                MypageActivity.this.startActivity(editIntent);

                Intent intent = getIntent();
                idText.setText(intent.getStringExtra("userID"));
                nameText.setText(intent.getStringExtra("userName"));
                ageText.setText(intent.getStringExtra("userAge"));

            }
        });
        insertToDatabase();

     }

    public void insertToDatabase(){
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String userPassword = intent.getStringExtra("userPassword");

        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){

                        String ID = idText.getText().toString();

                        Log.d("로그인 pre", userID);

                        String userID = jsonResponse.getString("userID");
                        String userPassword = jsonResponse.getString("userPassword");
                        String userName = jsonResponse.getString("userName");       //null값......왜...?
                        String userAge = jsonResponse.getString("userAge");

                        Log.d("userID",userID);
                        Log.d("userPassword",userPassword);
                        Log.d("userName",userName);
                        Log.d("userAge",userAge);

                        userId=userID;
                        userNa=userName;
                        userAg=userAge;

                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                        builder.setMessage("회원 정보를 가져오는데 실패하였습니다.")
                                .setNegativeButton("다시 시도",null)
                                .create()
                                .show();
                    }
                }catch (Exception e){
                    e.printStackTrace();

                }
                idText.setText(userId);
                nameText.setText(userNa);
                ageText.setText(userAg);
            }
        };


        LoginRequest loginRequest = new LoginRequest(userID,userPassword,responseListener);
        RequestQueue queue = Volley.newRequestQueue(MypageActivity.this);
        queue.add(loginRequest);
    }


}
