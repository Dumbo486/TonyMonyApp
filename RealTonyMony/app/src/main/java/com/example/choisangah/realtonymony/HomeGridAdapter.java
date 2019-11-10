package com.example.choisangah.realtonymony;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by choisangah on 2017. 6. 13..
 */

public class HomeGridAdapter extends BaseAdapter {
    Context context;
    ArrayList<Skin> skins = new ArrayList<>();

    public HomeGridAdapter(Context context,ArrayList<Skin> skins){
        this.context = context;
        this.skins = skins;
    }

    @Override
    public int getCount() {
        return skins.size();
    }

    @Override
    public Object getItem(int position) {
        return skins.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.griditemhome,null);

        final ImageView iv = (ImageView)convertView.findViewById(R.id.homegridImg);
        final TextView tv1 = (TextView)convertView.findViewById(R.id.homegridTv1);
        final TextView tv2 = (TextView)convertView.findViewById(R.id.homegridTv2);


        tv1.setText(skins.get(position).season);
        tv2.setText(skins.get(position).feeling);
        iv.setImageResource(skins.get(position).imgno);
        return convertView;
    }
}
