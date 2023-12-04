package com.KKDev.kosmat.bottomSheet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.KKDev.kosmat.Api;
import com.KKDev.kosmat.MainActivity;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.listener.MainActivityUpdateListener;
import com.KKDev.kosmat.model.User;
import com.KKDev.kosmat.model.UserResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class PenggunaBottomSheet extends BottomSheetDialogFragment {
    User user;
    BottomSheetDialogFragment bottomSheetDialogFragment = this;

    public PenggunaBottomSheet(User user) {
        this.user = user;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_pengguna, container, false);
        
        ImageView img_profile = view.findViewById(R.id.img_bs_pengguna);
        TextView tx_pengguna = view.findViewById(R.id.tx_bs_namaPengguna);
        TextView tx_nik = view.findViewById(R.id.tx_bs_nik);
        TextView tx_kamar = view.findViewById(R.id.tx_bs_kamar);
        TextView tx_tgllahir = view.findViewById(R.id.tx_bs_tgllahir);
        TextView tx_whatsapp = view.findViewById(R.id.tx_bs_whatsapp);
        TextView tx_whatsappWali = view.findViewById(R.id.tx_bs_whatsappWali);
        Button btn_hapus = view.findViewById(R.id.btn_bs_hapus);

        img_profile.setImageBitmap(user.getImageBitmap());
        tx_pengguna.setText(user.getNama());
        tx_nik.setText("Nik: "+user.getNik());
        tx_tgllahir.setText(user.getTgl_lahir());
        tx_whatsapp.setText(user.getNo_whatsapp());
        tx_whatsappWali.setText(user.getNo_whatsapp_wali());

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.urlKamar + "?method=getIdKamar&nik=" + user.getNik(), new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject object = new JSONObject(response.toString());
                    String status = object.getString("status");
                    if(status.equals("ok")){
                        String id_kamar = object.getString("id_kamar");
                        if(!id_kamar.isEmpty()) tx_kamar.setText("Kamar: "+id_kamar);
                        else tx_kamar.setText("Kamar: ~");
                    }else tx_kamar.setText("Kamar: ~");
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
        requestQueue.add(stringRequest);


        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Konfirmasi").setMessage("Hapus Penyewa ?").setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
//                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Api.urlUser + "?nik=" + user.getNik(), new Response.Listener() {
                            @Override
                            public void onResponse(Object response) {
                                if (response != null) {
                                    UserResponse userResponse = new Gson().fromJson(response.toString(), UserResponse.class);
                                    if (userResponse.getStatus().equals("ok")) {
                                        bottomSheetDialogFragment.dismiss();
                                        Toast.makeText(getContext(), user.getNama()+" dihapus", Toast.LENGTH_SHORT).show();
                                        MainActivityUpdateListener listener = ((MainActivity)getContext()).getListener();
                                        listener.updatePenyewaList();;
                                    }
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
                        requestQueue.add(stringRequest);
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }

        });
        return view;
    }
}
