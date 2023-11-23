package com.KKDev.kosmat.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.KKDev.kosmat.DescActivity;
import com.KKDev.kosmat.MainActivity;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.model.User;


public class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder> {

    private User[] data;
    private Context context;

    public userAdapter(Context context, User[] data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User row = data[position];

        byte[] byteArray = row.getImageByte();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        holder.imageView.setImageBitmap(bitmap);
        holder.textViewNama.setText(row.getNama());
        holder.textViewKamar.setText("");

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textViewNama;
        TextView textViewKamar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_profile);
            textViewNama = itemView.findViewById(R.id.tx_nama);
            textViewKamar = itemView.findViewById(R.id.tx_kamar);

            // Menambahkan listener klik pada itemView
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            User user = data[position];
            ((MainActivity)context).showBS_Pengguna(user);
/*
            if (position != RecyclerView.NO_POSITION) {
                // Mengambil deskripsi dari item yang diklik
                Object[] item = getItem(position);

                // Membuat intent untuk membuka DetailActivity
                Intent intent = new Intent(context, DescActivity.class);
                intent.putExtra("image", (Integer) item[0]);
                intent.putExtra("title", (String) item[1]);
                intent.putExtra("penghuni", (String) item[2]);
                intent.putExtra("description", (String) item[3]);
                context.startActivity(intent);
        }
  */
        }
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
