package com.KKDev.kosmat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.KKDev.kosmat.model.Kamar;
import com.KKDev.kosmat.model.User;
import com.KKDev.kosmat.model.UserResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KamarActivity extends AppCompatActivity {

    String mode;
    Kamar kamar;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamar);
        context = this;
        TextView tx_header = findViewById(R.id.tx_title);
        TextView tx_rincianPenyewa = findViewById(R.id.tx_rincianPenyewa);
        TextInputLayout txtx_no_kamar = findViewById(R.id.txt_noKamar);
        TextInputLayout txtx_harga_kamar = findViewById(R.id.txt_hargaKamar);
        TextInputLayout txtx_nik = findViewById(R.id.kamar_txt_nik);
        TextInputLayout txtx_nama = findViewById(R.id.kamar_kamar_txt_nama);
        TextInputEditText txt_no_kamar = (TextInputEditText) txtx_no_kamar.getEditText();
        TextInputEditText txt_harga_kamar = (TextInputEditText) txtx_harga_kamar.getEditText();
        TextInputEditText txt_nik = (TextInputEditText) txtx_nik.getEditText();
        TextInputEditText txt_nama = (TextInputEditText) txtx_nama.getEditText();
        CheckBox cb_disewa = findViewById(R.id.cb_disewa);
        Spinner sp_penyewa = findViewById(R.id.sp_penyewa);
        Button btn_simpan = findViewById(R.id.btn_simpan);
        Button btn_hapus = findViewById(R.id.btn_hapus);
        ConstraintLayout penyewa = findViewById(R.id.penyewa);
        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");

        cb_disewa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    penyewa.setVisibility(View.VISIBLE);
                }else{
                    penyewa.setVisibility(View.GONE);
                }
            }
        });

        if (mode.equals("edit")) {
            kamar = (Kamar) intent.getSerializableExtra("kamar");
            txt_no_kamar.setText(kamar.getId_kamar());
            txt_harga_kamar.setText(kamar.getHarga_kamar());
            tx_header.setText("Detail Kamar");
        } else {
            tx_header.setText("Tambah Kamar");
            btn_hapus.setVisibility(View.GONE);
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.urlUser + "?method=getAllUser", new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                if (response != null) {
                    UserResponse userResponse = new Gson().fromJson(response.toString(), UserResponse.class);
                    if (userResponse.getStatus().equals("ok")) {
                        List<User> userList = userResponse.getUserlist();

                        List<String> penyewaList = new ArrayList<>();
                        penyewaList.add("Tambah Baru");
                        for (User user : userList) {
                            penyewaList.add(user.getNama());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, penyewaList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_penyewa.setAdapter(adapter);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
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

        sp_penyewa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    txtx_nik.setVisibility(View.VISIBLE);
                    txtx_nama.setVisibility(View.VISIBLE);
                } else {
                    txtx_nik.setVisibility(View.GONE);
                    txtx_nama.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode.equals("edit")) {
                    // Create JSON object
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("method", "update");
                        jsonObject.put("old_id_kamar", kamar.getId_kamar());
                        jsonObject.put("id_kamar", txt_no_kamar.getText());
                        jsonObject.put("harga_kamar", txt_harga_kamar.getText());
                        jsonObject.put("deskripsi", "");
                        jsonObject.put("image", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                        builder.setTitle("Error").setMessage(e.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;
                    }
                    simpan_kamar(jsonObject);

                } else {

                    // Create JSON object
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("method", "create");
                        jsonObject.put("id_kamar", txt_no_kamar.getText());
                        jsonObject.put("harga_kamar", txt_harga_kamar.getText());
                        jsonObject.put("deskripsi", "");
                        jsonObject.put("image", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                        builder.setTitle("Error").setMessage(e.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        return;
                    }
                    simpan_kamar(jsonObject);

                }
            }
        });

        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Konfirmasi").setMessage("Hapus Kamar ?").setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Api.urlKamar + "?id_kamar=" + kamar.getId_kamar(), new Response.Listener() {
                            @Override
                            public void onResponse(Object response) {
                                if (response != null) {
                                    UserResponse userResponse = new Gson().fromJson(response.toString(), UserResponse.class);
                                    if (userResponse.getStatus().equals("ok")) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("Success").setMessage("Kamar Berhasil Dihapus").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Dismiss the dialog
                                                dialog.dismiss();
                                                onBackPressed();
                                            }
                                        }).show();
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

    private void simpan_kamar(JSONObject jsonObject) {
        String url = Api.urlKamar;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Activity activity = this;

        // Create the request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String status = response.getString("status");

                            // Handle the response based on code and status
                            if (status.equals("ok")) {
                                String message = mode.equals("edit") ? "Perubahan Telah Disimpan" : "Kamar Ditambahkan";
                                AlertDialog.Builder success = new AlertDialog.Builder(context);
                                success.setTitle("Success").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Dismiss the dialog
                                        dialog.dismiss();
                                        onBackPressed();
                                    }
                                }).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Error").setMessage(status).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
    }
}