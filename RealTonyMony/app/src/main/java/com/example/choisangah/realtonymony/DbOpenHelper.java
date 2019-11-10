package com.example.choisangah.realtonymony;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by choisangah on 2017. 6. 15..
 */

public class DbOpenHelper {

    private static final String DATABASE_NAME = "TonyMonyDB";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String _TABLENAME = "users";
            String _ID = "id";
            String NAME = "name";
            String SEX = "sex";
            String TONE = "tone";

            String _TABLENAME2 = "items";
            String _ID2 = "id";
            String NAME2 = "name";
            String BRAND2 = "brand";
            String TONE2 = "tone";
            String PATH2 = "path";
            String IPATH2 = "ipath";

           String sql = "create table "+_TABLENAME+"("
                   +_ID+" integer primary key autoincrement, "
                   +NAME+" text not null , "
                   +SEX+" text not null , "
                   +TONE+" text not null );";

            String sql2 = "create table "+_TABLENAME2+"("
                    +_ID2+" integer primary key autoincrement, "
                    +NAME2+" text not null , "
                    +BRAND2+" text not null , "
                    +TONE2+" text not null , "
                    +PATH2+" text not null , "
                    +IPATH2+" text not null);";

            db.execSQL(sql);
            db.execSQL(sql2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("Drop table if exists users");
            db.execSQL("Drop table if exists items");
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context) {
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException
    {
        mDBHelper = new DatabaseHelper(mCtx,DATABASE_NAME,null,DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        mDB.close();
    }

    public void INSERTusers(String name,String sex, String tone)
    {
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("sex",sex);
        values.put("tone",tone);
        mDB.insert("users",null,values);

    }

    public void INSERTitems(String name,String brand, String tone, String path, String ipath)
    {
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("brand",brand);
        values.put("tone",tone);
        values.put("path",path);
        values.put("ipath",ipath);
        mDB.insert("items",null,values);

    }



    public void deleteusers(String name)
    {
        mDB.delete("users","name = '" +name+"'",null);
    }

    public void deleteitems(String name)
    {
        mDB.delete("items","name = '" + name+"'",null);
    }

    public Cursor getMatchName(String name){
        Cursor c = mDB.rawQuery( "select * from users where name=" + "'" + name + "'" , null);
        return c;
    }

    public Cursor getMatchName2(String name){
        Cursor c = mDB.rawQuery( "select * from items where name=" + "'" + name + "'" , null);
        return c;
    }

    public Cursor getMatchTone(String tone){
        Cursor c = mDB.rawQuery( "select * from users where tone=" + "'" + tone + "'" , null);
        return c;
    }

    public Cursor getMatchTone2(String tone){
        Cursor c = mDB.rawQuery( "select * from items where tone=" + "'" + tone + "'" , null);
        return c;
    }

    public Cursor getAllitems()
    {
        return mDB.query("items",null,null,null,null,null,"id desc");
    }









}
