package com.KKDev.kosmat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeImageTransform;
import androidx.transition.TransitionSet;

public class loginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        TextView buttonTextView = view.findViewById(R.id.btn_GotoRegister);
        Button btn_login = view.findViewById(R.id.btn_Login);
        buttonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the destination fragment
                openDestinationFragmentWithTransitions(view, new registerFragment());
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), "Beralih ke menu utama", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private void openDestinationFragmentWithTransitions(View view,Fragment destinationFragment) {

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
