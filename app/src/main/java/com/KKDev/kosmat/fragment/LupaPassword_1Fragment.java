package com.KKDev.kosmat.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.KKDev.kosmat.LupaPasswordActivity;
import com.KKDev.kosmat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LupaPassword_1Fragment extends Fragment {

    private String noWhatsapp;
    boolean isUlangValid;
    TextView tx_kirimUlang;
    private CountDownTimer countDownTimer;
    private long initialDurationMillis = 1 * 60 * 1000;
    public LupaPassword_1Fragment(String noWhatsapp) {
        this.noWhatsapp = noWhatsapp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lupa_password_1, container, false);
        TextInputLayout txtx_code = view.findViewById(R.id.txt_kodewa);
        TextInputEditText txt_code = (TextInputEditText) txtx_code.getEditText();
        Button btn_lanjut = view.findViewById(R.id.lupapass_btn_lanjut);
        tx_kirimUlang = view.findViewById(R.id.tx_kirimUlang);
        TextView tx_watsapp = view.findViewById(R.id.tx_whatsapp);
        tx_watsapp.setText(noWhatsapp);

        startTimer();
        tx_kirimUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUlangValid){
                    ((LupaPasswordActivity) getActivity()).resendCode();
                    startTimer();
                }
            }
        });

        btn_lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputCode = txt_code.getText().toString();
                String result = ((LupaPasswordActivity) getActivity()).submitCode(inputCode);
                if(result.equals("ok")){

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragmentContainerView, new LupaPassword_2Fragment());
                    ft.commit();

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Gagal").setMessage(result).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Dismiss the dialog
                            dialog.dismiss();
                        }
                    }).show();
                }
            }
        });



        return view;
    }
    public void startTimer() {

        isUlangValid = false;
        countDownTimer = new CountDownTimer(initialDurationMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                String text = String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60);
                tx_kirimUlang.setText(text);
                tx_kirimUlang.setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onFinish() {
                isUlangValid = true;
                tx_kirimUlang.setText("Kirim Ulang Kode");
                tx_kirimUlang.setTextColor(getResources().getColor(R.color.link_blue));
            }
        };
        countDownTimer.start();
    }
}