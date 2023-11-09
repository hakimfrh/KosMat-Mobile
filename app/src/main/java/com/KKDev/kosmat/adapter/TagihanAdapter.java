package com.KKDev.kosmat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.KKDev.kosmat.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class TagihanAdapter extends RecyclerView.Adapter<TagihanAdapter.ViewHolder>{

    private Object[][] data;
    private Context context;
    private BottomSheetBehavior bottomSheetBehavior;

    TextView bs_tx_nokamar, bs_tx_nama, bs_tx_tagihan;

    public TagihanAdapter(Context context, Object[][] data,View bottomSheet) {
        this.context = context;
        this.data = data;
        bs_tx_nokamar = bottomSheet.findViewById(R.id.bs_tx_nokamar);
        bs_tx_nama = bottomSheet.findViewById(R.id.bs_tx_nama);
        bs_tx_tagihan = bottomSheet.findViewById(R.id.bs_tx_tagihan);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tagihan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object[] row = data[position];
        holder.imageView.setImageResource((Integer) row[0]);
        holder.textViewTitle.setText((String) row[1]);
        holder.textViewTotal.setText("Rp. "+(String) row[2]);
    }

    // Tambahkan method untuk mendapatkan deskripsi dari posisi tertentu
    public Object[] getItem(int position) {
        Object[] row = data[position];
        return row;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.tagihan_img_kamar);
            textViewTitle = itemView.findViewById(R.id.tagihan_title);
            textViewTotal = itemView.findViewById(R.id.tagihan_total);

            // Menambahkan listener klik pada itemView
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Object[] row = data[position];
            bs_tx_nokamar.setText((CharSequence) row[1]);
            bs_tx_tagihan.setText("Rp. " +(CharSequence) row[2]);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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
            }*/
        }
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
