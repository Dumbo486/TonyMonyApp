package layout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.choisangah.realtonymony.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by choisangah on 2017. 6. 13..
 */

public class TonyTestFragment extends Fragment {
    TonyTest2Fragment tonyTest2Fragment;
    ImageView testImgView,goRight;
    Button goAlbum,goCamera;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    String absoultePath;
    private Uri mImageCaptureUri;
    private String mParam1;

    public static TonyTestFragment newInstance(String param1){
        TonyTestFragment fragment = new TonyTestFragment();
        Bundle args = new Bundle();
        args.putString("path",param1);
        fragment.setArguments(args);
        return fragment;
    }


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if(getArguments() != null) mParam1 = getArguments().getString("path");
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ttest1,container,false);
        tonyTest2Fragment = new TonyTest2Fragment();


        testImgView = (ImageView)(view).findViewById(R.id.testImgView);
        goAlbum = (Button)(view).findViewById(R.id.goAlbum);
        goCamera = (Button)(view).findViewById(R.id.goCamera);
        goRight = (ImageView)(view).findViewById(R.id.goRight);

        onClick();

        return view;
    }




    public void onClick(){
        goCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTakePhotoAction();
            }
        });

        goAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTakeAlbumAction();
            }
        });

        goRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveSecondStepAction();
            }
        });
    }

    public void doTakePhotoAction(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String url = "tmp_"+String.valueOf(System.currentTimeMillis())+".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT,mImageCaptureUri);
        startActivityForResult(intent,PICK_FROM_CAMERA);

    }

    public void doTakeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,PICK_FROM_ALBUM);
    }

    public void moveSecondStepAction(){

        Bundle args = new Bundle();
        args.putString("path",absoultePath);
        TonyTest2Fragment tonyTest2Fragment = new TonyTest2Fragment ();
        tonyTest2Fragment.setArguments(args);


        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.container, tonyTest2Fragment);
        fragmentTransaction.replace(R.id.container,tonyTest2Fragment,"path");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK) return;

        switch (requestCode){
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.d("PersonalColor", mImageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA:{
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri,"image/*");

                intent.putExtra("outputX",250);
                intent.putExtra("outputY",200);
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("scale",true);
                intent.putExtra("return-data",true);
                startActivityForResult(intent,CROP_FROM_IMAGE);
                break;
            }
            case CROP_FROM_IMAGE:{
                if(resultCode!=RESULT_OK) return;

                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/PersonalColor/"+ System.currentTimeMillis()+".jpg";


                if(extras != null){
                    Bitmap photo = extras.getParcelable("data");
                    testImgView.setImageBitmap(photo);

                    storeCropImage(photo,filePath);
                    absoultePath = filePath;
                    break;

                }

                File f = new File(mImageCaptureUri.getPath());
                if(f.exists()) f.delete();
            }

        }
    }

    private void storeCropImage(Bitmap bitmap,String filePath){
        //String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PersonalColor";
        String dirPath = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(dirPath+"/PersonalColor");
        //File directory_PersonalColor = new File(dirPath);

        if(!myDir.exists()){
            //Toast.makeText(getContext(),"해당 디렉토리 없어오",Toast.LENGTH_SHORT).show();
            myDir.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;


        try{

            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(copyFile)));

            out.flush();
            out.close();


        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();

        }
    }


}
