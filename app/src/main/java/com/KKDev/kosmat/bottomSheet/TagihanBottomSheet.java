package com.KKDev.kosmat.bottomSheet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.KKDev.kosmat.Api;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.adapter.TagihanAdapter_inner;
import com.KKDev.kosmat.listener.TagihanCheckboxListener;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TagihanBottomSheet extends BottomSheetDialogFragment implements TagihanCheckboxListener{
    JSONObject jsonObject;
    TextView tx_total;
    TagihanAdapter_inner adapter;
    BottomSheetDialogFragment bottomSheetDialogFragment = this;

    public TagihanBottomSheet(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

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
                JSONArray tagihan_list = adapter.getTagihan();
                int length = tagihan_list.length();
                try {
                    for (int i = 0; i < length; i++) {
                        JSONObject tagihan = tagihan_list.getJSONObject(i);
                        validasi(tagihan.getString("id_tagihan"));
                        bottomSheetDialogFragment.dismiss();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
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
    private void validasi(String id_tagihan){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("method", "validasiTagihan");
            jsonObject.put("id_tagihan", id_tagihan);
        } catch (JSONException e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Error").setMessage(e.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            return;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api.urlTranskasi, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String status = response.getString("status");

                            // Handle the response based on code and status
                            if (status.equals("ok")) {

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Error").setMessage(status).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Error").setMessage(e.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Error").setMessage(error.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }
}
