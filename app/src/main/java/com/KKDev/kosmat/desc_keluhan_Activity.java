package com.KKDev.kosmat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.KKDev.kosmat.model.UserResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

public class desc_keluhan_Activity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_keluhan);
        TextView tx_tidakGambar = findViewById(R.id.tx_tidak_gambar);
        TextView tx_title = findViewById(R.id.tx_title);
        TextView tx_waktu = findViewById(R.id.waktu_keluhan);
        TextView tx_isiKeluhan = findViewById(R.id.tx_isi_keluhan);
        ImageView img_keluhan = findViewById(R.id.img_keluhan);
        Button btn_hapusKeluhan = findViewById(R.id.btn_hapus_keluhan);

        String id_kepemilikan, id_kamar, dateTime, tanggal, waktu, isiKeluhan, image;

        Intent intent = getIntent();
        String stringData = intent.getStringExtra("data");
        try {
            JSONObject data = new JSONObject(stringData);
            id_kepemilikan = data.getString("id_kepemilikan");
            id_kamar = data.getString("id_kamar");
            dateTime = data.getString("tanggal");
            isiKeluhan = data.getString("keterangan");
            image = data.getString("image_data");
            if (!image.isEmpty()) {
                tx_tidakGambar.setVisibility(View.GONE);
                byte[] byteArray = Base64.getDecoder().decode(image);
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                img_keluhan.setImageBitmap(bitmap);
            }
            tanggal = dateTime.split(" ")[0];
            waktu = dateTime.split(" ")[1];
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        tx_title.setText("Kamar " + id_kamar);
        tx_waktu.setText(nameBulan(tanggal) + "\n" + waktu);
        tx_isiKeluhan.setText(isiKeluhan);

        Context context = this;
        btn_hapusKeluhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Konfirmasi").setMessage("Hapus Keluhan ?").setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Api.urlKeluhan + "?id_kepemilikan=" + id_kepemilikan + "&tanggal=" + dateTime, new Response.Listener() {
                            @Override
                            public void onResponse(Object response) {
                                if (response != null) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.toString());
                                        String status = jsonObject.getString("status");
                                        if (status.equals("ok")) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            builder.setTitle("Success").setMessage("Keluhan Berhasil Dihapus").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Dismiss the dialog
                                                    setResult(RESULT_OK);
                                                    dialog.dismiss();
                                                    onBackPressed();
                                                }
                                            }).show();
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

    }

    private String nameBulan(String date) {
        String result = "";

        String tahun = date.split("-")[0];
        String bulan = date.split("-")[1];
        String tanggal = date.split("-")[2];
        if (bulan.equals("1")) {
            result = "Januari";
        } else if (bulan.equals("2")) {
            result = "Februari";
        } else if (bulan.equals("3")) {
            result = "Maret";
        } else if (bulan.equals("4")) {
            result = "April";
        } else if (bulan.equals("5")) {
            result = "Mei";
        } else if (bulan.equals("6")) {
            result = "Juni";
        } else if (bulan.equals("7")) {
            result = "Juli";
        } else if (bulan.equals("8")) {
            result = "Agustus";
        } else if (bulan.equals("9")) {
            result = "September";
        } else if (bulan.equals("10")) {
            result = "Oktober";
        } else if (bulan.equals("11")) {
            result = "November";
        } else if (bulan.equals("12")) {
            result = "Desember";
        }

        return tanggal + " " + result + " " + tahun;
    }
}