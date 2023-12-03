package com.KKDev.kosmat.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.KKDev.kosmat.MainActivity;
import com.KKDev.kosmat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class KeluhanAdapter extends RecyclerView.Adapter<KeluhanAdapter.ViewHolder> {


    Context context;
    JSONArray jsonArray;

    public KeluhanAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_keluhan, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject data = jsonArray.getJSONObject(position);
            holder.tx_idkamar.setText("Kamar "+data.getString("id_kamar"));
            holder.tx_keterangan.setText(data.getString("keterangan"));
            holder.tx_tanggal.setText(tanggal(data.getString("tanggal")));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // Tambahkan method untuk mendapatkan deskripsi dari posisi tertentu

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tx_idkamar, tx_keterangan, tx_tanggal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tx_idkamar = itemView.findViewById(R.id.tx_idkamar);
            tx_keterangan = itemView.findViewById(R.id.tx_keterangan);
            tx_tanggal = itemView.findViewById(R.id.tx_tanggal);

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String tanggal(String datetime){
        String result="";
        String tanggal = datetime.split(" ")[0];
        String jam = datetime.split(" ")[1];

        LocalDate parsedDate = LocalDate.parse(tanggal);
        LocalDate currentDate = LocalDate.now();
        if (parsedDate.isEqual(currentDate)) {
            result = jam.split(":")[0]+":"+jam.split(":")[1];
        } else {
           result = tanggal;
        }
        return result;
    }
}
