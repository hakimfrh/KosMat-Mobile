package com.KKDev.kosmat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.KKDev.kosmat.fragment.LoginFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LupaPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //disable darkmode

        setContentView(R.layout.activity_lupa_password);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainerView, new LupaPassword_1Fragment());
        ft.commit();
    }

    public String generateCode(){
        Random random = new Random();
        int min = 100000; // Minimum 6-digit number
        int max = 999999; // Maximum 6-digit number

        // Generate a random number within the specified range
        int randomNumber = random.nextInt((max - min) + 1) + min;

        // Convert the random number to a 6-digit string
        return String.format("%06d", randomNumber);
    }

    public void sendMessage(final String message, final String number) {
        String url = "http://" + Api.server_ip + ":8000/send-message";
        RequestQueue queue = Volley.newRequestQueue(this);  // Replace 'null' with your context if you are in an Android application

        Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        Toast.makeText(getBaseContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Eror").setMessage("Gagal mengirim pesan whagtapp. Cek kembali nomor anda").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Dismiss the dialog
                                dialog.dismiss();
                            }
                        }).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("message", message);
                params.put("number", number);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}