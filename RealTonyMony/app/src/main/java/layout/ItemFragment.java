package layout;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.choisangah.realtonymony.DbOpenHelper;
import com.example.choisangah.realtonymony.Item;
import com.example.choisangah.realtonymony.ItemAdapter;
import com.example.choisangah.realtonymony.R;

import java.util.ArrayList;

/**
 * Created by choisangah on 2017. 6. 13..
 */

public class ItemFragment extends Fragment {
    private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;
    ListView listView;
    ItemAdapter adapter;
    ArrayList<Item> itemArrayList = new ArrayList<>();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item1,container,false);

        mDbOpenHelper = new DbOpenHelper(this.getContext());
        mDbOpenHelper.open();

        mDbOpenHelper.INSERTitems("파스텔블러셔 CR03","어퓨","Spring Warm",
                "http://apieu.beautynet.co.kr/goods.detail.do?goodsNumber=72697" ,
                "http://file.beautynet.co.kr/upssdata2/upload/goods/97/72697/cr03-1_72697_20161114181552087.jpg");



        listView = (ListView)(view).findViewById(R.id.ilistView);
        adapter = new ItemAdapter();
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        addDBtolist();

        return view;
    }

    public void addDBtolist() {
        try {
            mCursor = mDbOpenHelper.getAllitems();
            mCursor.moveToFirst();
            int id = 0;
            String name = "";
            String brand = "";
            String tone = "";
            String path = "";
            String ipath = "";

            do {
                id = mCursor.getInt(mCursor.getColumnIndex("id"));
                name = mCursor.getString(mCursor.getColumnIndex("name"));
                brand = mCursor.getString(mCursor.getColumnIndex("brand"));
                tone = mCursor.getString(mCursor.getColumnIndex("tone"));
                path = mCursor.getString(mCursor.getColumnIndex("path"));
                ipath = mCursor.getString(mCursor.getColumnIndex("ipath"));

                adapter.addItem(id, name, brand, tone, path, ipath);
                Toast.makeText(getContext(),id+name+brand+tone+path+ipath,Toast.LENGTH_SHORT).show();



            } while (mCursor.moveToNext());
            mCursor.close();
        } catch (SQLiteException e) {
            Toast.makeText(getContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
