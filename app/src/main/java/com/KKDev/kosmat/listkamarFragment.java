package com.KKDev.kosmat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.KKDev.kosmat.adapter.User;
import com.KKDev.kosmat.adapter.cardAdapter;
import com.KKDev.kosmat.adapter.sqliteHelper;

public class listkamarFragment extends Fragment {

    private RecyclerView recyclerView;





    Object[][] data = null/*
            {R.drawable.kamar1, "Kamar 1", "Penghuni: Angga","Kamar Mandi: Dalam\nFasilitas: AC"},
            {R.drawable.kamar2, "Kamar 2", "Penghuni: Rama", "Kamar Mandi: Dalam\nFasilitas: Kipas"},
            {R.drawable.kamar3, "Kamar 3", "Penghuni: Tahur", "Kamar Mandi: Luar\nFasilitas: Blower"},
            {R.drawable.kamar4, "Kamar 4", "Penghuni: Bayu", "Kamar Mandi: Luar\nFasilitas: Frezer daging"}
            // Add more rows as needed...
            */
    ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listkamar, container, false);
        sqliteHelper db = new sqliteHelper(getContext());
        User user[] = db.getAllUser();
        data = new Object[user.length][4];
        for(int i=0; i < user.length;i++){
            data[i][0]=R.drawable.kamar1;
            data[i][1]=user[i].getNama();
            data[i][2]=user[i].getUsername();
            String desc = "username \t: " + user[i].getUsername() +"\n"
                    +"password \t: " + user[i].getPassword() +"\n"
                    +"nik \t\t\t\t\t\t\t: " + user[i].getNik() +"\n"
                    +"whatsapp \t: " + user[i].getNoWhatsapp() +"\n"
                    +"tgl-lahir \t\t\t: " + user[i].getTglLahir() +"\n"
                    +"gender \t\t\t: " + user[i].getGender() +"\n";
            data[i][3]=desc;

        }


        recyclerView = view.findViewById(R.id.kamarRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cardAdapter adapter = new cardAdapter(getContext(),data);
        recyclerView.setAdapter(adapter);

        return view;
    }
}