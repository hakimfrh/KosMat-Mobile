package com.KKDev.kosmat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.KKDev.kosmat.adapter.User;

public class DashboardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Intent intent = getActivity().getIntent();
        User user = (User) intent.getSerializableExtra("user");

        TextView tx_namaUser = view.findViewById(R.id.tx_dsNama);
        TextView tx_description = view.findViewById(R.id.tx_dsDesc);

        String nama = user.getNama();
        String desc = "username \t: " + user.getUsername() +"\n"
                    +"password \t: " + user.getPassword() +"\n"
                    +"nik \t\t\t\t\t\t\t: " + user.getNik() +"\n"
                    +"whatsapp \t: " + user.getNoWhatsapp() +"\n"
                    +"tgl-lahir \t\t\t: " + user.getTglLahir() +"\n"
                    +"gender \t\t\t\t: " + user.getGender() +"\n";

        tx_namaUser.setText(nama);
        tx_description.setText(desc);
        return view;
    }
}