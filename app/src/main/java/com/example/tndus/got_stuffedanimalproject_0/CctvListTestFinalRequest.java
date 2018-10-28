package com.example.tndus.got_stuffedanimalproject_0;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CctvListTestFinalRequest extends StringRequest {
    final static private String URL = "http://tndusdl73.cafe24.com/getVideoList.php";
    private Map<String, String> parameters;

    public CctvListTestFinalRequest(String day, String userID, Response.Listener<String>listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("day",day);
        parameters.put("userID",userID);

    }
    @Override
    public Map<String,String> getParams(){
        return parameters;

    }
}
