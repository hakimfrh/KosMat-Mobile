package com.KKDev.kosmat.bottomSheet;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.KKDev.kosmat.R;
import com.KKDev.kosmat.adapter.TagihanAdapter_inner;
import com.KKDev.kosmat.adapter.TagihanCheckboxListener;
import com.KKDev.kosmat.model.User;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TagihanBottomSheet extends BottomSheetDialogFragment implements TagihanCheckboxListener{
    JSONObject jsonObject;

    public TagihanBottomSheet(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    TextView tx_total;
    TagihanAdapter_inner adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_tagihan, container, false);
        TextView tx_nokamar = view.findViewById(R.id.tx_noKamar);
        TextView tx_nama = view.findViewById(R.id.tx_nama);
        tx_total = view.findViewById(R.id.tx_total);
        Button button = view.findViewById(R.id.btn_tagihan_lunas);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_tagihan_inner);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            tx_nokamar.setText("Kamar "+jsonObject.getString("id_kamar"));
            tx_nama.setText(jsonObject.getString("nama"));
            adapter = new TagihanAdapter_inner(getContext(),jsonObject.getJSONArray("array"),this);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }

    @Override
    public void onCheckTagihanChange(JSONArray jsonArray) {
        int total = 0;
        try {
            for (int i = 0; i < jsonArray.length(); i++)
                total += jsonArray.getJSONObject(i).getInt("jumlah");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        tx_total.setText("Rp. "+Integer.toString(total));
    }
}
