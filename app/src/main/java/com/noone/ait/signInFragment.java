package com.noone.ait;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class signInFragment extends Fragment{
    EditText etEmail, etPassword;
    Button signInButton;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    public signInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        etEmail = view.findViewById(R.id.inEmail);
        etPassword = view.findViewById(R.id.inPassword);
        signInButton = view.findViewById(R.id.signInButt);
        progressBar = view.findViewById(R.id.progresser);
        firebaseAuth = FirebaseAuth.getInstance();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = etEmail.getText().toString().trim();
                String userPassword = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(userEmail)) {
                    Toast.makeText(getContext(), "Please enter the email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(getContext(), "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(view.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getContext(), ProfileActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                    Toast.makeText(getContext(), "Sign in Successful", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(view.GONE);
                                } else {
                                    Toast.makeText(getContext(), "Sign in Failed " + task.getException(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(view.GONE);
                                    etPassword.setText("");

                                }

                                // ...
                            }
                        });

            }
        });


        return view;
    }

}
