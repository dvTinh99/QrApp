package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplication.Model.QRCustomerModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "customer.db";
    public static final String TABLE_NAME= "customer_table";
    public static final String COL_1= "id";
    public static final String COL_2= "name";
    public static final String COL_3= "phone";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table "+TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME+"");
    onCreate(db);
    }

    public boolean insertData(String name, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,phone);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true ;
        }
    }

//    public Cursor getAllData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
//
//        return res;
//    }
    public ArrayList<QRCustomerModel> getCustom(){
        ArrayList<QRCustomerModel> list = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        if (res.getCount()==0){
           // Toast.makeText(Main2Activity.this, "nothing to show", Toast.LENGTH_SHORT).show();
            return list;
        }
        while (res.moveToNext()){
            list.add(new QRCustomerModel(String.valueOf(res.getInt(0)),
                    res.getString(1),
                    res.getString(2)));
        }
        //show data

        return list;
    }
    public Cursor checkCustomer(String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where phone = "+phone,null);
//        if (res.getCount()==0){
//            return true ;
//        }
        return res;
    }

}
