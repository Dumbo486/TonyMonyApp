package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.choisangah.realtonymony.HomeActivity;
import com.example.choisangah.realtonymony.HomeGridAdapter;
import com.example.choisangah.realtonymony.R;
import com.example.choisangah.realtonymony.Skin;

import java.util.ArrayList;

/**
 * Created by choisangah on 2017. 6. 13..
 */

public class HomeFragment extends Fragment {
    TonyTestFragment tonyTestFragment;

    GridView gridView;
    Button goTest;
    HomeGridAdapter gridAdapter;
    ArrayList<Skin> skins = new ArrayList<>();
    final static int imglist[] = {R.drawable.springwarm,R.drawable.summercool,R.drawable.autumnwarm,R.drawable.wintercool};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home1,container,false);
        gridView = (GridView)(view).findViewById(R.id.gridView);
        goTest = (Button)(view).findViewById(R.id.goTest);
        tonyTestFragment = new TonyTestFragment();



        gridAdapter = new HomeGridAdapter(getContext(),skins);
        gridView.setAdapter(gridAdapter);

        addGridItem();
        gridClick();
        btnClick();
        return view;
    }

    void addGridItem(){
        if(skins.size() != 4){
            skins.add(new Skin("Spring","Warm",imglist[0]));
            skins.add(new Skin("Summer","Cool",imglist[1]));
            skins.add(new Skin("Autumn","Warm",imglist[2]));
            skins.add(new Skin("Winter","Cool",imglist[3]));
        }

    }

    void gridClick(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View dlgview = View.inflate(getContext(),R.layout.home_dialog,null);
                ImageView diaImg = (ImageView)(dlgview).findViewById(R.id.diaImg);
                TextView diaTv = (TextView)(dlgview).findViewById(R.id.diaTv);
                TextView diaTv2 = (TextView)(dlgview).findViewById(R.id.diaTv2);

                if(position ==0){
                    diaImg.setImageResource(R.drawable.dia1);
                    diaTv.setText("노란색을 지닌 따뜻한 유형");
                    diaTv2.setText("선명하고 밝은 톤이 잘 어울림");
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                    dlg.setTitle("Warm Spring")
                            .setView(dlgview)
                            .setNegativeButton("확인",null);
                    dlg.show();
                }
                else if(position ==1){
                    diaImg.setImageResource(R.drawable.dia2);
                    diaTv.setText("흰색과 파랑을 지닌 차가운 유형");
                    diaTv2.setText("화사하고 부드럽고 여성스러운 느낌을 줌");
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                    dlg.setTitle("Cool Summer")
                            .setView(dlgview)
                            .setNegativeButton("확인",null);
                    dlg.show();
                }
                else if(position ==2){
                    diaImg.setImageResource(R.drawable.dia3);
                    diaTv.setText(" 우리나라 여성에게 잘 어울리는황색을 지닌 따뜻한 유형");
                    diaTv2.setText("차분하고 편안한 느낌을 줌");
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                    dlg.setTitle("Warm Autumn")
                            .setView(dlgview)
                            .setNegativeButton("확인",null);
                    dlg.show();
                }
                else{
                    diaImg.setImageResource(R.drawable.dia4);
                    diaTv.setText("파랑과 흰색, 검정을 지닌 차가운 유형");
                    diaTv2.setText("선명하고 시적인 강렬한 느낌을 줌");
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                    dlg.setTitle("Cool Winter")
                            .setView(dlgview)
                            .setNegativeButton("확인",null);
                    dlg.show();
                }

            }
        });
    }

    void btnClick(){
        goTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, tonyTestFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                ((HomeActivity)getActivity()).selectNaviItem(1);
            }
        });
    }
}
