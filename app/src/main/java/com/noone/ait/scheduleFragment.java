package com.noone.ait;


import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class scheduleFragment extends Fragment {
    GridView gridView;
    DatabaseReference databaseReference;
    public scheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gridView = view.findViewById(R.id.gridViewSchedule);
        generateSchedule();

        return view;
    }
    public void generateSchedule() {
        final String timeSlots[] = {"DAY", "08:30-09:30", "09:30-10:30", "10:30-11:30","11:30-12:30", "01:00-02:00", "02:00-03:00", "03:00-04:00", "04:00-05:00"};
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Timetable");
        final scheduleClass scheduleClass = new scheduleClass();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {


                        scheduleClass.setZero(dataSnapshot.child("Monday").getValue(scheduleClass.class).getZero());
                        scheduleClass.setOne(dataSnapshot.child("Monday").getValue(scheduleClass.class).getOne());
                        scheduleClass.setTwo(dataSnapshot.child("Monday").getValue(scheduleClass.class).getTwo());
                        scheduleClass.setThree(dataSnapshot.child("Monday").getValue(scheduleClass.class).getThree());
                        scheduleClass.setFour(dataSnapshot.child("Monday").getValue(scheduleClass.class).getFour());
                        scheduleClass.setFive(dataSnapshot.child("Monday").getValue(scheduleClass.class).getFive());
                        scheduleClass.setSix(dataSnapshot.child("Monday").getValue(scheduleClass.class).getSix());
                        scheduleClass.setSeven(dataSnapshot.child("Monday").getValue(scheduleClass.class).getSeven());
                        scheduleClass.setEight(dataSnapshot.child("Monday").getValue(scheduleClass.class).getEight());


                        String mondaySlots[] = {
                                scheduleClass.getZero(),
                                scheduleClass.getOne(),
                                scheduleClass.getTwo(),
                                scheduleClass.getThree(),
                                scheduleClass.getFour(),
                                scheduleClass.getFive(),
                                scheduleClass.getSix(),
                                scheduleClass.getSeven(),
                                scheduleClass.getEight()

                        };


                        scheduleClass.setZero(dataSnapshot.child("Tuesday").getValue(scheduleClass.class).getZero());
                        scheduleClass.setOne(dataSnapshot.child("Tuesday").getValue(scheduleClass.class).getOne());
                        scheduleClass.setTwo(dataSnapshot.child("Tuesday").getValue(scheduleClass.class).getTwo());
                        scheduleClass.setThree(dataSnapshot.child("Tuesday").getValue(scheduleClass.class).getThree());
                        scheduleClass.setFour(dataSnapshot.child("Tuesday").getValue(scheduleClass.class).getFour());
                        scheduleClass.setFive(dataSnapshot.child("Tuesday").getValue(scheduleClass.class).getFive());
                        scheduleClass.setSix(dataSnapshot.child("Tuesday").getValue(scheduleClass.class).getSix());
                        scheduleClass.setSeven(dataSnapshot.child("Tuesday").getValue(scheduleClass.class).getSeven());
                        scheduleClass.setEight(dataSnapshot.child("Tuesday").getValue(scheduleClass.class).getEight());

                        String tuesdaySlots[] = {
                                scheduleClass.getZero(),
                                scheduleClass.getOne(),
                                scheduleClass.getTwo(),
                                scheduleClass.getThree(),
                                scheduleClass.getFour(),
                                scheduleClass.getFive(),
                                scheduleClass.getSix(),
                                scheduleClass.getSeven(),
                                scheduleClass.getEight()

                        };

                        scheduleClass.setZero(dataSnapshot.child("Wednesday").getValue(scheduleClass.class).getZero());
                        scheduleClass.setOne(dataSnapshot.child("Wednesday").getValue(scheduleClass.class).getOne());
                        scheduleClass.setTwo(dataSnapshot.child("Wednesday").getValue(scheduleClass.class).getTwo());
                        scheduleClass.setThree(dataSnapshot.child("Wednesday").getValue(scheduleClass.class).getThree());
                        scheduleClass.setFour(dataSnapshot.child("Wednesday").getValue(scheduleClass.class).getFour());
                        scheduleClass.setFive(dataSnapshot.child("Wednesday").getValue(scheduleClass.class).getFive());
                        scheduleClass.setSix(dataSnapshot.child("Wednesday").getValue(scheduleClass.class).getSix());
                        scheduleClass.setSeven(dataSnapshot.child("Wednesday").getValue(scheduleClass.class).getSeven());
                        scheduleClass.setEight(dataSnapshot.child("Wednesday").getValue(scheduleClass.class).getEight());

                        String wednesdaySlots[] = {
                                scheduleClass.getZero(),
                                scheduleClass.getOne(),
                                scheduleClass.getTwo(),
                                scheduleClass.getThree(),
                                scheduleClass.getFour(),
                                scheduleClass.getFive(),
                                scheduleClass.getSix(),
                                scheduleClass.getSeven(),
                                scheduleClass.getEight()

                        };
                        scheduleClass.setZero(dataSnapshot.child("Thursday").getValue(scheduleClass.class).getZero());
                        scheduleClass.setOne(dataSnapshot.child("Thursday").getValue(scheduleClass.class).getOne());
                        scheduleClass.setTwo(dataSnapshot.child("Thursday").getValue(scheduleClass.class).getTwo());
                        scheduleClass.setThree(dataSnapshot.child("Thursday").getValue(scheduleClass.class).getThree());
                        scheduleClass.setFour(dataSnapshot.child("Thursday").getValue(scheduleClass.class).getFour());
                        scheduleClass.setFive(dataSnapshot.child("Thursday").getValue(scheduleClass.class).getFive());
                        scheduleClass.setSix(dataSnapshot.child("Thursday").getValue(scheduleClass.class).getSix());
                        scheduleClass.setSeven(dataSnapshot.child("Thursday").getValue(scheduleClass.class).getSeven());
                        scheduleClass.setEight(dataSnapshot.child("Thursday").getValue(scheduleClass.class).getEight());

                        String thursdaySlots[] = {
                                scheduleClass.getZero(),
                                scheduleClass.getOne(),
                                scheduleClass.getTwo(),
                                scheduleClass.getThree(),
                                scheduleClass.getFour(),
                                scheduleClass.getFive(),
                                scheduleClass.getSix(),
                                scheduleClass.getSeven(),
                                scheduleClass.getEight()

                        };


                        scheduleClass.setZero(dataSnapshot.child("Friday").getValue(scheduleClass.class).getZero());
                        scheduleClass.setOne(dataSnapshot.child("Friday").getValue(scheduleClass.class).getOne());
                        scheduleClass.setTwo(dataSnapshot.child("Friday").getValue(scheduleClass.class).getTwo());
                        scheduleClass.setThree(dataSnapshot.child("Friday").getValue(scheduleClass.class).getThree());
                        scheduleClass.setFour(dataSnapshot.child("Friday").getValue(scheduleClass.class).getFour());
                        scheduleClass.setFive(dataSnapshot.child("Friday").getValue(scheduleClass.class).getFive());
                        scheduleClass.setSix(dataSnapshot.child("Friday").getValue(scheduleClass.class).getSix());
                        scheduleClass.setSeven(dataSnapshot.child("Friday").getValue(scheduleClass.class).getSeven());
                        scheduleClass.setEight(dataSnapshot.child("Friday").getValue(scheduleClass.class).getEight());

                        String fridaySlots[] = {
                                scheduleClass.getZero(),
                                scheduleClass.getOne(),
                                scheduleClass.getTwo(),
                                scheduleClass.getThree(),
                                scheduleClass.getFour(),
                                scheduleClass.getFive(),
                                scheduleClass.getSix(),
                                scheduleClass.getSeven(),
                                scheduleClass.getEight()

                        };


                        scheduleAdapter scheduleAdapter = new scheduleAdapter(getContext().getApplicationContext(), timeSlots, mondaySlots, tuesdaySlots, wednesdaySlots, thursdaySlots, fridaySlots);
                        gridView.setAdapter(scheduleAdapter);

                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });


    }
}