package com.KKDev.kosmat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.NamaViewHolder> {
    private CharSequence[] data;

    public itemAdapter(CharSequence[] data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NamaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new NamaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NamaViewHolder holder, int position) {
        CharSequence item = data[position];
        holder.namaTextView.setText(item);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class NamaViewHolder extends RecyclerView.ViewHolder {
        TextView namaTextView;

        public NamaViewHolder(@NonNull View itemView) {
            super(itemView);
            namaTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}

