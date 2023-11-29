package com.KKDev.kosmat.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.adapter.LaporanAdapter;
import com.KKDev.kosmat.adapter.TagihanAdapter;
import com.KKDev.kosmat.adapter.TransaksiAdapter;
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
        TextView tx_namaUser = view.findViewById(R.id.tx_dsNama);
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
        RequestQueue queue = Volley.newRequestQueue(getContext());
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
}