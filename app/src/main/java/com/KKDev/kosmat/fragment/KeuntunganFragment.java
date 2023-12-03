package com.KKDev.kosmat.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.KKDev.kosmat.Api;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.adapter.KeuntunganAdapter;
import com.KKDev.kosmat.adapter.LaporanAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class KeuntunganFragment extends Fragment {
    JSONArray pemasukan;
    JSONArray pengeluaran;
    JSONArray totalTransksi;
    RecyclerView recyclerView;
    TextView tx_loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_keuntungan, container, false);

        tx_loading = view.findViewById(R.id.tx_kamar_keuntungan_loading);
        recyclerView = view.findViewById(R.id.recycler_keuntungan);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getPemasukan();
        return view;
    }

    private void getPemasukan() {
        String url = Api.urlTranskasi;
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "?method=getTotalPemasukan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("ok")) {
                        pemasukan = jsonObject.getJSONArray("total_pemasukan");
                        getPengeluaran();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("EROR").setMessage(e.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss the dialog
                            dialog.dismiss();
                        }
                    }).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Eror").setMessage(error.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        queue.add(stringRequest);
    }

    private void getPengeluaran() {
        String url = Api.urlTranskasi;
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "?method=getTotalPengeluaran", new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("ok")) {
                        pengeluaran = jsonObject.getJSONArray("total_pengeluaran");
                        mergeTransaksi();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("EROR").setMessage(e.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss the dialog
                            dialog.dismiss();
                        }
                    }).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Eror").setMessage(error.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        queue.add(stringRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void mergeTransaksi() {
        try {
            // Ensure pemasukan and pengeluaran are not null and contain the expected JSON arrays
            if (pemasukan != null && pengeluaran != null) {
                Map<String, String> pemasukanMap = new HashMap<>();
                for (int i = 0; i < pemasukan.length(); i++) {
                    JSONObject pemasukanObj = pemasukan.getJSONObject(i);
                    pemasukanMap.put(pemasukanObj.getString("bulan"), pemasukanObj.getString("total_pemasukan"));
                }

                Map<String, String> pengeluaranMap = new HashMap<>();
                for (int i = 0; i < pengeluaran.length(); i++) {
                    JSONObject pengeluaranObj = pengeluaran.getJSONObject(i);
                    pengeluaranMap.put(pengeluaranObj.getString("bulan"), pengeluaranObj.getString("total_pengeluaran"));
                }

                HashSet<String> uniqueBulanSet = new HashSet<>(pemasukanMap.keySet());
                if (pemasukanMap.size() > pengeluaranMap.size()) {
                    uniqueBulanSet.addAll(pemasukanMap.keySet());
                } else {
                    uniqueBulanSet.addAll(pengeluaranMap.keySet());
                }

                JSONArray mergedArray = new JSONArray();
                for (String bulan : uniqueBulanSet) {
                    JSONObject mergedObj = new JSONObject();
                    mergedObj.put("bulan", bulan);

                    String pemasukanValue = pemasukanMap.getOrDefault(bulan, "0");
                    String pengeluaranValue = pengeluaranMap.getOrDefault(bulan, "0");

                    mergedObj.put("total_pemasukan", pemasukanValue);
                    mergedObj.put("total_pengeluaran", pengeluaranValue);

                    mergedArray.put(mergedObj);
                }
                List<JSONObject> keuntunganList = new ArrayList<>();
                for (int i = 0; i < mergedArray.length(); i++) {
                    keuntunganList.add(mergedArray.getJSONObject(i));
                }
                keuntunganList = sortJson(keuntunganList);

                totalTransksi = new JSONArray(keuntunganList);
                //textView.setText(totalTransksi.toString(4));
                recyclerView.setAdapter(new KeuntunganAdapter(getContext(), totalTransksi));
                if(keuntunganList.size()>0){
                    tx_loading.setVisibility(View.GONE);
                }
            }else {
                tx_loading.setText("Kosong...");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<JSONObject> sortJson(List<JSONObject> array) {
        Collections.sort(array, Comparator.comparing(o -> {
            try {
                return Integer.parseInt(((JSONObject) o).getString("bulan").split(" ")[0]);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }).reversed());
        return array;
    }
}