package com.KKDev.kosmat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class registerFragment extends Fragment {

    ImageView date;
    EditText txt_tanggal;
    ImageView btn_camera;
    Bitmap bitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), new String[]{
                    Manifest.permission.CAMERA
            },100);
        }

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        List<String> dataAgama = new ArrayList<>();
        dataAgama.add("Islam");
        dataAgama.add("Kristen");
        dataAgama.add("Katolik");
        dataAgama.add("Hindu");
        dataAgama.add("Budha");
        dataAgama.add("Khonghucu");

        TextInputLayout txtx_Nama = view.findViewById(R.id.txt_namaLengkap);
        TextInputLayout txtx_email = view.findViewById(R.id.txt_email);
        TextInputLayout txtx_username = view.findViewById(R.id.txt_regUsername);
        TextInputLayout txtx_password = view.findViewById(R.id.txt_regPassword);
        TextInputEditText txt_nama = (TextInputEditText) txtx_Nama.getEditText();
        TextInputEditText txt_email = (TextInputEditText) txtx_email.getEditText();
        TextInputEditText txt_username = (TextInputEditText) txtx_username.getEditText();
        TextInputEditText txt_password = (TextInputEditText) txtx_password.getEditText();
        txt_tanggal = view.findViewById(R.id.txt_tglLahir);
        RadioGroup rg_gender = view.findViewById(R.id.radioGroup);
        RadioButton rb_lakiLaki = view.findViewById(R.id.radio_laki);
        RadioButton rb_perempuan = view.findViewById(R.id.radio_perempuan);
        Spinner sp_agama = view.findViewById(R.id.agama);
        TextView buttonTextView = view.findViewById(R.id.btn_GotoLogin);
        TextView tx_emailNotValid = view.findViewById(R.id.tx_emailNotValid);
        date = view.findViewById(R.id.datepick);
        btn_camera = view.findViewById(R.id.btn_camera);
        Button btn_register = view.findViewById(R.id.btn_Register);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        tx_emailNotValid.setVisibility(View.INVISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dataAgama);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_agama.setAdapter(adapter);

        txt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
             //tidak digunakan
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString();
                if (!email.contains("@")) {
                    tx_emailNotValid.setVisibility(TextView.VISIBLE);
                } else {
                    tx_emailNotValid.setVisibility(TextView.INVISIBLE);
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
                    showDatePickerDialog();
                }
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
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
                Intent intent = new Intent(getContext(), DescRegisterActivity.class);
                intent.putExtra("nama", txt_nama.getText().toString());
                intent.putExtra("email", txt_email.getText().toString());
                intent.putExtra("gender", rb_lakiLaki.isChecked()?"Laki-Laki":"Perempuan");
                intent.putExtra("tanggal", txt_tanggal.getText().toString());
                intent.putExtra("agama", sp_agama.getSelectedItem().toString());
                intent.putExtra("username", txt_username.getText().toString());
                intent.putExtra("password", txt_password.getText().toString());
                intent.putExtra("image",bitmap);
                getContext().startActivity(intent);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            bitmap = (Bitmap) data.getExtras().get("data");

            btn_camera.setImageBitmap(bitmap);

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) btn_camera.getLayoutParams();
            params.width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            btn_camera.setLayoutParams(params);
        }
    }
    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update the EditText with the selected date
                        txt_tanggal.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
