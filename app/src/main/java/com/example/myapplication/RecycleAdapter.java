package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.QRCustomerModel;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private Context context;
    private List<QRCustomerModel> customer ;
    public RecycleAdapter(Context context, List<QRCustomerModel> customer){
        this.customer = customer;
        this.context = context ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QRCustomerModel customerModel = customer.get(position);

        holder.textView1.setText(customerModel.getId());
        holder.textView2.setText(customerModel.getName());
        holder.textView3.setText(customerModel.getPhone());
    }

    @Override
    public int getItemCount() {
        return this.customer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             this.textView1 =(TextView)itemView.findViewById(R.id.textView1);
             this.textView2 =(TextView)itemView.findViewById(R.id.textview2);
             this.textView3 =(TextView)itemView.findViewById(R.id.textview3);

        }
    }
    public void filerList(ArrayList<QRCustomerModel> filerList){
        customer = filerList ;
        notifyDataSetChanged();

    }
}
