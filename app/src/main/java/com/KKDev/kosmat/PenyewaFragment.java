package com.KKDev.kosmat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.KKDev.kosmat.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class PenyewaFragment extends Fragment {

        private ViewPager2 viewPager;
        private TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_penyewa, container, false);


            viewPager = view.findViewById(R.id.viewPager);
            tabLayout = view.findViewById(R.id.tabLayout);

            List<Fragment> fragments = new ArrayList<>();
            fragments.add(new UserFragment());
            fragments.add(new PesanFragment());
            // Add more fragments as needed
            FragmentActivity fragmentActivity = requireActivity();
            ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentActivity.getSupportFragmentManager(), getLifecycle(), fragments);
            viewPager.setAdapter(adapter);

            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                switch (position) {
                    case 0:
                        tab.setText("Penyewa");
                        break;
                    case 1:
                        tab.setText("Pesan");
                        break;
                }
            }).attach();
        return view;
    }
}