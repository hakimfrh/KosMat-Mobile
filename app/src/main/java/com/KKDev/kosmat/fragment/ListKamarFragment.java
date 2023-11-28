package com.KKDev.kosmat.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.KKDev.kosmat.KamarActivity;
import com.KKDev.kosmat.MainActivity;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.adapter.ViewPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class ListKamarFragment extends Fragment {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listkamar, container, false);

        FloatingActionButton btn_editHarga = view.findViewById(R.id.btn_editHargaKamar);
        FloatingActionButton btn_tambahKamar = view.findViewById(R.id.btn_tambahKamar);
        viewPager = view.findViewById(R.id.viewKamar);
        tabLayout = view.findViewById(R.id.tabKamar);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new KamarTerisiFragment());
        fragments.add(new KamarKosongFragment());

        // Add more fragments as needed
        FragmentActivity fragmentActivity = requireActivity();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentActivity.getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Terisi");
                    break;
                case 1:
                    tab.setText("Kosong");
                    break;
            }
        }).attach();

        btn_tambahKamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KamarActivity.class);
                intent.putExtra("mode","new");
                startActivity(intent);
            }
        });

        btn_editHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).showBS_editKamar();
            }
        });
        return view;
    }
}