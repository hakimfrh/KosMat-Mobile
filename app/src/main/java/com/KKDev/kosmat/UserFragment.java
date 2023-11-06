package com.KKDev.kosmat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.KKDev.kosmat.adapter.userAdapter;
import com.KKDev.kosmat.model.User;
import com.KKDev.kosmat.retrofit.DatabaseCallback;
import com.KKDev.kosmat.retrofit.DatabaseConnection;
import com.KKDev.kosmat.retrofit.UserResponse;

import java.util.List;

public class UserFragment extends Fragment {
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        DatabaseConnection db = new DatabaseConnection();
        db.getAllUser(new DatabaseCallback<UserResponse>() {
            @Override
            public void onSuccess(UserResponse data) {
                if (data.getStatus().equals("ok")) {
                    List<User> userList = data.getUserlist();

                    /*
                    User[] users = new User[userList.size()];
                    for (int i = 0; i < userList.size(); i++) {
                        users[i] = userList.get(i);
                    }
                    */

                    User[] users = userList.toArray(new User[0]);
                    recyclerView = view.findViewById(R.id.userlist);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    userAdapter adapter = new userAdapter(getContext(), users);
                    recyclerView.setAdapter(adapter);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Error")
                            .setMessage("Code: " + data.getCode() + "\n" + data.getStatus())
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Dismiss the dialog
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onError(Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Error")
                        .setMessage(t.toString())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Dismiss the dialog
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        return view;
    }
}
