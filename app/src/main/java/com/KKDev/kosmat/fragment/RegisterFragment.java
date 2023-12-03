package com.KKDev.kosmat.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.KKDev.kosmat.Api;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.retrofit.DatabaseCallback;
import com.KKDev.kosmat.retrofit.DatabaseConnection;
import com.KKDev.kosmat.model.UserResponse;
import com.KKDev.kosmat.whatsappVerificationActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.KKDev.kosmat.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterFragment extends Fragment {

    private static final int WHATSAPP_VERIFICATION = 2;
    User user;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WHATSAPP_VERIFICATION) {
            if (resultCode == 1) {
                Toast.makeText(getContext(), "Verifikasi whatsapp berhasil", Toast.LENGTH_SHORT).show();
                registerUser(user);
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        List<String> genderList = new ArrayList<>();
        genderList.add("Jenis Kelamin");
        genderList.add("Laki Laki");
        genderList.add("Perempuan");

        TextInputLayout txtx_nama = view.findViewById(R.id.txt_namaLengkap);
        TextInputLayout txtx_nik = view.findViewById(R.id.txt_nik);
        TextInputLayout txtx_whatsapp = view.findViewById(R.id.txt_whatsapp);
        TextInputLayout txtx_username = view.findViewById(R.id.txt_regUsername);
        TextInputLayout txtx_password = view.findViewById(R.id.txt_regPassword);
        TextInputLayout txtx_tanggal = view.findViewById(R.id.txt_tglLahir);
        TextInputEditText txt_nama = (TextInputEditText) txtx_nama.getEditText();
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
                    txtx_nama.setError("Nama hanya boleh huruf");
                } else {
                    txtx_nama.setError(null);
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
                    txtx_nik.setError("Masukkan NIK yang valid");
                } else {
                    txtx_nik.setError(null);
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
                    txtx_whatsapp.setError("Masukan Nomor yang valid");
                } else if (txt_whatsapp.length() > 2) {
                    if (!txt_whatsapp.getText().toString().substring(0, 2).equals("08")) {
                        txtx_whatsapp.setError("Masukkan nomor yang valid");
                    } else {
                        txtx_whatsapp.setError(null);
                    }
                } else {
                    txtx_whatsapp.setError(null);
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
                    showDatePickerDialog(txtx_tanggal);
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
                    txtx_username.setError("Username minimal 4 karakter");
                } else if (txt_username.getText().length() > 16) {
                    txtx_username.setError("Username maksimal 16 karakter");
                } else {
                    txtx_username.setError(null);
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
                    txtx_password.setError("Username minimal 4 karater");
                } else if (txt_password.getText().length() > 16) {
                    txtx_password.setError("Username maksimal 4 karakter");
                } else {
                    txtx_password.setError(null);
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
                String privilege = "1";
                String tglLahir = txt_tanggal.getText().toString();
                String gender = sp_gender.getSelectedItem().toString();
                boolean isValid = true;
                if(nik.length()!=16){
                    txtx_nik.setError("Masukkan NIK yang valid");
                    isValid = false;
                }
                if (TextUtils.isEmpty(nama)) {
                    txtx_nama.setError("Tidak boleh kosong");
                    isValid = false;
                }
                if (TextUtils.isEmpty(nik)) {
                    txtx_nik.setError("Tidak boleh kosong");
                    isValid = false;
                }
                if (TextUtils.isEmpty(noWhatsapp)) {
                    txtx_whatsapp.setError("Tidak boleh kosong");
                    isValid = false;
                }
                if (TextUtils.isEmpty(tglLahir)) {
                    txtx_tanggal.setError("Tidak boleh kosong");
                    isValid = false;
                }
                if (TextUtils.isEmpty(username)) {
                    txtx_username.setError("Tidak boleh kosong");
                    isValid = false;
                }
                if (TextUtils.isEmpty(password)) {
                    txtx_password.setError("Tidak boleh kosong");
                    isValid = false;
                }
                if (gender.equals("Jenis Kelamin")) {
                    isValid = false;
                }
                if (!(txtx_nama.getError() == null) || !(txtx_nik.getError() == null) || !(txtx_whatsapp.getError() == null) || !(txtx_tanggal.getError() == null) || !(txtx_username.getError() == null) || !(txtx_password.getError() == null)) {
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
                            user = new User(nik, username, password, nama, noWhatsapp, noWhatsappWali, privilege, tglLahir, gender, image);
                            Intent intent = new Intent(getActivity(), whatsappVerificationActivity.class);
                            intent.putExtra("whatsapp",noWhatsapp);
                            startActivityForResult(intent,WHATSAPP_VERIFICATION);
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

    private void showDatePickerDialog(TextInputLayout textInputLayout) {
        TextInputEditText textInputEditText = (TextInputEditText) textInputLayout.getEditText();
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Update the EditText with the selected date
                String tanggal = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                textInputEditText.setText(tanggal);
                textInputEditText.clearFocus();
                textInputLayout.setError(null);
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

    public void registerUser(User user) {
        String url = Api.urlUser;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        // Create JSON object
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("method", "register");
            jsonObject.put("nik", user.getNik());
            jsonObject.put("username", user.getUsername());
            jsonObject.put("password", user.getPassword());
            jsonObject.put("nama", user.getNama());
            jsonObject.put("no_whatsapp", user.getNo_whatsapp());
            jsonObject.put("no_whatsapp_wali", user.getNo_whatsapp_wali());
            jsonObject.put("privilege", user.getPrivilege());
            jsonObject.put("tgl_lahir", user.getTgl_lahir());
            jsonObject.put("gender", user.getGender());
            jsonObject.put("image", user.getImage());
        } catch (JSONException e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Error").setMessage(e.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            return;
        }

        // Create the request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String status = response.getString("status");

                            // Handle the response based on code and status
                            if (status.equals("User Registered")) {
                                AlertDialog.Builder success = new AlertDialog.Builder(getActivity());
                                success.setTitle("Register berhasil").setMessage("Silahkan login kembali").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Dismiss the dialog

                                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getContext().MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("username", user.getUsername());
                                        editor.putString("password", user.getPassword());
                                        editor.apply();
                                        dialog.dismiss();
                                        requireActivity().onBackPressed();
                                    }
                                }).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Error").setMessage(status).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Error").setMessage(e.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

}
