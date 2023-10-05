package com.KKDev.kosmat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

public class DescRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_register);
        // Mendapatkan deskripsi dari intent
        Intent intent = getIntent();
        String nama = intent.getStringExtra("nama");
        String email = intent.getStringExtra("email");
        String gender = intent.getStringExtra("gender");
        String agama = intent.getStringExtra("agama");
        String tanggal = intent.getStringExtra("tanggal");
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        Bitmap image = intent.getParcelableExtra("image");


        // Menampilkan deskripsi di TextView
        ShapeableImageView imageView = findViewById(R.id.img_kamera);
        TextView tx_Nama = findViewById(R.id.tx_nama);
        TextView tx_email = findViewById(R.id.tx_email);
        TextView tx_jenisKelamin = findViewById(R.id.tx_jenisKelamin);
        TextView tx_tanggalLahir = findViewById(R.id.tx_tanggalLahir);
        TextView tx_agama = findViewById(R.id.tx_agama);
        TextView tx_username = findViewById(R.id.tx_username);
        TextView tx_password = findViewById(R.id.tx_password);

        imageView.setImageBitmap(image);
        tx_Nama.setText(nama);
        tx_email.setText(email);
        tx_jenisKelamin.setText(gender);
        tx_tanggalLahir.setText(tanggal);
        tx_agama.setText(agama);
        tx_username.setText(username);
        tx_password.setText(password);
    }
}