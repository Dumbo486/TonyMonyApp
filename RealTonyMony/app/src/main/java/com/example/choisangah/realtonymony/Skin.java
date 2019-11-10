package com.example.choisangah.realtonymony;

/**
 * Created by choisangah on 2017. 6. 13..
 */

public class Skin {
    String season,feeling;
    int imgno;
    final static int imglist[] = {R.drawable.springwarm,R.drawable.summercool,R.drawable.autumnwarm,R.drawable.wintercool};

    public Skin(String season, String feeling,int imgno){
        this.season = season;
        this.feeling = feeling;
        this.imgno = imgno;
    }


}
