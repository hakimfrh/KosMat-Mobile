package com.KKDev.kosmat.bottomSheet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.KKDev.kosmat.Api;
import com.KKDev.kosmat.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

public class KeuntunganDescFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_keuntungan_desc, container, false);
        TextView tx_tanggal = view.findViewById(R.id.tx_desc_tanggal);
        TextView tx_pemasukan = view.findViewById(R.id.tx_desc_pemasukan);
        TextView tx_pengeluaran = view.findViewById(R.id.tx_desc_pengeluaran);
        TextView tx_keuntungan = view.findViewById(R.id.tx_desc_keuntungan);
        TextView tx_keuntungan_old = view.findViewById(R.id.tx_desc_keuntungan_old);
        TextView tx_selisih = view.findViewById(R.id.tx_desc_selisih);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.urlTranskasi + "?method=getKeuntunganDesc", new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject data = new JSONObject(response);;
                    String status = data.getString("status");
                    if(status.equals("ok")){
                        tx_tanggal.setText(data.getString("tanggal"));
                        tx_pemasukan.setText("Rp. "+data.getString("pemasukan"));
                        tx_pengeluaran.setText("Rp. "+data.getString("pengeluaran"));
                        tx_keuntungan.setText("Rp. "+data.getString("keuntungan"));
                        tx_keuntungan_old.setText("Rp. "+data.getString("keuntungan_old"));
                        tx_selisih.setText(data.getString("selisih"));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
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
        return view;
    }
}