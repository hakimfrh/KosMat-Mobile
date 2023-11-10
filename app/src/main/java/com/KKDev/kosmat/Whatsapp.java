//package com.KKDev.kosmat;
//
//import android.content.Context;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Whatsapp {
//
//    Context context;
//    public Whatsapp(Context context){
//        this.context = context;
//    }
//
//
//    private String sendMessage(final String message, final String number) {
//        String result;
//        String url = "http://" +Api.server_ip +":8000/send-message";
//        RequestQueue queue = Volley.newRequestQueue(this.context);  // Replace 'null' with your context if you are in an Android application
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println(response);
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        System.out.println("Error: " + error.getMessage());
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<>();
//                params.put("message", message);
//                params.put("number", number);
//                return params;
//            }
//        };
//
//        queue.add(stringRequest);
//    }
//}
