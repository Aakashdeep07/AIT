package com.noone.ait;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;

public class scheduleAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] timeSlots;
    private final String[] monday;
    private final String[] tuesday;
    private final String[] wednesday;
    private final String[] thursday;
    private final String[] friday;




    public scheduleAdapter(Context mContext, String[] subject, String[] monday, String[] tuesday, String[] wednesday, String[] thursday, String[] friday) {
        this.mContext = mContext;
        this.timeSlots = subject;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }

    @Override
    public int getCount() {
        return timeSlots.length;
    }

    @Override
    public Object getItem(int position) {
        return timeSlots[position];
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
            grid = inflater.inflate(R.layout.grid_10x6,null);
            Button textView = grid.findViewById(R.id.gridTitle);
            Button monDay = grid.findViewById(R.id.gridMonday);
            Button tuesDay = grid.findViewById(R.id.gridTuesday);
            Button wednesDay = grid.findViewById(R.id.gridWednesday);
            Button thursDay = grid.findViewById(R.id.gridThursday);
            Button friDay = grid.findViewById(R.id.gridFriday);
            textView.setText(timeSlots[position]);
            monDay.setText(monday[position]);
            tuesDay.setText(tuesday[position]);
            wednesDay.setText(wednesday[position]);
            thursDay.setText(thursday[position]);
            friDay.setText(friday[position]);
            //imageView.setImageResource(subject[position]);
        }
        else {
            grid = (View) convertView;
        }

        return grid;
    }
}
//