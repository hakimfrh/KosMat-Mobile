package com.KKDev.kosmat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.KKDev.kosmat.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class EditProfileFragment extends Fragment {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        Bundle bundle = getArguments();
        User user = (User) bundle.getSerializable("user");
        ImageView img_editProfile = view.findViewById(R.id.edit_imgprofile);

        byte[] byteArray = user.getImageByte();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        img_editProfile.setImageBitmap(bitmap);

        List<String> genderList = new ArrayList<>();
        genderList.add("Jenis Kelamin");
        genderList.add("Laki-Laki");
        genderList.add("Perempuan");

        TextInputLayout edit_txtx_Nama = view.findViewById(R.id.edit_txt_namaLengkap);
        TextInputLayout edit_txtx_email = view.findViewById(R.id.edit_txt_email);
        TextInputLayout edit_txtx_whatsapp = view.findViewById(R.id.edit_txt_whatsapp);
        TextInputLayout edit_txtx_username = view.findViewById(R.id.edit_txt_regUsername);
        TextInputLayout edit_txtx_password = view.findViewById(R.id.edit_txt_regPassword);
        TextInputLayout edit_txtx_tanggal = view.findViewById(R.id.edit_txt_tglLahir);
        TextInputEditText edit_txt_nama = (TextInputEditText) edit_txtx_Nama.getEditText();
        TextInputEditText edit_txt_email = (TextInputEditText) edit_txtx_email.getEditText();
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
        edit_txt_email.setText(user.getNik());
        edit_txt_whatsapp.setText(user.getNo_whatsapp());
        edit_txt_username.setText(user.getUsername());
        edit_txt_password.setText(user.getPassword());


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
                                editor.putString("username", "");
                                editor.putString("password", "");
                                editor.putBoolean("checkBox", false);
                                editor.apply();

                                Intent intent = new Intent(getActivity(), LogRegActivity.class);
                                startActivity(intent);

                                Toast.makeText(getContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show();
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

        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeImageTransform());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        setSharedElementReturnTransition(transitionSet);
        return view;
    }
}