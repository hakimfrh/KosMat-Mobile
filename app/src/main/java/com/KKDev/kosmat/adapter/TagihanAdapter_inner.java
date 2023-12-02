package com.KKDev.kosmat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.KKDev.kosmat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TagihanAdapter_inner extends RecyclerView.Adapter<TagihanAdapter_inner.ViewHolder> {

    List<Boolean> tagihan_list = new ArrayList<>();
    Context context;
    JSONArray jsonArray;
    TagihanCheckboxListener tagihanCheckboxListener;

    public TagihanAdapter_inner(Context context, JSONArray jsonArray, TagihanCheckboxListener tagihanCheckboxListener) {
        this.context = context;
        this.jsonArray = jsonArray;
        this.tagihanCheckboxListener = tagihanCheckboxListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_tagihan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject data = jsonArray.getJSONObject(position);
            tagihan_list.add(false);
            holder.tx_tagihan_jumlah.setText("Rp. " + data.getString("jumlah"));
            holder.tx_tagihan_bulan.setText(nameBulan(data.getString("tenggat")));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tagihan_list.set(holder.getAdapterPosition(), isChecked);
                tagihanCheckboxListener.onCheckTagihanChange(getTagihan());
            }
        });
        holder.tx_tagihan_bulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox.performClick();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView tx_tagihan_bulan, tx_tagihan_jumlah;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            tx_tagihan_bulan = itemView.findViewById(R.id.tx_tagihan_bulan);
            tx_tagihan_jumlah = itemView.findViewById(R.id.tx_tagihan_jumlah);
            //itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//
//        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public JSONArray getTagihan() {
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < jsonArray.length(); i++)
                if (tagihan_list.get(i)) {
                    array.put(jsonArray.getJSONObject(i));
                }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return array;
    }

    private String nameBulan(String date) {
        String result = "";

        String tahun = date.split("-")[0];
        String bulan = date.split("-")[1];
        if (bulan.equals("1")) {
            result = "Januari";
        } else if (bulan.equals("2")) {
            result = "Februari";
        } else if (bulan.equals("3")) {
            result = "Maret";
        } else if (bulan.equals("4")) {
            result = "April";
        } else if (bulan.equals("5")) {
            result = "Mei";
        } else if (bulan.equals("6")) {
            result = "Juni";
        } else if (bulan.equals("7")) {
            result = "Juli";
        } else if (bulan.equals("8")) {
            result = "Agustus";
        } else if (bulan.equals("9")) {
            result = "September";
        } else if (bulan.equals("10")) {
            result = "Oktober";
        } else if (bulan.equals("11")) {
            result = "November";
        } else if (bulan.equals("12")) {
            result = "Desember";
        }

        return result + " " + tahun;
    }

}
