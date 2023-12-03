package com.KKDev.kosmat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.KKDev.kosmat.listener.MainActivityUpdateListener;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Matrix;

public class KamarActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_CHOOSER = 3;

    String mode;
    Kamar kamar;
    Spinner sp_penyewa;
    CheckBox cb_disewa;
    ImageView imageView,btn_rotate;
    TextInputEditText txt_no_kamar, txt_nik, txt_nama;
    List<User> userList;
    Bitmap bitmap;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CHOOSER) {
                Uri selectedImage = data != null ? data.getData() : null;
                boolean isCamera = data != null && data.getExtras() != null && data.getExtras().containsKey("data");
                if (isCamera && data.getExtras() != null) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                } else if (selectedImage != null) {
                    InputStream inputStream = null;
                    try {
                        //baca file
                        inputStream = getContentResolver().openInputStream(selectedImage);
                        bitmap = BitmapFactory.decodeStream(inputStream);

                        //compress
                        int maxWidthOrHeight = 600;
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        int newWidth, newHeight;
                        if (width > height) {
                            newWidth = maxWidthOrHeight;
                            newHeight = (int) (height * ((float) maxWidthOrHeight / width));
                        } else {
                            newHeight = maxWidthOrHeight;
                            newWidth = (int) (width * ((float) maxWidthOrHeight / height));
                        }
                        bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);

                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // Handle the cancellation here (if required)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamar);
        context = this;

        imageView = findViewById(R.id.img_kamar);
        btn_rotate = findViewById(R.id.btn_rotate);
        TextView tx_header = findViewById(R.id.tx_title);
        TextView tx_rincianPenyewa = findViewById(R.id.tx_rincianPenyewa);
        TextInputLayout txtx_no_kamar = findViewById(R.id.txt_noKamar);
        TextInputLayout txtx_harga_kamar = findViewById(R.id.txt_hargaKamar);
        TextInputLayout txtx_nik = findViewById(R.id.kamar_txt_nik);
        TextInputLayout txtx_nama = findViewById(R.id.kamar_kamar_txt_nama);
        TextInputLayout txtx_desc = findViewById(R.id.txt_deskipsiKamar);
        TextInputEditText txt_harga_kamar = (TextInputEditText) txtx_harga_kamar.getEditText();
        TextInputEditText txt_desc_kamar = (TextInputEditText) txtx_desc.getEditText();
        txt_no_kamar = (TextInputEditText) txtx_no_kamar.getEditText();
        txt_nik = (TextInputEditText) txtx_nik.getEditText();
        txt_nama = (TextInputEditText) txtx_nama.getEditText();


        cb_disewa = findViewById(R.id.cb_disewa);
        sp_penyewa = findViewById(R.id.sp_penyewa);
        Button btn_simpan = findViewById(R.id.btn_simpan);
        Button btn_hapus = findViewById(R.id.btn_hapus);
        ConstraintLayout penyewa = findViewById(R.id.penyewa);

        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        if (mode.equals("edit")) {
            kamar = (Kamar) intent.getSerializableExtra("kamar");
            bitmap = kamar.getImageBitmap();
            imageView.setImageBitmap(bitmap);
            tx_header.setText("Detail Kamar");
            txt_no_kamar.setText(kamar.getId_kamar());
            txt_harga_kamar.setText(kamar.getHarga_kamar());
            txt_desc_kamar.setText(kamar.getDeskripsi());
        } else {
            tx_header.setText("Tambah Kamar");
            btn_hapus.setVisibility(View.GONE);
        }

        cb_disewa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    penyewa.setVisibility(View.VISIBLE);
                } else {
                    penyewa.setVisibility(View.GONE);
                }
            }
        });

        if (mode.equals("edit") && kamar.getId_kepemilikan() == null) {
            cb_disewa.setChecked(false);
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.urlUser + "?method=getAllUser", new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                if (response != null) {
                    UserResponse userResponse = new Gson().fromJson(response.toString(), UserResponse.class);
                    if (userResponse.getStatus().equals("ok")) {
                        userList = userResponse.getUserlist();

                        List<String> penyewaList = new ArrayList<>();
                        penyewaList.add("Tambah Baru");
                        for (User user : userList) {
                            penyewaList.add(user.getNama());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, penyewaList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_penyewa.setAdapter(adapter);

                        if (mode.equals("edit") && kamar.getId_kepemilikan() != null) {
                            int index = 0;
                            boolean penghuni = true;
                            while (!userList.get(index).getNik().equals(kamar.getNik())) {
                                index++;
                                if (userList.size() < index) {
                                    penghuni = false;
                                    break;
                                }
                            }
                            if (penghuni) sp_penyewa.setSelection(index + 1);
                        }
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
                        String encodedImage = "";
                        if (bitmap != null) {
                            // Convert the bitmap to a byte array
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                            byte[] image = stream.toByteArray();
                            encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
//                          Toast.makeText(getApplicationContext(), "Size = " + stream.size(), Toast.LENGTH_SHORT).show();
                        }
                        jsonObject.put("method", "updateKamar");
                        jsonObject.put("old_id_kamar", kamar.getId_kamar());
                        jsonObject.put("id_kamar", txt_no_kamar.getText());
                        jsonObject.put("harga_kamar", txt_harga_kamar.getText());
                        jsonObject.put("deskripsi", txt_desc_kamar.getText());
                        jsonObject.put("image", encodedImage);
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
                        String encodedImage = "";
                        if (bitmap != null) {
                            // Convert the bitmap to a byte array
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                            byte[] image = stream.toByteArray();
                            encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
//                          Toast.makeText(getApplicationContext(), "Size = " + stream.size(), Toast.LENGTH_SHORT).show();
                        }
                        jsonObject.put("method", "create");
                        jsonObject.put("id_kamar", txt_no_kamar.getText());
                        jsonObject.put("harga_kamar", txt_harga_kamar.getText());
                        jsonObject.put("deskripsi", txt_desc_kamar.getText());
                        jsonObject.put("image", encodedImage);
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                Intent chooserIntent = Intent.createChooser(galleryIntent, "Choose Image...");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

                startActivityForResult(chooserIntent, REQUEST_CHOOSER);
            }
        });
        btn_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = rotateBitmap(bitmap);
                imageView.setImageBitmap(bitmap);
            }
        });

    }

    private void simpan_kamar(JSONObject jsonObject) {
        String url = Api.urlKamar;
        Activity activity = this;
        // Create the request
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String status = response.getString("status");

                            // Handle the response based on code and status
                            if (status.equals("ok")) {
                                if (mode.equals("edit")) {
                                    int index = sp_penyewa.getSelectedItemPosition();
                                    if (index > 0) {
                                        String nik = userList.get(index - 1).getNik();
                                        if ((!kamar.getId_kamar().equals(txt_no_kamar)) || (!kamar.getNik().equals(nik))) {
                                            updatePenyewa();
                                        }
                                    } else {
                                        if (cb_disewa.isChecked()) {
                                            if (txt_nama.getText().equals("") || txt_nik.equals("")) {
                                                Toast.makeText(context, "Isi kolom yang kosong", Toast.LENGTH_SHORT).show();
                                            } else {
                                                tambahPenyewa();
                                            }
                                        }
                                    }
                                } else if (mode.equals("new") && cb_disewa.isChecked()) {
                                    updatePenyewa();
                                }

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

    private void tambahPenyewa() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("method", "registerPenyewa");
            jsonObject.put("nik", txt_nik.getText());
            jsonObject.put("nama", txt_nama.getText());
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api.urlUser, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String status = response.getString("status");

                            // Handle the response based on code and status
                            if (status.equals("ok")) {
                                updatePenyewa();
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

    private void updatePenyewa() {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        int penyewa = sp_penyewa.getSelectedItemPosition();
        String nik;
        String id_kamar = txt_no_kamar.getText().toString();
        String tanggal_masuk = f.format(date);
        if (cb_disewa.isChecked()) {
            if (penyewa == 0) {
                nik = txt_nik.getText().toString();
            } else {
                nik = userList.get(penyewa - 1).getNik();
            }
            String id_kepemilikan = id_kamar + getBulan(date) + nik;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id_kepemilikan", id_kepemilikan);
                jsonObject.put("id_kamar", id_kamar);
                jsonObject.put("tanggal_masuk", tanggal_masuk);
                jsonObject.put("nik", nik);
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
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api.urlKepemilikan, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int code = response.getInt("code");
                                String status = response.getString("status");

                                // Handle the response based on code and status
                                if (status.equals("ok")) {

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
        } else {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Api.urlKepemilikan + "?id_kepemilikan=" + kamar.getId_kepemilikan(), new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    if (response != null) {
                        UserResponse userResponse = new Gson().fromJson(response.toString(), UserResponse.class);
                        if (userResponse.getStatus().equals("ok")) {
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Error").setMessage(userResponse.getStatus()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Dismiss the dialog
                                    dialog.dismiss();
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

    }

    private String getBulan(Date date) {
        SimpleDateFormat m = new SimpleDateFormat("MM");
        int month = Integer.parseInt(m.format(date));
        String b = "";
        if (month == 1) {
            b = "J";
        }//januari
        if (month == 2) {
            b = "F";
        }//februari
        if (month == 3) {
            b = "M";
        }//maret
        if (month == 4) {
            b = "A";
        }//april
        if (month == 5) {
            b = "M";
        }//mei
        if (month == 6) {
            b = "U";
        }//juni
        if (month == 7) {
            b = "L";
        }//juli
        if (month == 8) {
            b = "G";
        }//agustus
        if (month == 9) {
            b = "S";
        }//september
        if (month == 10) {
            b = "O";
        }//oktokber
        if (month == 11) {
            b = "N";
        }//november
        if (month == 12) {
            b = "D";
        }//desember

        return b;
    }

    private Bitmap rotateBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(270);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    }

}