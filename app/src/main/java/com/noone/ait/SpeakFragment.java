package com.noone.ait;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpeakFragment extends Fragment {

    ImageButton speakButton;
    Button regButton;
    TextView titleTxt,messageTxt;
    String userId,userEmail;
    String context = "";
    String problem = "";
    public SpeakFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_speak, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        speakButton = view.findViewById(R.id.micClick);
        messageTxt = view.findViewById(R.id.messagetext);
        titleTxt = view.findViewById(R.id.titletext);
        regButton= view.findViewById(R.id.registerButton);
        regButton.setVisibility(View.INVISIBLE);
        userId = FirebaseAuth.getInstance().getUid();
        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                startActivityForResult(intent, 10);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                complaintHandler complainInfo = new complaintHandler(
                        userId,
                        userEmail,
                        context,
                        problem

                );
                FirebaseDatabase.getInstance().getReference("Complaint").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(complainInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                titleTxt.setText("Your Complaint has been successfully registered.");
                messageTxt.setText("Thank You.");
                        Toast.makeText(getContext(), "Complaint registered", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });




        return  view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 10:
                    String found = getOutputFromResults(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS));
                    if (!found.isEmpty()) {
                        problem = found;
                        regButton.setEnabled(true);
                        context = findContext(problem);
                        titleTxt.setText("Click on Register Complaint button to register your complaint");
                        messageTxt.setText("You Said:\n"+problem+"\nSubject:\n"+context);
                        regButton.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        } else {
            Toast.makeText(getContext().getApplicationContext(), "Failed to recognize Speech:: Speak Again ", Toast.LENGTH_SHORT).show();
        }
    }

    private String getOutputFromResults(ArrayList<String> results) {
//        String find = String.join(",", results);

        for (String str : results) {
            if (!getStringFromText(str).isEmpty()) {

                return getStringFromText(str);
            }
        }
        return "Speak Again";
    }

    private String getStringFromText(String str) {
        return str;
    }





    private String findContext(String problem) {
        if (problem.contains("washroom") || problem.contains("toilet") || problem.contains("bathroom")) {
            if (problem.contains("dirty") || problem.contains("stinky")) {
                context = "sanitation department";
            } else if (problem.contains("light")) {
                context = "electricity department";
            } else if (problem.contains("leakage")) {
                context = "services department";
            } else if (problem.contains("water")) {
                context = "water department";
            } else {
                context = "complaint";
            }
        } else if (problem.contains("parking") || problem.contains("bike") || problem.contains("car") || problem.contains("cycle")) {
            context = "parking department";
            if (problem.contains("stolen")) {
                context = "theft department";
            }
        } else if (problem.contains("water") || problem.contains("drinking water") || problem.contains("potable") || problem.contains("portable") || problem.contains("drinkable")) {
            if (problem.contains("drinking")) {
                context = "water department";
            }
            if (problem.contains("unhealthy")) {
                context = "water department";
            }
            if (problem.contains("waterlog")) {
                context = "sanitation department";
            } else {
                context = "water department";
            }
        } else if (problem.contains("electricity") || problem.contains("power")) {
            if (problem.contains("no")) {
                context = "electricity department";
            }
            if (problem.contains("issue") || problem.contains("problem") || problem.contains("supply")) {
                context = "electricity department";
            }
        } else if (problem.contains("not working")) {
            if (problem.contains("fan")) {
                context = "services department";
            }
            if (problem.contains("light")) {
                context = "services department";
            }
            if (problem.contains("computer")) {
                context = "IT services department";
            }
            if (problem.contains("AC")) {
                context = "services department";
            } else if (problem.contains("no")) {
                if (problem.contains("power") || problem.contains("light") || problem.contains("lite")) {
                    context = "electricity department";
                }
                if (problem.contains("water")) {
                    context = "water department";
                }
                if (problem.contains("teacher") || problem.contains("faculty")) {
                    context = "administration department";
                }

            }
        } else if (problem.contains("clean") || problem.contains("dirty")) {
            context = "sanitation department";
            if (problem.contains("class")) {
                context = "sanitation department";
            }
            if (problem.contains("college")) {
                context = "sanitation department";
            }
        } else if (problem.contains("dog")) {
            context = "animal control department";
        } else {
            context = "complaint";
        }

        return context;
    }
}
