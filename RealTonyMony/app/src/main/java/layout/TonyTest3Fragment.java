package layout;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.choisangah.realtonymony.DbOpenHelper;
import com.example.choisangah.realtonymony.HomeActivity;
import com.example.choisangah.realtonymony.R;

/**
 * Created by choisangah on 2017. 6. 13..
 */

public class TonyTest3Fragment extends Fragment {
    UserPlatteFragment userPlatteFragment;
    DbOpenHelper mDbOpenHelper;
    Cursor mCursor;


    static String result,imgPath;
//    String sex,strName;
    TextView resultTv;
    ImageView springIv,summerIv,autumnIv,winterIv;
    Bitmap bm1,bm2,bm3,bm4;
    Button goResult;

    public static TonyTest3Fragment newInstance(String param1,String param2){
        TonyTest3Fragment fragment = new TonyTest3Fragment();
        Bundle args = new Bundle();
        args.putString("userName",param1);
        args.putString("path",param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        result = arg.getString("result");
        imgPath = arg.getString("path");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ttest3,container,false);
        mDbOpenHelper = new DbOpenHelper(this.getContext());
        mDbOpenHelper.open();

        resultTv = (TextView)(view).findViewById(R.id.resultTv);
        springIv = (ImageView)(view).findViewById(R.id.springIv);
        summerIv = (ImageView)(view).findViewById(R.id.summerIv);
        autumnIv = (ImageView)(view).findViewById(R.id.autumnIv);
        winterIv = (ImageView)(view).findViewById(R.id.winterIv);
        goResult = (Button)(view).findViewById(R.id.goResult);

        initImgViews();

        if(result.equals("Spring Warm")){
            resultTv.setText("당신의 퍼스널컬러는 '봄웜톤' 입니다");
            summerIv.setImageBitmap(grayScale(bm2));
            autumnIv.setImageBitmap(grayScale(bm3));
            winterIv.setImageBitmap(grayScale(bm4));
        }
        else if(result.equals("Summer Cool")){
            resultTv.setText("당신의 퍼스널컬러는 '여름쿨톤' 입니다");
            springIv.setImageBitmap(grayScale(bm1));
            autumnIv.setImageBitmap(grayScale(bm3));
            winterIv.setImageBitmap(grayScale(bm4));
        }
        else if(result.equals("Autumn Warm")){
            resultTv.setText("당신의 퍼스널컬러는 '가을웜톤' 입니다");
            summerIv.setImageBitmap(grayScale(bm2));
            springIv.setImageBitmap(grayScale(bm1));
            winterIv.setImageBitmap(grayScale(bm4));
        }
        else if(result.equals("Winter Cool")){
            resultTv.setText("당신의 퍼스널컬러는 '겨울쿨톤' 입니다");
            summerIv.setImageBitmap(grayScale(bm2));
            autumnIv.setImageBitmap(grayScale(bm3));
            springIv.setImageBitmap(grayScale(bm1));
        }

        return view;
    }

    public void initImgViews(){
        bm1 = BitmapFactory.decodeResource(getResources(),R.drawable.spring);
        bm2 = BitmapFactory.decodeResource(getResources(),R.drawable.summer);
        bm3 = BitmapFactory.decodeResource(getResources(),R.drawable.autumn);
        bm4 = BitmapFactory.decodeResource(getResources(),R.drawable.winter);
        springIv.setImageBitmap(bm1);
        summerIv.setImageBitmap(bm2);
        autumnIv.setImageBitmap(bm3);
        winterIv.setImageBitmap(bm4);

        showDialog();

    }

    private Bitmap grayScale(final Bitmap orgBitmap){
        int width, height;
        width = orgBitmap.getWidth();
        height = orgBitmap.getHeight();

        Bitmap bmpGrayScale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bmpGrayScale);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(orgBitmap , 0 , 0 , paint);
        return bmpGrayScale;

    }

    public void showDialog(){
        goResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText userName;
                View dView = View.inflate(getContext(),R.layout.result_dialog,null);
                userName= (EditText)dView.findViewById(R.id.userName);
                final RadioButton female = (RadioButton)dView.findViewById(R.id.fradio);
                final RadioButton male = (RadioButton)dView.findViewById(R.id.mradio);





                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                dlg.setTitle("")
                        .setView(dView)
                        .setPositiveButton("닫기",null)
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String sex="";
                                String strName = userName.getText().toString();
                                if(female.isChecked()) sex ="Female";
                                if(male.isChecked())sex = "Male";



                                mDbOpenHelper.INSERTusers(strName,sex,result);



                                Bundle args = new Bundle();
                                args.putString("userName",strName);
                                args.putString("path",imgPath);
                                UserPlatteFragment userPlatteFragment = new UserPlatteFragment();
                                userPlatteFragment.setArguments(args);


                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.container,userPlatteFragment,"userName");
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                                ((HomeActivity)getActivity()).selectNaviItem(3);

                            }
                        }).show();

            }
        });
    }


}
