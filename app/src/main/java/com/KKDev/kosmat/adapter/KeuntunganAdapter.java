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

public class KeuntunganAdapter extends RecyclerView.Adapter<KeuntunganAdapter.ViewHolder> {

    Context context;
    JSONArray jsonArray;

    public KeuntunganAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_keuntungan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeuntunganAdapter.ViewHolder holder, int position) {
        try {
            JSONObject data = jsonArray.getJSONObject(position);
            holder.tx_bulan.setText(nameBulan(data.getString("bulan")));
            int pemasukan = data.getInt("total_pemasukan");
            int pengeluaran = data.getInt("total_pengeluaran");
            int keuntungan = pemasukan-pengeluaran;
            holder.tx_jumlah_pemasukan.setText("Rp. "+pemasukan);
            holder.tx_jumlah_pengeluaran.setText("Rp. "+pengeluaran);
            holder.tx_jumlah_keuntungan.setText("Rp. "+keuntungan);

            if(keuntungan<=0){
                holder.tx_jumlah_keuntungan.setTextColor(context.getResources().getColor(R.color.red));
            }else {
                holder.tx_jumlah_keuntungan.setTextColor(context.getResources().getColor(R.color.primary));
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
        TextView tx_bulan, tx_jumlah_pemasukan, tx_jumlah_pengeluaran, tx_jumlah_keuntungan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tx_bulan = itemView.findViewById(R.id.tx_bulan);
            tx_jumlah_pemasukan = itemView.findViewById(R.id.tx_jumlah_pemasukan);
            tx_jumlah_pengeluaran = itemView.findViewById(R.id.tx_jumlah_pengeluaran);
            tx_jumlah_keuntungan = itemView.findViewById(R.id.tx_jumlah_keuntungan);

        }
    }
    private String nameBulan(String date){
        String result ="";

        String bulan= date.split(" ")[0];
        String tahun = date.split(" ")[1];
        if(bulan.equals("1")){ result = "Januari";
        }else if (bulan.equals("2")) {result = "Februari";
        }else if (bulan.equals("3")) {result = "Maret";
        }else if (bulan.equals("4")) {result = "April";
        }else if (bulan.equals("5")) {result = "Mei";
        }else if (bulan.equals("6")) {result = "Juni";
        }else if (bulan.equals("7")) {result = "Juli";
        }else if (bulan.equals("8")) {result = "Agustus";
        }else if (bulan.equals("9")) {result = "September";
        }else if (bulan.equals("10")) {result = "Oktober";
        }else if (bulan.equals("11")) {result = "November";
        }else if (bulan.equals("12")) {result = "Desember";
        }

        return result+" "+tahun;
    }

}
