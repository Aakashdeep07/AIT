package com.noone.ait;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class signUpFragment extends Fragment {
    EditText etName, etRoll,etEmail, etPassword, etMobile;
    Spinner spinBranch, spinSemester;
    Button signUpButton;
    private FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    DatabaseReference databaseReference;

    public signUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        etName = view.findViewById(R.id.sname);
        etRoll = view.findViewById(R.id.userid);
        etEmail = view.findViewById(R.id.upEmail);
        etPassword = view.findViewById(R.id.upPassword);
        etMobile = view.findViewById(R.id.mobile);
        spinBranch = view.findViewById(R.id.branch);
        spinSemester = view.findViewById(R.id.semester);
        signUpButton = view.findViewById(R.id.signUpButt);
        progressBar = view.findViewById(R.id.progress);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String studentName = etName.getText().toString().trim();
                final String roll = etRoll.getText().toString().trim();
                final String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                final String mobile = etMobile.getText().toString().trim();
                final String course = spinBranch.getSelectedItem().toString();
                final String semester = spinSemester.getSelectedItem().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(roll)) {
                    Toast.makeText(getContext(), "Please Enter Roll Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(studentName)) {
                    Toast.makeText(getContext(), "Please Enter Student Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(getContext(), "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (roll.length()>11 || roll.length()<11){
                    Toast.makeText(getContext(), "Please Enter 11 digits Correct Roll No", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(view.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(view.VISIBLE);
                                if (task.isSuccessful()) {
                                    User information = new User(
                                            studentName,
                                            roll,
                                            email,
                                            mobile,
                                            course,
                                            semester,
                                            null
                                    );
                                    FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getContext(), "Sign Up Successful \n Please Sign in now", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(view.INVISIBLE);
                                            signInFragment signInFragment = new signInFragment();
                                            MainActivity.fragmentManager.beginTransaction().replace(R.id.fragmentContainer,signInFragment,null).addToBackStack(null).commit();

                                        }
                                    });



                                } else {
                                    Toast.makeText(getContext(), "Sign Up Failed "+task.getException(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(view.INVISIBLE);
                                }


                            }
                        });
            }
        });


        return view;
    }

}
