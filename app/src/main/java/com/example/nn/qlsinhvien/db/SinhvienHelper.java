package com.example.nn.qlsinhvien.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nn.qlsinhvien.models.Sinhvien;

import java.util.ArrayList;
import java.util.List;

public class SinhvienHelper {
    private final static  String DATABASE_NAME = "qlsinhvien.s3db";
    private  static SQLiteDatabase database;
    private Context context;

    public SinhvienHelper(Context context){
        this.context = context;
        database = MySQLite.initDatabase(context,DATABASE_NAME);
    }
    public void insertsinhvien(Sinhvien sinhvien){
        ContentValues contentValues = new ContentValues();

        contentValues.put(Sinhvien.COL_MSSV,sinhvien.getMSSV());
        contentValues.put(Sinhvien.COL_HOTEN,sinhvien.getHOTEN());
        contentValues.put(Sinhvien.COL_LOP,sinhvien.getLOP());

        database.insert(Sinhvien.TABLE_NAME,null,contentValues);
    }
    public void updatesinhvien(Sinhvien sinhvien){
        ContentValues contentValues = new ContentValues();

        String[] where = {String.valueOf(sinhvien.getID())};

        contentValues.put(Sinhvien.COL_MSSV,sinhvien.getMSSV());
        contentValues.put(Sinhvien.COL_HOTEN,sinhvien.getHOTEN());
        contentValues.put(Sinhvien.COL_LOP,sinhvien.getLOP());

        database.update(Sinhvien.TABLE_NAME,contentValues,Sinhvien.WHERECLAUSE,where);
    }
    public void deletesinhvien(Sinhvien sinhvien){
        String[] where = {String.valueOf(sinhvien.getID())};
        database.delete(Sinhvien.TABLE_NAME,Sinhvien.WHERECLAUSE,where);
    }
    public List<Sinhvien> getAllsinhvien(){
        List<Sinhvien> sinhvienList = new ArrayList<Sinhvien>();
        Cursor cursor = database.rawQuery( "Select * from "+Sinhvien.TABLE_NAME,null);
        if(cursor.moveToFirst()){
            do{
                Sinhvien sinhvien = new Sinhvien();
                sinhvien.setID(Integer.parseInt(cursor.getString(0)));
                sinhvien.setMSSV(cursor.getString(1));
                sinhvien.setHOTEN(cursor.getString(2));
                sinhvien.setLOP(cursor.getString(3));
                sinhvienList.add(sinhvien);
            }while (cursor.moveToNext());
        }
        return  sinhvienList;
    }
}

