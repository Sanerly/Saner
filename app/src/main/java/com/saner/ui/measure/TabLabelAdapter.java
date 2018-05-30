package com.saner.ui.measure;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.saner.R;
import com.saner.util.LogUtil;

import java.util.List;

/**
 * Created by sunset on 2018/5/29.
 */

public class TabLabelAdapter extends BaseAdapter {

    private List<String> stringList;
private  Context context;

    public TabLabelAdapter(List<String> stringList, Context context) {
        this.stringList = stringList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_label, null);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(stringList.get(position));
//        LogUtil.logd(stringList.get(position));
        return convertView;
    }


    static class ViewHolder {
        TextView textView;
    }
}
