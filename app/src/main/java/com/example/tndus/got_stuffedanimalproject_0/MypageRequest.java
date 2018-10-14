package com.example.tndus.got_stuffedanimalproject_0;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MypageRequest extends StringRequest {
    final static private String URL = "http://tndusdl73.cafe24.com/Login.php";
    private Map<String, String> parameters;

    public MypageRequest(String userID, String userPassword, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        Log.d("77777777777",userID);
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        Log.d("7777777777557","putputputput");

        //parameters.put("userPassword",userPassword);

    }
    @Override
    public Map<String,String> getParams(){
        Log.d("cccccccccccc","fffffffffff");
        Log.d("paprpaprpar",parameters.toString());
        return parameters;
    }
}
