package com.noone.ait;


import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.Authenticator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class downloadsFragment extends Fragment
{
    private static boolean PERMISSION_ALLOWED = false;
    ExpandableListView expandableListView;
    ExpandableAdapterForDownloads expandableListAdapter;
    List<String> content;
    Map<String, List<String>> subContent;
    private static final int WRITE_PERMISSION = 1001;
    public downloadsFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_downloads, container, false);
        fillData();
        expandableListView = view.findViewById(R.id.expandableDownloads);
        expandableListAdapter = new ExpandableAdapterForDownloads(getContext(), content, subContent);
        expandableListView.setAdapter(expandableListAdapter);

            if (checkPermissions()==false){
                Toast.makeText(getContext(), "Please allow Permission ", Toast.LENGTH_SHORT).show();
                checkPermissions();
            }
            else {

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    switch (groupPosition) {
                        case 0:
                            switch (childPosition) {
                                case 0:
                                    String filename = content.get(groupPosition) + subContent.get(content.get(groupPosition)).get(childPosition);
                                    downloadFile(filename, Constants.bca_syllabus);
                                    break;
                                case 1:
                                    Toast.makeText(getContext(), " Downloading BCA Time Table", Toast.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    Toast.makeText(getContext(), " Downloading BCA Magazine", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            break;

                        case 1:
                            switch (childPosition) {
                                case 0:
                                    String filename = content.get(groupPosition) + subContent.get(content.get(groupPosition)).get(childPosition);
                                    downloadFile(filename, Constants.bvoc_syllabus);
                                    break;
                                case 1:
                                    Toast.makeText(getContext(), " Downloading BVOC Time Table", Toast.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    Toast.makeText(getContext(), " Downloading BVOC Magazine", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            break;

                        case 2:
                            switch (childPosition) {
                                case 0:
                                    String filename = content.get(groupPosition) + subContent.get(content.get(groupPosition)).get(childPosition);
                                    downloadFile(filename, Constants.diploma_syllabus);
                                    break;
                                case 1:
                                    Toast.makeText(getContext(), " Downloading Diploma Table", Toast.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    Toast.makeText(getContext(), " Downloading Diploma Magazine", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            break;


                    }
//                Toast.makeText(getContext(),content.get(groupPosition)+subContent.get(content.get(groupPosition)).get(childPosition)+" ....", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });


        }
        return view;
    }

    private boolean checkPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
               return true;
            }else{
                Toast.makeText(getContext(), "Please allow Storage Permission", Toast.LENGTH_SHORT).show();
                getActivity().requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
                if(PERMISSION_ALLOWED==true){
                    Toast.makeText(getContext(), "Permission allowed", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else{

                    Toast.makeText(getContext(), "Restart the app and allow Storage Permission", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
    return false;
    }

    private void downloadFile(String filename, String url) {
        Uri downloadURI = Uri.parse(url);
        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            if (downloadManager!=null){
                DownloadManager.Request request = new DownloadManager.Request(downloadURI);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                            .setTitle(filename)
                            .setDescription("Downloading "+filename)
                            .setAllowedOverMetered(true)
                            .setAllowedOverRoaming(true)
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
                            .setMimeType(getMimeType(downloadURI));
                }
                downloadManager.enqueue(request);
                Toast.makeText(getContext(), "Download Started", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(Intent.ACTION_VIEW, downloadURI);
                startActivity(intent);
            }
        }
        catch (Exception e){
            Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    PERMISSION_ALLOWED = true;
            }
            else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                PERMISSION_ALLOWED = false;
            }
        }
    }
    private String getMimeType(Uri uri){
        ContentResolver resolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(resolver.getType(uri));
    }
public void fillData(){
        content = new ArrayList<>();
        subContent = new HashMap<>();

        content.add("BCA");
        content.add("B.VOC");
        content.add("Diploma");

        List<String> bca = new ArrayList<>();
        List<String> bvoc = new ArrayList<>();
        List<String> diploma = new ArrayList<>();

        bca.add("Syllabus");
        bca.add("Time Table");
        bca.add("Magazine");

        bvoc.add("Syllabus");
        bvoc.add("Time Table");
        bvoc.add("Magazine");

        diploma.add("Syllabus");
        diploma.add("Time Table");
        diploma.add("Magazine");

        subContent.put(content.get(0), bca);
        subContent.put(content.get(1), bvoc);
        subContent.put(content.get(2), diploma);



}

}
