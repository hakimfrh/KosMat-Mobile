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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TransaksiAdapter_inner extends RecyclerView.Adapter<TransaksiAdapter_inner.ViewHolder> {
    //    private Object[][] data;
    Context context;
    JSONArray jsonArray;

    public TransaksiAdapter_inner(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter_inner.ViewHolder holder, int position) {
        try {
            JSONObject data = jsonArray.getJSONObject(position);
            String tipe = data.getString("tipe");

            holder.tx_tipe.setText(data.getString("tipe"));
            holder.tx_keterangan.setText(data.getString("keterangan"));
            holder.tx_jumlah.setText("Rp. "+data.getString("jumlah"));

            if(tipe.equals("Pemasukan")){
                holder.imageView.setImageResource(R.drawable.icon_pemasukan);
                holder.tx_tipe.setTextColor(context.getResources().getColor(R.color.primary));
                holder.tx_jumlah.setTextColor(context.getResources().getColor(R.color.primary));
            } else if (tipe.equals("Pengeluaran")) {
                holder.imageView.setImageResource(R.drawable.icon_pengeluaran);
                holder.tx_tipe.setTextColor(context.getResources().getColor(R.color.red));
                holder.tx_jumlah.setTextColor(context.getResources().getColor(R.color.red));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tx_tipe, tx_keterangan, tx_jumlah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon_transaksi);
            tx_tipe = itemView.findViewById(R.id.tx_tipe);
            tx_keterangan = itemView.findViewById(R.id.tx_keterangan);
            tx_jumlah = itemView.findViewById(R.id.tx_jumlah);

        }
    }


}
