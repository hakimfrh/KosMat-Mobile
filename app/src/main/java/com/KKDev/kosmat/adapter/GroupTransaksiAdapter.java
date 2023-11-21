package com.KKDev.kosmat.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.KKDev.kosmat.R;
import com.KKDev.kosmat.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupTransaksiAdapter extends RecyclerView.Adapter<GroupTransaksiAdapter.ViewHolder>{
//    private Object[][] data;
    Map<String, List<Object[]>> transaksi = new HashMap<>();

    public GroupTransaksiAdapter(Object[][] data) {
//        this.data = data;

        for (Object[] transaction : data) {
            String day = (String) transaction[0];

            // Mengecek apakah hari tersebut sudah ada dalam Map
            if (!transaksi.containsKey(day)) {
                // Jika belum, membuat List baru untuk hari tersebut
                transaksi.put(day, new ArrayList<>());
            }

            // Menambahkan transaksi ke dalam List yang terkait dengan hari tersebut
            transaksi.get(day).add(transaction);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_card_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupTransaksiAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return transaksi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
        }
    }


}
