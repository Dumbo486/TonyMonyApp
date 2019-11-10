package layout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.choisangah.realtonymony.R;
import com.example.choisangah.realtonymony.RGBtoHSV;

import static android.content.ContentValues.TAG;

/**
 * Created by choisangah on 2017. 6. 13..
 */

public class TonyTest2Fragment extends Fragment  {
    TonyTestFragment tonyTestFragment;
    ImageView testImgView2,goRight2;
    Button hair,eye,cheek;
    String imgPath;
    int r,g,b,type;
    Bitmap bm;
    RGBtoHSV hairvalue,eyevalue,cheekvalue;
    static int cool =0 ;
    static String tone="";


    public static TonyTest2Fragment newInstance(String param1, String param2){
        TonyTest2Fragment fragment = new TonyTest2Fragment();
        Bundle args = new Bundle(2);
        args.putString("result",param1);
        args.putString("path",param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        imgPath = arg.getString("path");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ttest2,container,false);

        testImgView2 = (ImageView)(view).findViewById(R.id.testImgView2);
        hair = (Button)(view).findViewById(R.id.hairBtn);
        eye = (Button)(view).findViewById(R.id.eyeBtn);
        cheek = (Button)(view).findViewById(R.id.cheekBtn);
        goRight2 = (ImageView)(view).findViewById(R.id.goRight2);

        initImgView();
        hairclick();
        eyeclick();
        cheekclick();

        goRight2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveFinalStepAction();
            }
        });




        return view;
    }

    public void initImgView(){

        if (imgPath == null)
            return;

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250,
                getResources().getDisplayMetrics());

        int height = width;

        Bitmap bm = BitmapFactory.decodeFile(imgPath);
//        bm = BitmapFactory.decodeResource(getResources(),R.drawable.sample);
        this.bm = Bitmap.createScaledBitmap(bm, width, height, false);
        testImgView2.setImageBitmap(this.bm);
    }

    public void hairclick(){
        hair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testImgView2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        float x = event.getX();
                        float y =  event.getY();
                        Log.d(TAG, "x: " + x + ", y: " + y);
                        Log.d(TAG, "width: " + bm.getWidth() + ", height: " + bm.getHeight());
                        Log.d(TAG, "i width: " + testImgView2.getWidth() + ", i height: " + testImgView2.getHeight());
                        x = Math.max(x, 0);
                        y = Math.max(y, 0);
                        x = Math.min(x, testImgView2.getWidth() - 1);
                        y = Math.min(y, testImgView2.getHeight() - 1);

                        Log.d(TAG, "x: " + x + ", y: " + y);
                        int colour = bm.getPixel((int)x,(int)y);
                        r = Color.red(colour);
                        g = Color.green(colour);
                        b = Color.blue(colour);
                        Toast.makeText(getContext(),r+"/"+g+"/"+b,Toast.LENGTH_SHORT).show();
                        hairvalue = new RGBtoHSV(r,g,b);

                        if(hairvalue.getV()<20 || (hairvalue.getV()<50 && (hairvalue.getR()==0 || hairvalue.getG()==0))
                                || (hairvalue.getV()<50 && hairvalue.getB()>100)) cool++;
                        return true;
                    }
                });
            }
        });
    }

    public void eyeclick(){
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testImgView2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        float x = event.getX();
                        float y =  event.getY();
                        Log.d(TAG, "x: " + x + ", y: " + y);
                        Log.d(TAG, "width: " + bm.getWidth() + ", height: " + bm.getHeight());
                        Log.d(TAG, "i width: " + testImgView2.getWidth() + ", i height: " + testImgView2.getHeight());
                        x = Math.max(x, 0);
                        y = Math.max(y, 0);
                        x = Math.min(x, testImgView2.getWidth() - 1);
                        y = Math.min(y, testImgView2.getHeight() - 1);

                        Log.d(TAG, "x: " + x + ", y: " + y);
                        int colour = bm.getPixel((int)x,(int)y);
                        r = Color.red(colour);
                        g = Color.green(colour);
                        b = Color.blue(colour);
                        eyevalue = new RGBtoHSV(r,g,b);
                        Toast.makeText(getContext(),r+"/"+g+"/"+b,Toast.LENGTH_SHORT).show();
                        if(eyevalue.getV()<20 || (eyevalue.getV()<50 && (eyevalue.getR()==0 || eyevalue.getG()==0))
                                || (eyevalue.getV()<50 && eyevalue.getB()>100)) cool++;

                        return true;
                    }
                });
            }
        });
    }

    public void cheekclick(){
        cheek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testImgView2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        float x = event.getX();
                        float y =  event.getY();
                        Log.d(TAG, "x: " + x + ", y: " + y);
                        Log.d(TAG, "width: " + bm.getWidth() + ", height: " + bm.getHeight());
                        Log.d(TAG, "i width: " + testImgView2.getWidth() + ", i height: " + testImgView2.getHeight());
                        x = Math.max(x, 0);
                        y = Math.max(y, 0);
                        x = Math.min(x, testImgView2.getWidth() - 1);
                        y = Math.min(y, testImgView2.getHeight() - 1);

                        Log.d(TAG, "x: " + x + ", y: " + y);
                        int colour = bm.getPixel((int)x,(int)y);
                        r = Color.red(colour);
                        g = Color.green(colour);
                        b = Color.blue(colour);
                        cheekvalue = new RGBtoHSV(r,g,b);
                        Toast.makeText(getContext(),r+"/"+g+"/"+b,Toast.LENGTH_SHORT).show();
                        if(cheekvalue.getH()<33 || cheekvalue.getH()>300) cool++;

                        if(cool>=2){
                            if(cheekvalue.getB()>=235) tone = "Winter Cool";
                            else tone = "Summer Cool";
                        }
                        else{
                            if(cheekvalue.getG()-cheekvalue.getB()>20) tone="Spring Warm";
                            else tone = "Autumn Warm";
                        }


                        return true;
                    }
                });
            }
        });
    }


//    public String getTone(){
//
////        int cool=0;
//        boolean iscool=false;
//        String tone=null;
////
////
////        //웜톤 쿨톤 기준 검사 중
////        if(hairvalue.getV()<20 || (hairvalue.getV()<50 && (hairvalue.getR()==0 || hairvalue.getG()==0))
////                || (hairvalue.getV()<50 && hairvalue.getB()>100)) cool++;
////        else if(eyevalue.getV()<20 || (eyevalue.getV()<50 && (eyevalue.getR()==0 || eyevalue.getG()==0))
////                || (eyevalue.getV()<50 && eyevalue.getB()>100)) cool++;
////        else if(cheekvalue.getH()<33 || cheekvalue.getH()>300) cool++;
//
//        //웜톤 쿨톤 뭐가 더 많은지
//        if(cool>=2) iscool = true;
//
//        //계절까지 총 톤 타입
//        if(iscool){
//            if(cheekvalue.getB()>=235) tone = "Winter Cool";
//            else tone = "Summer Cool";
//        }
//        else{
//            if(cheekvalue.getG()-cheekvalue.getB()>20) tone="Spring Warm";
//            else tone = "Autumn Warm";
//        }
//
//        return tone;
//    }

    public void moveFinalStepAction(){

        Bundle args = new Bundle();
        args.putString("result",tone);
        args.putString("path",imgPath);
        TonyTest3Fragment tonyTest3Fragment = new TonyTest3Fragment ();
        tonyTest3Fragment.setArguments(args);



        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.container, tonyTest2Fragment);
        fragmentTransaction.replace(R.id.container,tonyTest3Fragment,"result");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
