package com.noone.ait;

import androidx.annotation.NonNull;


import androidx.appcompat.app.AppCompatActivity;


import androidx.fragment.app.FragmentManager;

import android.app.DownloadManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView tvUsername;
    GridView gridView;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    BottomNavigationView bottNavigationView;
    CircleImageView circleImageView;
    Bitmap profilePicture;
    private String userId;

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvUsername = findViewById(R.id.username);
        circleImageView = findViewById(R.id.profile_image);
        String title[] = {"School", "Schedule", "Downloads", "Information"};
        final int[] thumbsId = {
                R.drawable.ic_school_black_24dp,
                R.drawable.ic_schedule_black_24dp,
                R.drawable.ic_file_download_black_24dp,
                R.drawable.ic_info_outline_black_24dp
        };

        gridView = findViewById(R.id.gridView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        userId = firebaseUser.getUid();

        fragmentManager = getSupportFragmentManager();
        bottNavigationView = findViewById(R.id.bottom_nav);
        setBottomNavBar();
        ImageAdapter adapter = new ImageAdapter(getApplicationContext(), title, thumbsId);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(ProfileActivity.this, "School", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        showSchedule();
                        break;
                        case 2:
                        downloads();
                        break;
                        case 3:
                        Toast.makeText(ProfileActivity.this, "Information", Toast.LENGTH_SHORT).show();
                        break;
                        default:
                            Toast.makeText(ProfileActivity.this, "Else", Toast.LENGTH_SHORT).show();
                            break;
                }
            }
        });
        //SharedPreferences sharedPreferences = getSharedPreferences("com.noone.ait.sharedPrefs",Context.MODE_PRIVATE);
        //String profilePicUrl = sharedPreferences.getString("profilePicUri",null);
        //if (profilePicUrl!=null){
        //  Glide.with(getApplicationContext()).load(profilePicUrl).dontAnimate().into(circleImageView);
        //}
        //fetchImage();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user1 = new User();
                    user1.setStudent_name(dataSnapshot.child(userId).getValue(User.class).getStudent_name());
                    user1.setProfile_pic(dataSnapshot.child(userId).getValue(User.class).getProfile_pic());
                    tvUsername.setText(user1.getStudent_name());
                    SharedPreferences sharedPreferences = getSharedPreferences("com.noone.ait.sharedPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profilePicUri", user1.getProfile_pic());
                    editor.apply();
                    if (user1.getProfile_pic() != null) {
                        Glide.with(getApplicationContext()).load(user1.getProfile_pic()).dontAnimate().into(circleImageView);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Please upload a Profile Picture", Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                tvUsername.setText(databaseError.getMessage());

            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                imageContainer imageContainer = new imageContainer();
                ProfileActivity.fragmentManager.beginTransaction().replace(R.id.profileContainer, imageContainer, null).commit();
            }
        });
    }

    private void showSchedule() {
        scheduleFragment scheduleFragment = new scheduleFragment();
        ProfileActivity.fragmentManager.beginTransaction().replace(R.id.profileContainer, scheduleFragment, null).commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }

    public Bitmap fetchImage() {
        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            final File dir = cw.getDir("profileImage", Context.MODE_PRIVATE);
            final File file = new File(dir, "profile.jpg");
            StorageReference reference = storageReference.child("ProfileImages").child(userId + "jpeg");
            reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    profilePicture = BitmapFactory.decodeFile(file.getAbsolutePath());

                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file);
                        profilePicture.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    circleImageView.setImageBitmap(profilePicture);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this, "Failed to load image" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return profilePicture;
    }

    public void downloads(){
        downloadsFragment downloadsFragment = new downloadsFragment();
        ProfileActivity.fragmentManager.beginTransaction().replace(R.id.profileContainer, downloadsFragment, null).commit();
    }
    public void setBottomNavBar() {
        bottNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.ic_home:
                        if (getSupportFragmentManager().findFragmentById(R.id.profileContainer) != null) {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            ProfileActivity.fragmentManager.beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.profileContainer)).commit();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.ic_speak:
                        SpeakFragment speakFragment = new SpeakFragment();
                        ProfileActivity.fragmentManager.beginTransaction().replace(R.id.profileContainer, speakFragment, null).commit();
                        break;
//                    case R.id.ic_notification:
//                        Intent i4 = new Intent(mContext, NotificationActivity.class);
//                        mContext.startActivity(i4);
//                        break;
                    case R.id.ic_account:


                        accountSettingsFragment accountSettingsFragment = new accountSettingsFragment();
                        ProfileActivity.fragmentManager.beginTransaction().replace(R.id.profileContainer, accountSettingsFragment, null).commit();


                        break;
                }
                return true;
            }
        });
    }
}
