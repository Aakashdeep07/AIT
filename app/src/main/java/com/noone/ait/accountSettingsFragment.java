package com.noone.ait;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class accountSettingsFragment extends Fragment {

    ProgressDialog progressDialog;
    Toolbar toolBar;
    private Uri filepath;
    CircleImageView circleImage;
    EditText name, roll, email, mobile;
    Spinner course, sem;
    SharedPreferences sharedPreferences;
    Button editButton;
    DatabaseReference databaseReference;
    private String userId;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    public accountSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        name = view.findViewById(R.id.sname);
        roll = view.findViewById(R.id.userid);
        email = view.findViewById(R.id.Email);
        mobile = view.findViewById(R.id.mobile);
        course = view.findViewById(R.id.branch);
        sem = view.findViewById(R.id.semester);


        roll.setEnabled(false);
        email.setEnabled(false);
        name.setEnabled(false);
        mobile.setEnabled(false);
        sem.setEnabled(false);
        course.setEnabled(false);

        editButton = view.findViewById(R.id.saveButt);

        sharedPreferences = getActivity().getSharedPreferences("com.noone.ait.sharedPrefs",Context.MODE_PRIVATE);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        toolBar = view.findViewById(R.id.toolbar);
        toolBar.setTitle("Welcome " + user.getEmail());
        toolBar.inflateMenu(R.menu.main_menu);

        circleImage = view.findViewById(R.id.editableImage);
        fetchImage();
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name.setEnabled(true);
                mobile.setEnabled(true);
                sem.setEnabled(true);
                course.setEnabled(true);
                editButton.setText("Update Details");
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User information = new User(
                                name.getText().toString().trim(),
                                roll.getText().toString(),
                                email.getText().toString(),
                                mobile.getText().toString().trim(),
                                course.getSelectedItem().toString(),
                                sem.getSelectedItem().toString(),
                                fetchImage()



                        );
                        if (TextUtils.isEmpty(email.getText().toString())) {
                            Toast.makeText(getContext(), "Please check email", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(roll.getText().toString())) {
                            Toast.makeText(getContext(), "Please check Roll number", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(name.getText().toString())) {
                            Toast.makeText(getContext(), "Please check your name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(mobile.getText().toString())) {
                            Toast.makeText(getContext(), "Please Check Mobile Number", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (mobile.getText().toString().length() > 10 || mobile.getText().toString().length() < 10) {
                            Toast.makeText(getContext(), "Mobile number must be of 10 digits", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                editButton.setText("**Updated Successfully**");
                                editButton.setEnabled(false);
                                sem.setEnabled(false);
                                course.setEnabled(false);
                                roll.setEnabled(false);
                                mobile.setEnabled(false);
                                name.setEnabled(false);
                                Toast.makeText(getContext(), "**Updated Successfully**", Toast.LENGTH_SHORT).show();

//                                progressBar.setVisibility(view.INVISIBLE);

                            }
                        });
                    }
                });
            }
        });
        circleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user1 = new User();
                    user1.setStudent_name(dataSnapshot.child(userId).getValue(User.class).getStudent_name());
                    user1.setRoll(dataSnapshot.child(userId).getValue(User.class).getRoll());
                    user1.setEmail(dataSnapshot.child(userId).getValue(User.class).getEmail());
                    user1.setMobile(dataSnapshot.child(userId).getValue(User.class).getMobile());
                    user1.setCourse(dataSnapshot.child(userId).getValue(User.class).getCourse());
                    user1.setSemester(dataSnapshot.child(userId).getValue(User.class).getSemester());
                    user1.setProfile_pic(dataSnapshot.child(userId).getValue(User.class).getProfile_pic());

                    //Fetching Profile Pic
                    name.setText(user1.getStudent_name());
                    roll.setText(user1.getRoll());
                    email.setText(user1.getEmail());
                    mobile.setText(user1.getMobile());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext().getApplicationContext(), "Error Occured :: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.signOut:
                        Toast.makeText(getContext().getApplicationContext(), "Signing Out", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        return true;
                    case R.id.changePassword:
                        Toast.makeText(getContext().getApplicationContext(), "Changing Password ", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });


        return view;
    }

    public String fetchImage() {
        String url = sharedPreferences.getString("profilePicUri", null);
        if (url != null) {
            Glide.with(getContext()).load(url).dontAnimate().into(circleImage);

        }

        return url;
    }
        //        try {
//            ContextWrapper cw = new ContextWrapper(getContext());
//            final File dir = cw.getDir("profileImage", Context.MODE_PRIVATE);
//            final File file = new File(dir, "profile" + ".jpg");
//            profilePicture = BitmapFactory.decodeFile(file.getAbsolutePath());
//            circleImage.setImageBitmap(profilePicture);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            filepath = data.getData();
            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filepath);
                uploadImage();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        if (filepath != null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            final StorageReference reference = storageReference.child("ProfileImages").child(userId + "jpeg");
            reference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            User user = new User();

                            user.setProfile_pic(String.valueOf(uri));
                            //HashMap<String, String> hashMap = new HashMap<>();
                            //hashMap.put("profile_pic", String.valueOf(uri));
                            databaseReference.child(userId).child("profile_pic").setValue(String.valueOf(uri));
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("profilePicUri", String.valueOf(uri));
                            editor.apply();

                            //FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(String.valueOf(uri)).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(getContext(), "Link Saved to FBDB", Toast.LENGTH_SHORT).show();
//                                }
//                            });

                            Glide.with(getContext()).load(String.valueOf(uri)).dontAnimate().into(circleImage);
                        }
                    });

                    Toast.makeText(getContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });

        }
    }
}
