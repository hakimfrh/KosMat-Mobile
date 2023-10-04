package com.KKDev.kosmat;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class registerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        TextView buttonTextView = view.findViewById(R.id.btn_GotoLogin);

        buttonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Back to login page
                requireActivity().onBackPressed();
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
}
