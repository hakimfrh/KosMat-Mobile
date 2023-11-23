package com.KKDev.kosmat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.KKDev.kosmat.fragment.DashboardFragment;
import com.KKDev.kosmat.fragment.LaporanFragment;
import com.KKDev.kosmat.fragment.ListKamarFragment;
import com.KKDev.kosmat.fragment.PenyewaFragment;
import com.KKDev.kosmat.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private BottomSheetBehavior bottomSheet_pengguna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //disable darkmode
        setContentView(R.layout.activity_main);

        View bottomSheet_tagihan = findViewById(R.id.bs_tagihan);
        View bs_pengguna = findViewById(R.id.bs_pengguna);
        bottomSheet_pengguna = BottomSheetBehavior.from(bs_pengguna);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        loadFragment(new DashboardFragment(bottomSheet_tagihan));

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.dashboard) {
                    loadFragment(new DashboardFragment(bottomSheet_tagihan));
                    return true;
                } else if (item.getItemId() == R.id.kamar) {
                    loadFragment(new ListKamarFragment());
                    return true;
                } else if (item.getItemId() == R.id.laporan) {
                    loadFragment(new LaporanFragment());
                    return true;
                } else if (item.getItemId() == R.id.penyewa) {
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

        if (backStackEntryCount < 0) {
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
    public void showBS_Pengguna(User user){
        TextView tx_pengguna = findViewById(R.id.tx_bs_namaPengguna);
        TextView tx_nik = findViewById(R.id.tx_bs_nik);
        TextView tx_kamar = findViewById(R.id.tx_bs_kamar);
        TextView tx_alamat = findViewById(R.id.tx_bs_alamat);
        TextView tx_whatsapp = findViewById(R.id.tx_bs_whatsapp);
        TextView tx_whatsappWali = findViewById(R.id.tx_bs_whatsappWali);

        tx_pengguna.setText(user.getNama());
        tx_nik.setText(user.getNik());
        //tx_kamar.setText(user.);
//        tx_alamat.setText(user.get);
        tx_whatsapp.setText(user.getNo_whatsapp());
        tx_whatsappWali.setText(user.getNo_whatsapp_wali());
        bottomSheet_pengguna.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

}