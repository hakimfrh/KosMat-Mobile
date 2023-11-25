package com.KKDev.kosmat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class whatsappVerificationActivity extends AppCompatActivity {

    private String whatsapp = "";
    private String kode = "";
    private boolean isCodeValid = false;
    private boolean isKirimValid = false;
    private CountDownTimer countDownTimer_1;
    private CountDownTimer countDownTimer_2;
    private long timerCode = 3 * 60 * 1000;
    private long timerKirimUlang = 1 * 60 * 1000;

    TextView tx_kirimUlang;

    @Override
    protected void onStop() {
        if(countDownTimer_1!=null){countDownTimer_1.cancel();}
        if(countDownTimer_2!=null){countDownTimer_2.cancel();}
        setResult(RESULT_CANCELED);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //disable darkmode
        setContentView(R.layout.activity_whatsapp_verification);

        Intent intent = getIntent();
        whatsapp = intent.getStringExtra("whatsapp");

        TextInputLayout txtx_code = findViewById(R.id.txt_kodewa);
        TextInputEditText txt_code = (TextInputEditText) txtx_code.getEditText();
        Button btn_lanjut = findViewById(R.id.lupapass_btn_lanjut);
        tx_kirimUlang = findViewById(R.id.tx_kirimUlang);
        TextView tx_watsapp =findViewById(R.id.tx_whatsapp);

        tx_watsapp.setText(whatsapp);
        //generateCode();
        //submitCode(kode);
        sendCode();
        tx_kirimUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isKirimValid){
                    sendCode();
                }
            }
        });

        btn_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = txt_code.getText().toString();
                submitCode(code);
            }
        });


    }

    public String submitCode(String code) {
        Context context = this;
        String result;
        if (isCodeValid) {
            if (this.kode.equals(code)) {
                result = "ok";
                setResult(1);
                finish();
            } else {
                result = "Kode yang dimasukkan salah";
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Eror").setMessage(result).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                }).show();
            }
        } else {
            result = "Kode tidak valid. Silahkan klik kirim ulang kode";
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Eror").setMessage(result).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss the dialog
                    dialog.dismiss();
                }
            }).show();
        }
        return result;
    }

    public String generateCode() {
        Random random = new Random();
        int min = 100000; // Minimum 6-digit number
        int max = 999999; // Maximum 6-digit number

        // Generate a random number within the specified range
        int randomNumber = random.nextInt((max - min) + 1) + min;
        startTimer_1();
        // Convert the random number to a 6-digit string
        return String.format("%06d", randomNumber);
    }

    public void startTimer_1() {
        isCodeValid = true;
        countDownTimer_1 = new CountDownTimer(timerCode, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                isCodeValid = false;
            }
        };
        countDownTimer_1.start();
    }

    public void startTimer_2() {

        isKirimValid = false;
        countDownTimer_2 = new CountDownTimer(timerKirimUlang, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                String text = String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60);
                tx_kirimUlang.setText(text);
                tx_kirimUlang.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.black));
            }

            @Override
            public void onFinish() {
                isKirimValid = true;
                tx_kirimUlang.setText("Kirim Ulang Kode");
                tx_kirimUlang.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.link_blue));
            }
        };
        countDownTimer_2.start();

    }

    public void sendMessage(final String message, final String number) {
        String url = "https://whatsapp-api--hakimfrh.repl.co/send-message";
        RequestQueue queue = Volley.newRequestQueue(this);  // Replace 'null' with your context if you are in an Android application

        Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                Toast.makeText(getBaseContext(), "Kode Terkirim", Toast.LENGTH_SHORT).show();
                startTimer_2();
            }
        }, new Response.ErrorListener() {
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

    public void sendCode() {
        if (!isCodeValid) {
            kode = generateCode();
        }
        String body = "Kode Verifikasi 'Kosmat-Mobile' adalah *" + kode + "*. Jangan Berikan code ini pada siapapun.";
        sendMessage(body, whatsapp);
    }
}