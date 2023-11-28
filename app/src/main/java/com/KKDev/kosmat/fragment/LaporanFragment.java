package com.KKDev.kosmat.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.KKDev.kosmat.R;
import com.KKDev.kosmat.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class LaporanFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_laporan, container, false);

        ViewPager2 viewPager = view.findViewById(R.id.viewLaporan);
        TabLayout tabLayout = view.findViewById(R.id.tabLaporan);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PengeluaranFragment());
        fragments.add(new PemasukanFragment());
        fragments.add(new KeuntunganFragment());
        // Add more fragments as needed
        FragmentActivity fragmentActivity = requireActivity();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentActivity.getSupportFragmentManager(), getLifecycle(), fragments);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Pengeluaran");
                    break;
                case 1:
                    tab.setText("Pemasukan");
                    break;
                case 2:
                    tab.setText("Keuntungan");
                    break;
            }
        }).attach();

        return view;
    }
}