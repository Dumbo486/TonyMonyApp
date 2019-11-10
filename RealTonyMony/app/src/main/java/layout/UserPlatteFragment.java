package layout;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.choisangah.realtonymony.DbOpenHelper;
import com.example.choisangah.realtonymony.HomeActivity;
import com.example.choisangah.realtonymony.R;

/**
 * Created by choisangah on 2017. 6. 14..
 */

public class UserPlatteFragment extends Fragment {
    String userName,imgPath;
    //MyDBmanager myDBmanager;
    DbOpenHelper mDbOpenHelper;
    Cursor mCursor;
    TextView name,sex,tone;
    ImageView palette,hairlist,userImgView;
    Button goItem;
    HomeFragment homeFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragment = new HomeFragment();
        Bundle arg = getArguments();

            userName = arg.getString("userName");
            imgPath = arg.getString("path");



    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo,container,false);

        mDbOpenHelper = new DbOpenHelper(this.getContext());
        mDbOpenHelper.open();

        name = (TextView)(view).findViewById(R.id.nameTv);
        sex = (TextView)(view).findViewById(R.id.sexTv);
        tone = (TextView)(view).findViewById(R.id.toneTv);

        palette = (ImageView)(view).findViewById(R.id.mypalette);
        hairlist = (ImageView)(view).findViewById(R.id.besthair);
        userImgView = (ImageView)(view).findViewById(R.id.userImgView);

        goItem = (Button)(view).findViewById(R.id.goItem);

        Init();
        moveItemFragment();
        return view;
    }

    public void Init(){
        if (imgPath == null)
            return;
        Bitmap bm = BitmapFactory.decodeFile(imgPath);
        userImgView.setImageBitmap(bm);


        try {
            mCursor = mDbOpenHelper.getMatchName(userName);
            mCursor.moveToFirst();
            String str = "";
            do{
                str = mCursor.getString(mCursor.getColumnIndex("name"));
                name.setText(str);
                str =  mCursor.getString(mCursor.getColumnIndex("sex"));
                sex.setText(str);
                str=  mCursor.getString(mCursor.getColumnIndex("tone"));
                tone.setText(str);

                if(str.equals("Spring Warm")){
                    palette.setImageResource(R.drawable.springp);
                    hairlist.setImageResource(R.drawable.hspring);
                }
                else if(str.equals("Summer Cool")){
                    palette.setImageResource(R.drawable.summerp);
                    hairlist.setImageResource(R.drawable.hsummer);
                }
                else if(str.equals("Autumn Warm")){
                    palette.setImageResource(R.drawable.autumnp);
                    hairlist.setImageResource(R.drawable.hautumn);
                }
                else if(str.equals("Winter Cool")){
                    palette.setImageResource(R.drawable.winterp);
                    hairlist.setImageResource(R.drawable.hwinter);
                }
            }while (mCursor.moveToNext());
            mCursor.close();

        }catch (SQLiteException e){
            Toast.makeText(getContext(),"Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void moveItemFragment(){
        goItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toneType = tone.getText().toString();

                Bundle args = new Bundle();
                args.putString("toneType",toneType);
                ItemFragment itemFragment = new ItemFragment();
                itemFragment.setArguments(args);


                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,itemFragment,"toneType");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                ((HomeActivity)getActivity()).selectNaviItem(2);
            }
        });
    }
}
