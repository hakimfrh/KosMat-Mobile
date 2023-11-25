package com.KKDev.kosmat.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.KKDev.kosmat.Api;
import com.KKDev.kosmat.LogRegActivity;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.model.User;
import com.KKDev.kosmat.model.UserResponse;
import com.KKDev.kosmat.retrofit.DatabaseCallback;
import com.KKDev.kosmat.retrofit.DatabaseConnection;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import android.Manifest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditProfileFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    Bitmap profile_image;
    ImageView img_editProfile;

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (data != null) {
                profile_image = (Bitmap) data.getExtras().get("data");
                img_editProfile.setImageBitmap(profile_image);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        Bundle bundle = getArguments();
        User user = (User) bundle.getSerializable("user");
        img_editProfile = view.findViewById(R.id.edit_imgprofile);

        byte[] byteArray = user.getImageByte();
        profile_image= BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        img_editProfile.setImageBitmap(profile_image);

        List<String> genderList = new ArrayList<>();
        genderList.add("Jenis Kelamin");
        genderList.add("Laki Laki");
        genderList.add("Perempuan");

        LinearLayout tx_editprofile = view.findViewById(R.id.tx_editprofile);
        TextInputLayout edit_txtx_Nama = view.findViewById(R.id.edit_txt_namaLengkap);
        TextInputLayout edit_txtx_email = view.findViewById(R.id.edit_txt_nik);
        TextInputLayout edit_txtx_whatsapp = view.findViewById(R.id.edit_txt_whatsapp);
        TextInputLayout edit_txtx_username = view.findViewById(R.id.edit_txt_regUsername);
        TextInputLayout edit_txtx_password = view.findViewById(R.id.edit_txt_regPassword);
        TextInputLayout edit_txtx_tanggal = view.findViewById(R.id.edit_txt_tglLahir);
        TextInputEditText edit_txt_nama = (TextInputEditText) edit_txtx_Nama.getEditText();
        TextInputEditText edit_txt_nik = (TextInputEditText) edit_txtx_email.getEditText();
        TextInputEditText edit_txt_whatsapp = (TextInputEditText) edit_txtx_whatsapp.getEditText();
        TextInputEditText edit_txt_username = (TextInputEditText) edit_txtx_username.getEditText();
        TextInputEditText edit_txt_password = (TextInputEditText) edit_txtx_password.getEditText();
        TextInputEditText edit_txt_tanggal = (TextInputEditText) edit_txtx_tanggal.getEditText();
        Spinner sp_gender = view.findViewById(R.id.edit_sp_gender);
        Button btn_register = view.findViewById(R.id.btn_edit);
        ImageView img_logout = view.findViewById(R.id.btn_logout);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, genderList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(adapter);

        edit_txt_nama.setText(user.getNama());
        edit_txt_nik.setText(user.getNik());
        edit_txt_whatsapp.setText(user.getNo_whatsapp());
        edit_txt_tanggal.setText(user.getTgl_lahir());
        edit_txt_username.setText(user.getUsername());
        edit_txt_password.setText(user.getPassword());
        sp_gender.setSelection(user.getGender().equals("Laki Laki") ? 1 : 2);


        edit_txt_tanggal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) showDatePickerDialog(edit_txt_tanggal);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Drawable drawable = img_editProfile.getDrawable();
//
//                // Convert the drawable to a bitmap
//                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//                drawable.draw(canvas);

                Bitmap bitmap = profile_image;
                // Convert the bitmap to a byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] image = stream.toByteArray();


                String nik = edit_txt_nik.getText().toString();
                String username = edit_txt_username.getText().toString();
                String password = edit_txt_password.getText().toString();
                String nama = edit_txt_nama.getText().toString();
                String noWhatsapp = edit_txt_whatsapp.getText().toString();
                String noWhatsappWali = "";
                String privilege = "0";
                String tglLahir = edit_txt_tanggal.getText().toString();
                String gender = sp_gender.getSelectedItem().toString();

                User newUser = new User(nik, username, password, nama, noWhatsapp, noWhatsappWali, privilege, tglLahir, gender, image);

                String url = Api.urlUser;

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                // Create JSON object
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("method", "update");
                    jsonObject.put("user_nik", user.getNik());
                    jsonObject.put("nik", newUser.getNik());
                    jsonObject.put("username", newUser.getUsername());
                    jsonObject.put("password", newUser.getPassword());
                    jsonObject.put("nama", newUser.getNama());
                    jsonObject.put("no_whatsapp", newUser.getNo_whatsapp());
                    jsonObject.put("no_whatsapp_wali", newUser.getNo_whatsapp_wali());
                    jsonObject.put("privilege", newUser.getPrivilege());
                    jsonObject.put("tgl_lahir", newUser.getTgl_lahir());
                    jsonObject.put("gender", newUser.getGender());

                    String encodedImage = Base64.encodeToString(newUser.getImageByte(), Base64.DEFAULT);
                    jsonObject.put("image", encodedImage);
                } catch (JSONException e) {
                    e.printStackTrace();
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                    builder.setTitle("Error").setMessage(e.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    return;
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int code = response.getInt("code");
                                    String status = response.getString("status");

                                    // Handle the response based on code and status
                                    if (status.equals("ok")) {
                                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getContext().MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("username", username);
                                        editor.putString("password", password);
                                        editor.putBoolean("checkBox", true);
                                        editor.apply();

                                        Intent intent = new Intent(getActivity(), LogRegActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();


                                    } else {
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                                        builder.setTitle("Error").setMessage(status).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
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
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                                builder.setTitle("Error").setMessage(error.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();

                            }
                        });

                // Add the request to the RequestQueue
                requestQueue.add(jsonObjectRequest);
            }
        });

        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Konfirmasi")
                        .setMessage("Apakah kamu mau keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Close the app
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getContext().MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                //editor.putString("username", "");
                                editor.putString("password", "");
                                editor.putBoolean("checkBox", false);
                                editor.apply();

                                Intent intent = new Intent(getActivity(), LogRegActivity.class);
                                startActivity(intent);
                                Toast.makeText(getContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Dismiss the dialog
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        tx_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        img_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it
                    requestPermissions(
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_IMAGE_CAPTURE
                    );
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

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
}