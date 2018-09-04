package com.example.tndus.got_stuffedanimalproject_0;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText)findViewById(R.id.idText);
        final EditText passwordText = (EditText)findViewById(R.id.passwordText);
        final EditText nameText = (EditText)findViewById(R.id.nameText);
        final EditText ageText = (EditText)findViewById(R.id.ageText);

        Button registerButton = (Button)findViewById(R.id.registerButton);


        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassWord = passwordText.getText().toString();
                final String userName = nameText.getText().toString();
                int userAge = Integer.parseInt(ageText.getText().toString());
                Log.d("Register", "onCliack: aa");

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Register", "onCliack: aa");
                        try {

                            Log.d("Register", "onCliack: bb");
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
//                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                                builder.setMessage("회원 등록에 성공했습니다.")
//                                        .setPositiveButton("확인",null)
//                                        .create()
//                                        .show();
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공했습니다.", Toast.LENGTH_LONG).show();
                                Log.d("Register", "success");
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else{
//                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                                builder.setMessage("회원 등록에 실패했습니다.")
//                                        .setNegativeButton("다시시도",null)
//                                        .create()
//                                        .show();
                                Toast.makeText(getApplicationContext(), "회원 등록에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_LONG).show();
                                Log.d("Register", "Fail");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Log.d("Register", "onCliack: aa");
                RegisterRequest registerRequest = new RegisterRequest(userID,userPassWord,userName,userAge,responseListener);
                Log.d("Register", "onCliack: xx");
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                Log.d("Register", "onCliack: aa");

                queue.add(registerRequest);
                Log.d("Register", "onCliack: aa");

            }
        });

    }
}
