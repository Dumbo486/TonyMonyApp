package com.example.choisangah.realtonymony;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by choisangah on 2017. 6. 15..
 */

public class ItemAdapter extends BaseAdapter {
    ArrayList<Item> itemArrayList = new ArrayList<>();
    Handler handler = new Handler();
    Thread thread;



    @Override
    public int getCount() {
        return itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,null);
//            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.listitem, parent, false);
        }

        final ImageView itemIv = (ImageView)convertView.findViewById(R.id.itemIv);
        TextView ibrandTv = (TextView)convertView.findViewById(R.id.ibrandTv);
        TextView inameTv = (TextView)convertView.findViewById(R.id.inameTv);
        TextView itoneTv = (TextView)convertView.findViewById(R.id.itoneTv);
        Button ilinkBtn = (Button)convertView.findViewById(R.id.ilinkBtn);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                // TODO Auto-generated method stub
                try{
                    // 걍 외우는게 좋다 -_-;
                    URL url = new URL(itemArrayList.get(position).getiImglink());
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            itemIv.setImageBitmap(bm);
                        }
                    });
                    itemIv.setImageBitmap(bm); //비트맵 객체로 보여주기
                } catch(Exception e){

                }

            }
        });
        thread.start();


//        itemIv.setImageResource(R.drawable.sample);
        ibrandTv.setText(itemArrayList.get(position).getIbrand());
        inameTv.setText(itemArrayList.get(position).getIname());
        itoneTv.setText(itemArrayList.get(position).getItone());
//        ilinkBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //링크로 웹 뷰 뜌뜌
//            }
//        });




        return null;
    }

    public void addItem(int id, String name, String brand, String tone, String path, String ipath) {
        Item item = new Item();

        item.setId(id);
        item.setIname(name);
        item.setIbrand(brand);
        item.setItone(tone);
        item.setiSitelink(path);
        item.setiImglink(ipath);

        itemArrayList.add(item);
        this.notifyDataSetChanged();


    }

}
