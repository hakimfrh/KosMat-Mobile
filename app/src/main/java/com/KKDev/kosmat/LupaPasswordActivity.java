package com.KKDev.kosmat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.KKDev.kosmat.fragment.LupaPassword_1Fragment;
import com.KKDev.kosmat.fragment.LupaPassword_2Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LupaPasswordActivity extends AppCompatActivity {

    private String whatsapp = "";
    private String kode = "";
    private boolean isValid = true;
    private CountDownTimer countDownTimer;
    private long initialDurationMillis = 3 * 60 * 1000;

    @Override
    protected void onStop() {
        countDownTimer.cancel();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //disable darkmode

        setContentView(R.layout.activity_lupa_password);

        String url = Api.urlOwnerWhatsapp;
        RequestQueue queue = Volley.newRequestQueue(this);  // Replace 'null' with your context if you are in an Android application
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            // Retrieve values directly from the JSON object
                           // int code = jsonObject.getInt("code");
                            String status = jsonObject.getString("status");
                            whatsapp = jsonObject.getString("whatsapp");
                            if (status.equals("ok")) {

                                kode = generateCode();
                                String body = "Kode Verifikasi 'Kosmat-Mobile' adalah *" + kode + "*. Jangan Berikan code ini pada siapapun. Kode ini digunakan untuk mengatur ulang password";
                                sendMessage(body, whatsapp);

                                FragmentManager fm = getSupportFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.fragmentContainerView, new LupaPassword_1Fragment(whatsapp));
                                ft.commit();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                        builder.setTitle("Eror").setMessage(error.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Dismiss the dialog
                                dialog.dismiss();
                                onBackPressed();
                            }
                        }).show();
                    }
                });
        queue.add(stringRequest);

    }

    public String submitCode(String code) {
        String result;
        if (isValid) {
            if (this.kode.equals(code)) {
                result = "ok";
            } else {
                result = "Code yang dimasukkan salah";
            }
        } else {
            result = "Code tidak valid. Silahkan klik kirim ulang kode";
        }
        return result;
    }

    public String generateCode() {
        Random random = new Random();
        int min = 100000; // Minimum 6-digit number
        int max = 999999; // Maximum 6-digit number

        // Generate a random number within the specified range
        int randomNumber = random.nextInt((max - min) + 1) + min;
        startTimer();
        // Convert the random number to a 6-digit string
        return String.format("%06d", randomNumber);
    }

    public void startTimer() {
        isValid = true;
        countDownTimer = new CountDownTimer(initialDurationMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                isValid = false;
            }
        };
        countDownTimer.start();
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
                        Toast.makeText(getBaseContext(), "Kode Terkirim", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Eror").setMessage("Gagal mengirim pesan whatsapp.\nCek kembali nomor atau koneksi anda").setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

    public void resendCode() {
        if (!isValid) {
            kode = generateCode();
        }
        String body = "Kode Verifikasi 'Kosmat-Mobile' adalah *" + kode + "*. Jangan Berikan code ini pada siapapun. Kode ini digunakan untuk mengatur ulang password";
        sendMessage(body, whatsapp);
    }
}