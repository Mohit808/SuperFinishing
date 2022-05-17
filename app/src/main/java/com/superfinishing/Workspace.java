package com.superfinishing;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Workspace {
    public static String currentUser;
    public static String myName;
    public static String myImage;


    public static void sendMessage(Context context, final String topic, final HashMap<String, String> hashMap){
        final String url = "https://fcm.googleapis.com/fcm/send";
        StringRequest myReq = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
//                        Toast.makeText(MainActivity.this, "Bingo Success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, "Oops error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public byte[] getBody() throws com.android.volley.AuthFailureError {
                Map<String, Object> rawParameters = new Hashtable();
                HashMap dataMap=new HashMap();
                dataMap.put("data",new JSONObject(hashMap));
                rawParameters.put("data",dataMap);
//                        rawParameters.put("notification",new JSONObject(hashMap));
                rawParameters.put("to", "/topics/"+topic);
                return new JSONObject(rawParameters).toString().getBytes();
            };
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "key=AAAA7A3SUdI:APA91bFPByyCvMU6ouia85OTCPpEIq38IrUc-qMPgUaROIH3MqZ8qsJFLLut5xv0pbzVKem2zw9WTK8tfTZ-oydbeBL6j-a0o5r2XjLK3sf26c3-TEieeS4HJm3e-usE9HKdd5mDXSTC");
                headers.put("Content-Type","application/json");
                return headers;
            }
        };
        Volley.newRequestQueue(context).add(myReq);
    }

}
