package com.KKDev.kosmat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);

        // Mendapatkan deskripsi dari intent
        int image = getIntent().getIntExtra("image", 0);// Menggunakan key "description"
        String title = getIntent().getStringExtra("title"); // Menggunakan key "description"
        String penghuni = getIntent().getStringExtra("penghuni"); // Menggunakan key "description"
        String description = getIntent().getStringExtra("description"); // Menggunakan key "description"

        // Menampilkan deskripsi di TextView
        ImageView image1 = findViewById(R.id.descImage);
        TextView tx_penghuni = findViewById(R.id.descPenghuni);
        TextView tx_title = findViewById(R.id.descTitle);
        TextView tx_desc = findViewById(R.id.descDescription);

        image1.setImageResource(image);
        tx_penghuni.setText(penghuni);
        tx_title.setText(title);
        tx_desc.setText(description);
    }
}
