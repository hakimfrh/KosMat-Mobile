package com.KKDev.kosmat.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.KKDev.kosmat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LupaPasswordFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lupa_password, container, false);

        TextInputLayout txtx_password_1 = view.findViewById(R.id.txt_gantipassword_1);
        TextInputLayout txtx_password_2= view.findViewById(R.id.txt_gantipassword_2);
        TextInputEditText txt_password_1 = (TextInputEditText) txtx_password_1.getEditText();
        TextInputEditText txt_password_2 = (TextInputEditText) txtx_password_2.getEditText();
        Button btn_resetPassword = view.findViewById(R.id.btn_gantiPassword);


        txt_password_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()<4){
                    txtx_password_1.setError("harus lebih dari 4 karakter");
                }else{txtx_password_1.setError(null);}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_password_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()<4){
                    txtx_password_2.setError("harus lebih dari 4 karakter");
                } else{txtx_password_2.setError(null);}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btn_resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password_1 = txt_password_1.getText().toString();
                String password_2 = txt_password_2.getText().toString();
                if(password_1.equals(password_2)){

                }else{
                    txtx_password_2.setError("Password tidak sama");
                }
            }
        });


        return view;
    }
}