package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapplication.Model.QRCustomerModel;

import java.util.ArrayList;

public class CustomerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<QRCustomerModel> objects ;
    private static LayoutInflater inflate =null ;

    public CustomerAdapter(Context context,ArrayList<QRCustomerModel> objects) {

        this.mContext = context;
       this.objects = objects;
       inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Nullable
    @Override
    public QRCustomerModel getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView =convertView;
        itemView = (itemView==null) ? inflate.inflate(R.layout.list_item,null) :itemView;

        TextView textView1 =(TextView)itemView.findViewById(R.id.textView1);
        TextView textView2 =(TextView)itemView.findViewById(R.id.textview2);
        TextView textView3 =(TextView)itemView.findViewById(R.id.textview3);

        QRCustomerModel customerModel = objects.get(position);

        textView1.setText(customerModel.getId());
        textView2.setText(customerModel.getName());
        textView3.setText(customerModel.getPhone());
        return itemView;
    }
}
