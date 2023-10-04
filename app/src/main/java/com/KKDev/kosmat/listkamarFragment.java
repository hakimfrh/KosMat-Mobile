package com.KKDev.kosmat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.KKDev.kosmat.adapter.cardAdapter;

public class listkamarFragment extends Fragment {

    private RecyclerView recyclerView;

    Object[][] data = {
            {R.drawable.kamar1, "Kamar 1", "Penghuni: Angga","Kamar Mandi: Dalam","Fasilitas: AC"},
            {R.drawable.kamar2, "Kamar 2", "Penghuni: Rama", "Kamar Mandi: Dalam","Fasilitas: AC"},
            {R.drawable.kamar3, "Kamar 3", "Penghuni: Tahur", "Kamar Mandi: Luar","Fasilitas: AC"},
            {R.drawable.kamar4, "Kamar 4", "Penghuni: Bayu", "Kamar Mandi: Luar","Fasilitas: AC"}
            // Add more rows as needed...
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listkamar, container, false);

        recyclerView = view.findViewById(R.id.kamarRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        cardAdapter adapter = new cardAdapter(getContext(),data);
        recyclerView.setAdapter(adapter);

        return view;
    }
}