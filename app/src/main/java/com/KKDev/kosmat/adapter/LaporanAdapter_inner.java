package com.KKDev.kosmat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.KKDev.kosmat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LaporanAdapter_inner extends RecyclerView.Adapter<LaporanAdapter_inner.ViewHolder> {
    Context context;
    JSONArray jsonArray;

    public LaporanAdapter_inner(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public LaporanAdapter_inner.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_laporan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanAdapter_inner.ViewHolder holder, int position) {
        try {
            JSONObject data = jsonArray.getJSONObject(position);
            holder.tx_keterangan.setText(data.getString("keterangan"));
            holder.tx_tanggal.setText(data.getString("tanggal"));
            holder.tx_jumlah.setText("Rp. "+data.getString("jumlah"));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tx_keterangan, tx_tanggal, tx_jumlah;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tx_keterangan = itemView.findViewById(R.id.tx_keterangan);
            tx_tanggal = itemView.findViewById(R.id.tx_tanggal);
            tx_jumlah = itemView.findViewById(R.id.tx_jumlah);
        }
    }
}
