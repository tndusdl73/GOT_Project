package com.example.tndus.got_stuffedanimalproject_0;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EditUserInfoRequest extends StringRequest {
    final static private String URL = "http://tndusdl73.cafe24.com/EditUserInfo.php";
    private Map<String, String> parameters;

    public EditUserInfoRequest(String userID, String userPassword, String userName, int userAge, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);

        Log.d("request", "onCliack: aa");

        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("userName",userName);
        parameters.put("userAge",userAge +"" );



    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
