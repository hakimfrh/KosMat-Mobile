package com.KKDev.kosmat.bottomSheet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.KKDev.kosmat.Api;
import com.KKDev.kosmat.MainActivity;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.listener.MainActivityUpdateListener;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TambahPengeluaranBottomSheet extends BottomSheetDialogFragment {

    BottomSheetDialogFragment bottomSheetDialogFragment = this;
    JSONArray jsonArray;

    public TambahPengeluaranBottomSheet(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_tambah_pengeluaran, container, false);
        AutoCompleteTextView txt_jenis = view.findViewById(R.id.txt_jenis);
        TextInputLayout txtx_jumlah = view.findViewById(R.id.txt_jumlah);
        TextInputEditText txt_jumlah = (TextInputEditText) txtx_jumlah.getEditText();
        Button btn_tambah_pengeluaran = view.findViewById(R.id.btn_tambah_pengeluaran);

        List<String> pengeluaran = new ArrayList<>();
        if (!(jsonArray == null)) {
            if (jsonArray.length() > 0) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONArray array = jsonObject.getJSONArray("array");
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject data = array.getJSONObject(j);
                            String keterangan = data.getString("keterangan");
                            if (!(pengeluaran.contains(keterangan))) {
                                pengeluaran.add(keterangan);
                            }
                        }
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, pengeluaran);
        txt_jenis.setAdapter(adapter);

        btn_tambah_pengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("method", "tambahPengeluaran");
                    jsonObject.put("keterangan", txt_jenis.getText());
                    jsonObject.put("jumlah", txt_jumlah.getText());
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
                String url = Api.urlTranskasi;
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int code = response.getInt("code");
                                    String status = response.getString("status");

                                    // Handle the response based on code and status
                                    if (status.equals("ok")) {
                                        bottomSheetDialogFragment.dismiss();
                                        MainActivityUpdateListener listener = ((MainActivity)getContext()).getListener();
                                        listener.updateTransaksiList();
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
        });
        return view;
    }
}
