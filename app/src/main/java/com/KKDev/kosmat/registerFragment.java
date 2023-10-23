package com.KKDev.kosmat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.KKDev.kosmat.adapter.sqliteHelper;
import com.KKDev.kosmat.adapter.User;

public class registerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        List<String> genderList = new ArrayList<>();
        genderList.add("Jenis Kelamin");
        genderList.add("Laki-Laki");
        genderList.add("Perempuan");

        TextInputLayout txtx_Nama = view.findViewById(R.id.txt_namaLengkap);
        TextInputLayout txtx_email = view.findViewById(R.id.txt_email);
        TextInputLayout txtx_whatsapp = view.findViewById(R.id.txt_whatsapp);
        TextInputLayout txtx_username = view.findViewById(R.id.txt_regUsername);
        TextInputLayout txtx_password = view.findViewById(R.id.txt_regPassword);
        TextInputLayout txtx_tanggal = view.findViewById(R.id.txt_tglLahir);
        TextInputEditText txt_nama = (TextInputEditText) txtx_Nama.getEditText();
        TextInputEditText txt_email = (TextInputEditText) txtx_email.getEditText();
        TextInputEditText txt_whatsapp = (TextInputEditText) txtx_whatsapp.getEditText();
        TextInputEditText txt_username = (TextInputEditText) txtx_username.getEditText();
        TextInputEditText txt_password = (TextInputEditText) txtx_password.getEditText();
        TextInputEditText txt_tanggal = (TextInputEditText) txtx_tanggal.getEditText();
        Spinner sp_gender = view.findViewById(R.id.sp_gender);
        TextView buttonTextView = view.findViewById(R.id.btn_GotoLogin);
        Button btn_register = view.findViewById(R.id.btn_Register);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, genderList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(adapter);

        txt_nama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if (!input.matches("^[a-zA-Z\\s]*$")) {
                    txt_nama.setError("Namamu aneh");
                } else {
                    txt_nama.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
             //tidak digunakan
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString();
                if (!email.contains("@")||!email.contains(".")) {
                    txt_email.setError("masukkan email yang valid");
                } else {
                    txt_email.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            //tidak digunakan
            }
        });

        txt_tanggal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(txt_tanggal);
                    txt_tanggal.clearFocus();
                }
            }
        });

        txt_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txt_username.getText().length()<4){
                    txt_username.setError("Kependekan bro");
                } else if (txt_username.getText().length()>16) {
                    txt_username.setError("Kepankangan bro");
                }else{
                    txt_username.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(txt_password.getText().length()<4){
                    txt_password.setError("Kependekan bro");
                } else if (txt_password.getText().length()>16) {
                    txt_password.setError("Kepankangan bro");
                }else{
                    txt_password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back to login page
                requireActivity().onBackPressed();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nik = txt_email.getText().toString();
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();
                String nama = txt_nama.getText().toString();
                String noWhatsapp = txt_whatsapp.getText().toString();
                String privilege = "0";
                String tglLahir = txt_tanggal.getText().toString();
                String gender =sp_gender.getSelectedItem().toString();

                boolean isValid = true;
                if(TextUtils.isEmpty(nama)){
                    txt_nama.setError("Tidak boleh kosong");
                    isValid = false;
                }if(TextUtils.isEmpty(nik)){
                    txt_email.setError("Tidak boleh kosong");
                    isValid = false;
                }if(TextUtils.isEmpty(noWhatsapp)){
                    txt_whatsapp.setError("Tidak boleh kosong");
                    isValid = false;
                }if(TextUtils.isEmpty(tglLahir)){
                    txt_tanggal.setError("Tidak boleh kosong");
                    isValid = false;
                }if(TextUtils.isEmpty(username)){
                    txt_username.setError("Tidak boleh kosong");
                    isValid = false;
                }if(TextUtils.isEmpty(password)){
                    txt_password.setError("Tidak boleh kosong");
                    isValid = false;
                }if(gender.equals("Jenis Kelamin")){
                    isValid = false;
                }

                if(isValid){
                    sqliteHelper db = new sqliteHelper(container.getContext());
                    User user = new User(nik,username,password,nama,noWhatsapp,privilege,tglLahir,gender);
                    db.register(user);
                }
            }
        });


        // Set up shared element transition for re-enter transition
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeImageTransform());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        setSharedElementReturnTransition(transitionSet);

        return view;
    }
    private void showDatePickerDialog(TextInputEditText textField) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update the EditText with the selected date
                        String tanggal = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        textField.setText(tanggal);

                        // Calculate age
                        Calendar dob = Calendar.getInstance();
                        dob.set(year, monthOfYear, dayOfMonth);
                        Calendar today = Calendar.getInstance();
                        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                        // Check if age is not 18
                        if (age <= 18) {
                            // Show the "age not accepted" message]
                        } else {
                            // Hide the "age not accepted" message
                        }
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
    private void openDestinationFragmentWithoutTransitions(View view,Fragment destinationFragment) {

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.logRegFragment, destinationFragment); // Replace with the correct container ID
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
