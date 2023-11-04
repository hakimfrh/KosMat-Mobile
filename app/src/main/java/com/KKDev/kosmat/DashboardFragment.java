package com.KKDev.kosmat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeImageTransform;
import androidx.transition.TransitionSet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.KKDev.kosmat.model.User;

public class DashboardFragment extends Fragment {
    private boolean isDashboardVisible = false;

    @Override
    public void onStart() {
        super.onStart();
        isDashboardVisible = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isDashboardVisible = false;
    }

    public boolean isDashboardVisible() {
        return isDashboardVisible;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        Intent intent = getActivity().getIntent();
        User user = (User) intent.getSerializableExtra("user");

        ImageView img_profile = view.findViewById(R.id.img_profile);
        TextView tx_namaUser = view.findViewById(R.id.tx_dsNama);
        TextView tx_description = view.findViewById(R.id.tx_dsDesc);

        String nama = user.getNama();
        String desc = "username \t: " + user.getUsername() + "\n"
                + "password \t: " + user.getPassword() + "\n"
                + "nik \t\t\t\t\t\t\t: " + user.getNik() + "\n"
                + "whatsapp \t: " + user.getNo_whatsapp() + "\n"
                + "tgl-lahir \t\t\t: " + user.getTgl_lahir() + "\n"
                + "gender \t\t\t\t: " + user.getGender() + "\n";

        tx_namaUser.setText(nama);
        tx_description.setText(desc);
        byte[] byteArray = user.getImageByte();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

// Assuming 'imageView' is your ImageView
        img_profile.setImageBitmap(bitmap);

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openDestinationFragmentWithTransitions(view, new editprofileFragment(),user);
            }
        });
        return view;
    }

    private void openDestinationFragmentWithTransitions(View view, Fragment destinationFragment, User user) {

        // Set up shared element transition
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeImageTransform());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        destinationFragment.setSharedElementEnterTransition(transitionSet);

        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        destinationFragment.setArguments(bundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFragmentContainer, destinationFragment); // Replace with the correct container ID
        transaction.addToBackStack(null);
        transaction.addSharedElement(view.findViewById(R.id.absoluteLayout), "background");
        transaction.addSharedElement(view.findViewById(R.id.circle1), "circle1");
        transaction.addSharedElement(view.findViewById(R.id.circle2), "circle2");
        transaction.addSharedElement(view.findViewById(R.id.img_profile), "img_profile");
        transaction.addSharedElement(view.findViewById(R.id.img_editprofile), "img_editProfile");
        transaction.commit();
    }
}