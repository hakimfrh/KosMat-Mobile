package com.KKDev.kosmat.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeImageTransform;
import androidx.transition.TransitionSet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.KKDev.kosmat.Api;
import com.KKDev.kosmat.MainActivity;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.adapter.LaporanAdapter;
import com.KKDev.kosmat.adapter.TagihanAdapter;
import com.KKDev.kosmat.adapter.TransaksiAdapter;
import com.KKDev.kosmat.bottomSheet.ImageFragment;
import com.KKDev.kosmat.bottomSheet.KeuntunganDescFragment;
import com.KKDev.kosmat.model.User;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashboardFragment extends Fragment {
    private boolean isDashboardVisible = false;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        ImageView img_profile = view.findViewById(R.id.img_profile);
        ImageView img_info = view.findViewById(R.id.img_info);
        TextView tx_namaUser = view.findViewById(R.id.tx_dsNama);
        TextView tx_bulanIni = view.findViewById(R.id.tx_bulanIni);
        TextView tx_keuntungan = view.findViewById(R.id.tx_keuntungan);
        TextView tx_lihatSemua = view.findViewById(R.id.tx_lihat_semua);
        RecyclerView recycler_transaksi = view.findViewById(R.id.recycler_transaksi);
        RecyclerView recycler_tagihan = view.findViewById(R.id.recycler_tagihan);
        recycler_transaksi.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_tagihan.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        Intent intent = getActivity().getIntent();
        User user = (User) intent.getSerializableExtra("user");
        String nama = user.getNama();
        tx_namaUser.setText(nama);
        img_profile.setImageBitmap(user.getImageBitmap());

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDestinationFragmentWithTransitions(view, new EditProfileFragment(), user);
            }
        });
        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment keuntunganDescFragment = new KeuntunganDescFragment();
                keuntunganDescFragment.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), "showKeuntungan");

            }
        });
        tx_lihatSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).updateTransaksiList();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest keuntunganRequest = new StringRequest(Request.Method.GET, Api.urlTranskasi+"?method=getKeuntungan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("ok")) {
                        tx_bulanIni.setText(nameBulan(jsonObject.getString("bulan")));
                        tx_keuntungan.setText("Rp. "+jsonObject.getString("keuntungan"));
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
        queue.add(keuntunganRequest);
        StringRequest tagihanRequest = new StringRequest(Request.Method.GET, Api.urlTranskasi+"?method=getTagihan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("ok")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("tagihan_list");
                        recycler_tagihan.setAdapter( new TagihanAdapter(getContext(), jsonArray));
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
        queue.add(tagihanRequest);


        StringRequest transaksiRequest = new StringRequest(Request.Method.GET, Api.urlTranskasi+"?method=getTransaksi", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("ok")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("transaksi");
                        recycler_transaksi.setAdapter( new TransaksiAdapter(getContext(), jsonArray));
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
        queue.add(transaksiRequest);
        return view;
    }

    private void openDestinationFragmentWithTransitions(View view, Fragment destinationFragment, User user) {

        // Set up shared element transition
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeImageTransform());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        destinationFragment.setSharedElementEnterTransition(transitionSet);

        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        destinationFragment.setArguments(bundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFragmentContainer, destinationFragment); // Replace with the correct container ID
        transaction.addToBackStack(null);
        transaction.addSharedElement(view.findViewById(R.id.absoluteLayout), "background");
        transaction.addSharedElement(view.findViewById(R.id.circle1), "circle1");
        transaction.addSharedElement(view.findViewById(R.id.circle2), "circle2");
        transaction.addSharedElement(view.findViewById(R.id.img_profile), "img_profile");
        transaction.addSharedElement(view.findViewById(R.id.img_editprofile), "img_editProfile");
        transaction.commit();
    }
    private String nameBulan(String bulan) {
        String result = "";

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

        return result;
    }
}