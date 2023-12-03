package com.KKDev.kosmat.adapter;

import android.content.Context;
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

import com.KKDev.kosmat.MainActivity;
import com.KKDev.kosmat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

public class TagihanAdapter extends RecyclerView.Adapter<TagihanAdapter.ViewHolder> {


    Context context;
    JSONArray jsonArray;

    public TagihanAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tagihan, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject data = jsonArray.getJSONObject(position);

            int total = 0;
            for (int i = 0; i < data.getJSONArray("array").length(); i++) {
                int jumlah = data.getJSONArray("array").getJSONObject(i).getInt("jumlah");
                total += jumlah;
            }
            byte[] byteArray = Base64.getDecoder().decode(data.getString("photo"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            holder.imageView.setImageBitmap(bitmap);
            holder.textViewTitle.setText("Kamar " + data.getString("id_kamar"));
            holder.textViewTotal.setText("Rp. " + total);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // Tambahkan method untuk mendapatkan deskripsi dari posisi tertentu

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.tagihan_img_kamar);
            textViewTitle = itemView.findViewById(R.id.tagihan_title);
            textViewTotal = itemView.findViewById(R.id.tagihan_total);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                int position = getAdapterPosition();
                JSONObject data = jsonArray.getJSONObject(position);
                ((MainActivity) context).showBS_tagihan(data);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
}
