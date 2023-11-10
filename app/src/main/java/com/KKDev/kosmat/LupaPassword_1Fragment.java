package com.KKDev.kosmat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LupaPassword_1Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lupa_password_1, container, false);

        Intent intent = getActivity().getIntent();
        String getUsername = (String) intent.getStringExtra("username");

        TextInputLayout txtx_username = view.findViewById(R.id.lupapass_txt_username);
        TextInputEditText txt_username = (TextInputEditText) txtx_username.getEditText();
        Button btn_lanjut_1 = view.findViewById(R.id.lupapass_btn_lanjut_1);

        txt_username.setText(getUsername);
        btn_lanjut_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txt_username.getText().toString();
                String url = Api.urlUser+"?method=getWhatsapp&username="+username;
                RequestQueue queue = Volley.newRequestQueue(getContext());  // Replace 'null' with your context if you are in an Android application

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    // Retrieve values directly from the JSON object
                                    int code = jsonObject.getInt("code");
                                    String status = jsonObject.getString("status");
                                    String whatsapp = jsonObject.getString("whatsapp");
                                    if (status.equals("ok")) {
                                        String authCode = ((LupaPasswordActivity) getActivity()).generateCode();
                                        String body = "Kode Verifikasi 'Kosmat-Mobile' adalah *"+authCode +"*. Jangan Berikan code ini pada siapapun. Kode ini digunakan untuk mengatur ulang password";
                                        ((LupaPasswordActivity) getActivity()).sendMessage(body, whatsapp);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Eror").setMessage(error.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Dismiss the dialog
                                        dialog.dismiss();
                                    }
                                }).show();
                            }
                        });

                queue.add(stringRequest);
            }
        });

        return view;
    }
}