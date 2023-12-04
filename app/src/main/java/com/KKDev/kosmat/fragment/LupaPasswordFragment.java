package com.KKDev.kosmat.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.KKDev.kosmat.Api;
import com.KKDev.kosmat.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class LupaPasswordFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lupa_password, container, false);

        TextInputLayout txtx_password_1 = view.findViewById(R.id.txt_gantipassword_1);
        TextInputLayout txtx_password_2 = view.findViewById(R.id.txt_gantipassword_2);
        TextInputEditText txt_password_1 = (TextInputEditText) txtx_password_1.getEditText();
        TextInputEditText txt_password_2 = (TextInputEditText) txtx_password_2.getEditText();
        Button btn_resetPassword = view.findViewById(R.id.btn_gantiPassword);


        txt_password_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_password_1.getText().length() < 8) {
                    txtx_password_1.setError("Password minimal 8 karakter");
                } else if (txt_password_1.getText().length() > 16) {
                    txtx_password_1.setError("Password maksimal 16 karakter");
                } else {
                    txtx_password_1.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_password_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_password_2.getText().length() < 8) {
                    txtx_password_2.setError("Password minimal 8 karakter");
                } else if (txt_password_2.getText().length() > 16) {
                    txtx_password_2.setError("Password maksimal 16 karakter");
                } else {
                    txtx_password_2.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btn_resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password_1 = txt_password_1.getText().toString();
                String password_2 = txt_password_2.getText().toString();
                if (password_1.equals(password_2) && (!password_1.isEmpty())) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("method", "updatePassword");
                        jsonObject.put("password", password_1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Error").setMessage(e.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;
                    }
                    String url = Api.urlUser;
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        int code = response.getInt("code");
                                        String status = response.getString("status");

                                        // Handle the response based on code and status
                                        if (status.equals("ok")) {

                                            AlertDialog.Builder success = new AlertDialog.Builder(getContext());
                                            success.setTitle("Success").setMessage("Password telah diubah").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Dismiss the dialog
                                                    dialog.dismiss();
                                                    getActivity().onBackPressed();
                                                }
                                            }).show();
                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                            builder.setTitle("Error").setMessage(status).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("Error").setMessage(e.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Handle error
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Error").setMessage(error.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                                }
                            });

                    // Add the request to the RequestQueue
                    requestQueue.add(jsonObjectRequest);
                } else {
                    if (password_1.isEmpty()) {
                        txtx_password_1.setError("Password tidak boleh kosong");
                    } else {
                        txtx_password_2.setError("Password tidak sama");
                    }
                }
            }
        });


        return view;
    }
}