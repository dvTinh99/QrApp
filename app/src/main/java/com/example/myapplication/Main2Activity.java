package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.QRCustomerModel;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private DatabaseHelper db ;
    private RecyclerView listView;
    private Button btn_goBack ;
    private ArrayList<QRCustomerModel> list ;
    private EditText text_search ;
    private RecycleAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = (RecyclerView) findViewById(R.id.list_view);
        btn_goBack = (Button) findViewById(R.id.button_goback);
        text_search = (EditText) findViewById(R.id.text_search);

        btn_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                startActivities(new Intent[]{intent});
            }
        });
        db = new DatabaseHelper(this);

        list = db.getCustom();

        adapter = new RecycleAdapter(this,list);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        text_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }
    private void filter(String text){
        ArrayList<QRCustomerModel> filteredList = new ArrayList<>();

        for (QRCustomerModel item : list){
            try {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    //  Log.d("idgetname", "name : "+item.getName());
                    filteredList.add(item);
                }
                if (item.getPhone().contains(text)) {
                    filteredList.add(item);
                }
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        adapter.filerList(filteredList);
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
