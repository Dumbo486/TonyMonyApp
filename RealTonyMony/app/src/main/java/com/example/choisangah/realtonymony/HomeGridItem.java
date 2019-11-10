package com.example.choisangah.realtonymony;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by choisangah on 2017. 6. 13..
 */

public class HomeGridItem extends LinearLayout {
    ImageView iv;
    TextView tv1,tv2;

    public HomeGridItem(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.griditemhome,this);

        iv = (ImageView)findViewById(R.id.homegridImg);
        tv1 = (TextView)findViewById(R.id.homegridTv1);
        tv2 = (TextView)findViewById(R.id.homegridTv2);

    }


}
