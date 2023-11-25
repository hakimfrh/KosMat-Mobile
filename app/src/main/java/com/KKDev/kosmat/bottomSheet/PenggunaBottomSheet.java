package com.KKDev.kosmat.bottomSheet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.KKDev.kosmat.R;
import com.KKDev.kosmat.model.User;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PenggunaBottomSheet extends BottomSheetDialogFragment {
    User user;

    public PenggunaBottomSheet(User user) {
        this.user = user;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_pengguna, container, false);
        
        ImageView img_profile = view.findViewById(R.id.img_bs_pengguna);
        TextView tx_pengguna = view.findViewById(R.id.tx_bs_namaPengguna);
        TextView tx_nik = view.findViewById(R.id.tx_bs_nik);
        TextView tx_kamar = view.findViewById(R.id.tx_bs_kamar);
        TextView tx_alamat = view.findViewById(R.id.tx_bs_alamat);
        TextView tx_whatsapp = view.findViewById(R.id.tx_bs_whatsapp);
        TextView tx_whatsappWali = view.findViewById(R.id.tx_bs_whatsappWali);

        img_profile.setImageBitmap(user.getImageBitmap());
        tx_pengguna.setText(user.getNama());
        tx_nik.setText(user.getNik());
        //tx_kamar.setText(user.);
//        tx_alamat.setText(user.get);
        tx_whatsapp.setText(user.getNo_whatsapp());
        tx_whatsappWali.setText(user.getNo_whatsapp_wali());
        return view;
    }
}
