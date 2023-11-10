package com.KKDev.kosmat.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeImageTransform;
import androidx.transition.TransitionSet;

import com.KKDev.kosmat.LupaPasswordActivity;
import com.KKDev.kosmat.MainActivity;
import com.KKDev.kosmat.R;
import com.KKDev.kosmat.model.User;
import com.KKDev.kosmat.retrofit.DatabaseCallback;
import com.KKDev.kosmat.retrofit.DatabaseConnection;
import com.KKDev.kosmat.model.UserResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoginFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", getContext().MODE_PRIVATE);


        TextInputLayout txtx_password = view.findViewById(R.id.txt_password);
        TextInputLayout txtx_username = view.findViewById(R.id.txt_username);
        TextInputEditText txt_username = (TextInputEditText) txtx_username.getEditText();
        TextInputEditText txt_password = (TextInputEditText) txtx_password.getEditText();
        TextView buttonTextView = view.findViewById(R.id.btn_GotoRegister);
        TextView btn_lupaPass = view.findViewById(R.id.btn_lupapassword);
        CheckBox cb_ingatSaya = view.findViewById(R.id.cb_ingatSaya);
        Button btn_login = view.findViewById(R.id.btn_Login);

        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        boolean savedCheckbox = sharedPreferences.getBoolean("checkBox", false);

        txt_username.setText(savedUsername);
        //txt_password.setText(savedPassword);

        buttonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the destination fragment
                openDestinationFragmentWithTransitions(view, new RegisterFragment());
            }
        });
        btn_lupaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LupaPasswordActivity.class);
                intent.putExtra("username", txt_username.getText());
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {

                } else {

                    DatabaseConnection db = new DatabaseConnection();
                    db.login(username, password, new DatabaseCallback<UserResponse>() {
                        @Override
                        public void onSuccess(UserResponse userResponse) {
                            if (userResponse.getStatus().equals("ok")) {
                                List<User> userList = userResponse.getUserlist();
                                User user = userList.get(0);
                                if (user != null) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", username);
                                    editor.putString("password", password);
                                    editor.putBoolean("checkBox", cb_ingatSaya.isChecked());
                                    editor.apply();

                                    login(user);
                                } else {
                                    // Show an alert dialog for incorrect login
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("Gagal Login").setMessage("Username atau Password salah").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Dismiss the dialog
                                            dialog.dismiss();
                                        }
                                    }).show();
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            // Handle error
                            // Show an alert dialog for error
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Error").setMessage(t.toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Dismiss the dialog
                                    dialog.dismiss();
                                }
                            }).show();
                        }
                    });
                }
            }

        });

        return view;
    }

    private void login(User user) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        Toast.makeText(getContext(), "Berhasil Login sebagai " + user.getNama(), Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    private void openDestinationFragmentWithTransitions(View view, Fragment destinationFragment) {

        // Set up shared element transition
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeImageTransform());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        destinationFragment.setSharedElementEnterTransition(transitionSet);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.logRegFragment, destinationFragment); // Replace with the correct container ID
        transaction.addToBackStack(null);
        transaction.addSharedElement(view.findViewById(R.id.logo), "shared_logo");
        transaction.addSharedElement(view.findViewById(R.id.textView3), "shared_text");
        transaction.addSharedElement(view.findViewById(R.id.txt_username), "shared_username");
        transaction.addSharedElement(view.findViewById(R.id.txt_password), "shared_password");
        transaction.addSharedElement(view.findViewById(R.id.btn_Login), "shared_button");
        transaction.addSharedElement(view.findViewById(R.id.btn_GotoRegister), "shared_button2");
        transaction.addSharedElement(view.findViewById(R.id.tv_belumPunyaAkun), "shared_textView");
        transaction.addSharedElement(view.findViewById(R.id.cardView), "shared_card");
        transaction.commit();
    }
}
