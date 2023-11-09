package com.KKDev.kosmat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.KKDev.kosmat.fragment.DashboardFragment;
import com.KKDev.kosmat.fragment.LaporanFragment;
import com.KKDev.kosmat.fragment.ListKamarFragment;
import com.KKDev.kosmat.fragment.PenyewaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //disable darkmode
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        loadFragment(new DashboardFragment());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.dashboard) {
                    loadFragment(new DashboardFragment());
                    return true;
                } else if (item.getItemId() == R.id.kamar) {
                    loadFragment(new ListKamarFragment());
                    return true;
                } else if (item.getItemId() == R.id.laporan) {
                    loadFragment(new LaporanFragment());
                    return true;
                } else if (item.getItemId() == R.id.pesan) {
                    loadFragment(new PenyewaFragment());
                    return true;
                }
                return false;
            }
        });
    }

    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        //Toast.makeText(this, Integer.toString(backStackEntryCount), Toast.LENGTH_SHORT).show();

        //DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().findFragmentByTag("DashboardFragment");

        if (backStackEntryCount < 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Exit")
                    .setMessage("mau keluar?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Close the app
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss the dialog
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            super.onBackPressed(); // Perform default back behavior for other fragments
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainFragmentContainer, fragment);
        ft.commit();
    }

}