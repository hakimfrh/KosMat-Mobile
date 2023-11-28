package com.KKDev.kosmat.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.KKDev.kosmat.Api;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.adapter.listKamarAdapter;
import com.KKDev.kosmat.model.Kamar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class KamarKosongFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kamar_kosong, container, false);

        String url = Api.urlKamarKosong;
        Context context = getContext();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // Retrieve values directly from the JSON object
                    // int code = jsonObject.getInt("code");
                    String status = jsonObject.getString("status");
                    if (status.equals("ok")) {
                        Gson gson = new Gson();
                        Type kamarListType = new TypeToken<List<Kamar>>() {}.getType();
                        List<Kamar> kamarList = gson.fromJson(jsonObject.getJSONArray("kamar_list").toString(), kamarListType);

                        recyclerView = view.findViewById(R.id.KamarKosongRecyclerView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        listKamarAdapter adapter = new listKamarAdapter(context, kamarList,false);
                        recyclerView.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

        return view;
    }
}