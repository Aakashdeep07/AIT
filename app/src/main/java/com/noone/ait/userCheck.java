package com.noone.ait;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.noone.ait.MainActivity.fragmentManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class userCheck extends Fragment {

    Button signIn, signUp;

    public userCheck() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_check, container, false);
        signIn = view.findViewById(R.id.inButton);
        signUp = view.findViewById(R.id.upButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInFragment signInFragment = new signInFragment();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, signInFragment, null).addToBackStack(null).commit();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpFragment signUpFragment = new signUpFragment();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer, signUpFragment, null).addToBackStack(null).commit();
            }
        });


        return view;

    }

}
