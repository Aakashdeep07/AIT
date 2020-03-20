package com.noone.ait;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableAdapterForDownloads extends BaseExpandableListAdapter {
    Context context;
    List<String> content;
    Map<String, List<String>> subContent;

    public ExpandableAdapterForDownloads(Context context, List<String> content, Map<String, List<String>> subContent) {
        this.context = context;
        this.content = content;
        this.subContent = subContent;
    }

    @Override
    public int getGroupCount() {
        return content.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return subContent.get(content.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return content.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
     return subContent.get(content.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String content = (String) getGroup(groupPosition);

        if (convertView == null){
          LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
          convertView = layoutInflater.inflate(R.layout.download_parent, null);
        }
        TextView textParent = (TextView) convertView.findViewById(R.id.downloadParent);
        textParent.setText(content);
    return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String subContent = (String) getChild(groupPosition, childPosition);

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.download_child, null);
        }
        TextView textChild = (TextView) convertView.findViewById(R.id.downloadChild);
        textChild.setText(subContent);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
