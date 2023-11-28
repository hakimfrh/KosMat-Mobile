package com.KKDev.kosmat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.KKDev.kosmat.R;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewHolder>{
    @NonNull
    @Override
    public LaporanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tagihan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tx_bulan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tx_bulan = itemView.findViewById(R.id.tx_bulan);
        }
    }
}
