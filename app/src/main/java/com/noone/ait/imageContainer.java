package com.noone.ait;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class imageContainer extends Fragment {
    PhotoView photoView;
    androidx.appcompat.widget.Toolbar toolbar;
    public imageContainer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_container, container, false);
        photoView = view.findViewById(R.id.imageViewFragment);
        toolbar = view.findViewById(R.id.toolbarFragment);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.fragmentManager.beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.profileContainer)).commit();
            }
        });
        fetchImage();
        return view;

    }
    public void fetchImage() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.noone.ait.sharedPrefs",Context.MODE_PRIVATE);
        String url = sharedPreferences.getString("profilePicUri",null);
        if (url!=null){
            Glide.with(getContext()).load(url).dontAnimate().into(photoView);
        }
        else{
            Toast.makeText(getContext(), "Please upload a Profile Picture", Toast.LENGTH_SHORT).show();
        }

//        try {
//            ContextWrapper cw = new ContextWrapper(getContext());
//            final File dir = cw.getDir("profileImage", Context.MODE_PRIVATE);
//            final File file = new File(dir,"profile"+".jpg");
//            Glide.with(getContext()).load(file).transition(DrawableTransitionOptions.withCrossFade()).into(photoView);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
