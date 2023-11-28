package com.KKDev.kosmat.bottomSheet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.KKDev.kosmat.Api;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.adapter.ViewPagerAdapter;
import com.KKDev.kosmat.fragment.DummyFragment;
import com.KKDev.kosmat.fragment.KamarKosongFragment;
import com.KKDev.kosmat.fragment.KamarTerisiFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditHargaKamarBottomSheet extends BottomSheetDialogFragment {

    BottomSheetDialogFragment bottomSheetDialogFragment = this;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_kamar_editharga, container, false);

        TextView tx_editHarga = view.findViewById(R.id.tx_editharga);
        Button btn_editHarga = view.findViewById(R.id.btn_editHarga_lanjut);
        TextInputLayout txtx_harga = view.findViewById(R.id.txt_editHarga);
        TextInputEditText txt_harga = (TextInputEditText) txtx_harga.getEditText();

        TabLayout tabLayout = view.findViewById(R.id.tabEditHargaKamar);
        ViewPager2 viewPager = view.findViewById(R.id.viewEditHarga);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DummyFragment());
        fragments.add(new DummyFragment());
        fragments.add(new DummyFragment());

        // Add more fragments as needed
        FragmentActivity fragmentActivity = requireActivity();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentActivity.getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Ubah");
                    break;
                case 1:
                    tab.setText("Tambah");
                    break;
                case 2:
                    tab.setText("Kurangi");
                    break;
            }
        }).attach();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch ((String) tab.getText()) {
                    case "Ubah":
                        tx_editHarga.setText("Ubah tarif seluruh kos menjadi:");
                        break;
                    case "Tambah":
                        tx_editHarga.setText("Tambah tarif seluruh kos sebanyak:");
                        break;
                    case "Kurangi":
                        tx_editHarga.setText("Kurangi tarif seluruh kos sebanyak:");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btn_editHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String method = "";
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        method = "ubahHarga";
                        break;
                    case 1:
                        method = "tambahHarga";
                        break;
                    case 2:
                        method = "kurangHarga";
                }

                String url = Api.urlKamar;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("method", method);
                    jsonObject.put("harga", txt_harga.getText().toString());
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
