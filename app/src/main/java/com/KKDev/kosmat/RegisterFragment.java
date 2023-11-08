package com.KKDev.kosmat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.KKDev.kosmat.retrofit.DatabaseCallback;
import com.KKDev.kosmat.retrofit.DatabaseConnection;
import com.KKDev.kosmat.model.UserResponse;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.KKDev.kosmat.model.User;

import org.json.JSONException;

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        List<String> genderList = new ArrayList<>();
        genderList.add("Jenis Kelamin");
        genderList.add("Laki Laki");
        genderList.add("Perempuan");

        TextInputLayout txtx_Nama = view.findViewById(R.id.txt_namaLengkap);
        TextInputLayout txtx_nik = view.findViewById(R.id.txt_nik);
        TextInputLayout txtx_whatsapp = view.findViewById(R.id.txt_whatsapp);
        TextInputLayout txtx_username = view.findViewById(R.id.txt_regUsername);
        TextInputLayout txtx_password = view.findViewById(R.id.txt_regPassword);
        TextInputLayout txtx_tanggal = view.findViewById(R.id.txt_tglLahir);
        TextInputEditText txt_nama = (TextInputEditText) txtx_Nama.getEditText();
        TextInputEditText txt_nik = (TextInputEditText) txtx_nik.getEditText();
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

        txt_nik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //tidak digunakan
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nik = s.toString();
                if (nik.length() > 16) {
                    txt_nik.setError("NIK Tidak Valid");
                } else {
                    txt_nik.setError(null);
                }
           /* 
                if (!email.contains("@") || !email.contains(".")) {
                    txt_nik.setError("masukkan email yang valid");
                }
            */
            }

            @Override
            public void afterTextChanged(Editable s) {
                //tidak digunakan
            }
        });

        txt_whatsapp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_whatsapp.length() > 13) {
                    txt_whatsapp.setError("Nomormu kepanjangan bro");
                } else if (txt_whatsapp.length() > 2) {
                    if (!txt_whatsapp.getText().toString().substring(0, 2).equals("08")) {
                        txt_whatsapp.setError("Masukkan nomor yang valid");
                    } else {
                        txt_whatsapp.setError(null);
                    }
                } else {
                    txt_whatsapp.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_tanggal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog(txt_tanggal);
                }
            }
        });

        txt_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_username.getText().length() < 4) {
                    txt_username.setError("Kependekan bro");
                } else if (txt_username.getText().length() > 16) {
                    txt_username.setError("Kepanjangan bro");
                } else {
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
                if (txt_password.getText().length() < 4) {
                    txt_password.setError("Kependekan bro");
                } else if (txt_password.getText().length() > 16) {
                    txt_password.setError("Kepanjangan bro");
                } else {
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
                // Assuming you have an XML drawable named "my_drawable"
                Drawable drawable = getResources().getDrawable(R.drawable.icon_person2);

                // Convert the drawable to a bitmap
                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);

                // Convert the bitmap to a byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                String nik = txt_nik.getText().toString();
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();
                String nama = txt_nama.getText().toString();
                String noWhatsapp = txt_whatsapp.getText().toString();
                String noWhatsappWali = "";
                String privilege = "0";
                String tglLahir = txt_tanggal.getText().toString();
                String gender = sp_gender.getSelectedItem().toString();
                boolean isValid = true;
                if (TextUtils.isEmpty(nama)) {
                    txt_nama.setError("Tidak boleh kosong");
                    isValid = false;
                }
                if (TextUtils.isEmpty(nik)) {
                    txt_nik.setError("Tidak boleh kosong");
                    isValid = false;
                }
                if (TextUtils.isEmpty(noWhatsapp)) {
                    txt_whatsapp.setError("Tidak boleh kosong");
                    isValid = false;
                }
                if (TextUtils.isEmpty(tglLahir)) {
                    txt_tanggal.setError("Tidak boleh kosong");
                    isValid = false;
                }
                if (TextUtils.isEmpty(username)) {
                    txt_username.setError("Tidak boleh kosong");
                    isValid = false;
                }
                if (TextUtils.isEmpty(password)) {
                    txt_password.setError("Tidak boleh kosong");
                    isValid = false;
                }
                if (gender.equals("Jenis Kelamin")) {
                    isValid = false;
                }
                if (!(txt_nama.getError() == null) || !(txt_nik.getError() == null) || !(txt_whatsapp.getError() == null) || !(txt_tanggal.getError() == null) || !(txt_username.getError() == null) || !(txt_password.getError() == null)) {
                    isValid = false;
                }
                if (isValid) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    String data = "username \t: " + username + "\n" + "password \t: " + password + "\n" + "nik \t\t\t\t\t\t\t: " + nik + "\n" + "whatsapp \t: " + noWhatsapp + "\n" + "tgl-lahir \t\t\t: " + tglLahir + "\n" + "gender \t\t\t\t: " + gender + "\n";
                    builder.setTitle("Konfirmasi").setMessage("Apakah data yang dimasukkan sudah benar?\n\n" + data).setPositiveButton("YA", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //SqliteHelper db = new SqliteHelper(container.getContext());
                            DatabaseConnection db = new DatabaseConnection();

                            User user = new User(nik, username, password, nama, noWhatsapp, noWhatsappWali, privilege, tglLahir, gender, image);
                            try {
                                db.registerUser(user, new DatabaseCallback<UserResponse>() {
                                    @Override
                                    public void onSuccess(UserResponse data) {
                                        //if (data.getCode() == 200) {
                                        if (data.getStatus().equals("User Registered")) {
                                            AlertDialog.Builder success = new AlertDialog.Builder(getActivity());
                                            success.setTitle("Register berhasil").setMessage("Silahkan login kembali").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // Dismiss the dialog

                                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getContext().MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("username", username);
                                                    editor.putString("password", password);
                                                    editor.apply();
                                                    dialog.dismiss();
                                                    requireActivity().onBackPressed();
                                                }
                                            }).show();
                                        } else {
                                            AlertDialog.Builder success = new AlertDialog.Builder(getActivity());
                                            success.setTitle("Gagal").setMessage(data.getStatus()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable t) {
                                        AlertDialog.Builder success = new AlertDialog.Builder(getActivity());
                                        success.setTitle("Error").setMessage(t.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                                    }
                                });
                            } catch (JSONException e) {
                                AlertDialog.Builder success = new AlertDialog.Builder(getActivity());
                                success.setTitle("Error").setMessage(e.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                                throw new RuntimeException(e);
                            }

                        }
                    }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

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

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Update the EditText with the selected date
                String tanggal = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                textField.setText(tanggal);
                textField.clearFocus();
                textField.setError(null);
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

    private void openDestinationFragmentWithoutTransitions(View view, Fragment destinationFragment) {

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.logRegFragment, destinationFragment); // Replace with the correct container ID
        transaction.addToBackStack(null);
        transaction.commit();
    }

}