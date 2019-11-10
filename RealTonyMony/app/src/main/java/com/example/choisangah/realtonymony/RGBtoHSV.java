package com.example.choisangah.realtonymony;

import android.graphics.Color;

/**
 * Created by choisangah on 2017. 6. 13..
 */

public class RGBtoHSV {
    int r,g,b;
    float h,s,v;
    float[] hsv = new float[3];


    public RGBtoHSV(int r,int g,int b){
        this.r = r;
        this.g = g;
        this.b = b;
        Color.RGBToHSV(r,g,b,this.hsv);
        this.h = this.hsv[0];
        this.s = this.hsv[1];
        this.v = this.hsv[2];

    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getS() {
        return s;
    }

    public void setS(float s) {
        this.s = s;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }

    public float[] getHsv() {
        return hsv;
    }

    public void setHsv(float[] hsv) {
        this.hsv = hsv;
    }
}
