package com.KKDev.kosmat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.KKDev.kosmat.model.User;
import com.KKDev.kosmat.model.UserResponse;
import com.KKDev.kosmat.retrofit.DatabaseCallback;
import com.KKDev.kosmat.retrofit.DatabaseConnection;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class LogRegActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //disable darkmode
        setContentView(R.layout.activity_log_reg);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.logRegFragment, new LoginFragment(), "LoginFragment");
        ft.commit();

        SharedPreferences sharedPreferences = this.getSharedPreferences("login", this.MODE_PRIVATE);

        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        boolean savedCheckbox = sharedPreferences.getBoolean("checkBox", false);

        if (savedCheckbox && (!savedUsername.isEmpty() && !savedPassword.isEmpty())) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.urlUser + "?method=getUser&username=" + savedUsername + "&password=" + savedPassword, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    if (response != null) {
                        UserResponse userResponse = new Gson().fromJson(response.toString(), UserResponse.class);
                        if (userResponse.getStatus().equals("ok")) {

                            List<User> userList = userResponse.getUserlist();
                            User user = userList.get(0);
                            if (user != null) {

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Berhasil Login sebagai " + user.getNama(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LogRegActivity.this);
                    builder.setTitle("Eror").setMessage(error.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss the dialog
                            dialog.dismiss();
                        }
                    }).show();
                }
            });
            requestQueue.add(stringRequest);
        }
    }

    public void onBackPressed() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentByTag("LoginFragment");
        if (loginFragment != null && loginFragment.isVisible()) {
            finishAffinity();
        } else {
            super.onBackPressed();
        }

    }
}