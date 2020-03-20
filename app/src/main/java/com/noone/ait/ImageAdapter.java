package com.noone.ait;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] thumbsId;


    public ImageAdapter(Context mContext, String[] web, int[] thumbsId) {
        this.mContext = mContext;
        this.web = web;
        this.thumbsId = thumbsId;
    }

    @Override
    public int getCount() {
    return thumbsId.length;
    }

    @Override
    public Object getItem(int position) {
        return thumbsId[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_2x2,null);
            TextView textView = grid.findViewById(R.id.gridTitle);
            ImageView imageView = grid.findViewById(R.id.gridImage);
            textView.setText(web[position]);
            imageView.setImageResource(thumbsId[position]);
        }
        else {
            grid = (View) convertView;
        }
//        ImageView imageView = new ImageView(mContext);
//        imageView.setImageResource(thumbsId[position]);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setLayoutParams(new GridView.LayoutParams(300,300));
//        return imageView;
        return grid;
    }
}
