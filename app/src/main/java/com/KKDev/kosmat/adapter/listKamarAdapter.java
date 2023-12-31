package com.KKDev.kosmat.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.KKDev.kosmat.KamarActivity;
import com.KKDev.kosmat.MainActivity;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.listener.MainActivityUpdateListener;
import com.KKDev.kosmat.model.Kamar;

import java.util.List;


public class listKamarAdapter extends RecyclerView.Adapter<listKamarAdapter.ViewHolder> {

    Context context;
    Fragment fragment;
    private List<Kamar> kamarList;
    private Boolean isTerisi;

    public listKamarAdapter(Context context, Fragment fragment, List<Kamar> kamarList, Boolean isTerisi) {
        this.context = context;
        this.fragment = fragment;
        this.kamarList = kamarList;
        this.isTerisi = isTerisi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_kamar, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Kamar kamar = kamarList.get(position);


        String desc = isTerisi?kamar.getNama():kamar.getDeskripsi();

        holder.imageView.setImageBitmap(kamar.getImageBitmap());
        holder.textViewTitle.setText("Kamar " +kamar.getId_kamar());
        holder.textViewDesc.setText(desc);
        holder.textViewHarga.setText("Rp. "+kamar.getHarga_kamar());
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewDesc;
        TextView textViewHarga;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.Title);
            textViewDesc = itemView.findViewById(R.id.desc);
            textViewHarga = itemView.findViewById(R.id.harga_kamar);

            // Menambahkan listener klik pada itemView
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                // Mengambil deskripsi dari item yang diklik
                Kamar kamar = kamarList.get(position);

                // Membuat intent untuk membuka DetailActivity
                Intent intent = new Intent(context, KamarActivity.class);
                intent.putExtra("kamar",kamar);
                intent.putExtra("mode","edit");
                fragment.startActivityForResult(intent,88);
            }
        }
    }

    @Override
    public int getItemCount() {
        return kamarList.size();
    }
}
