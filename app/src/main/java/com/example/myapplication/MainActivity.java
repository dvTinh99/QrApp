package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.QRCustomerModel;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView ;
    private TextView txtResult ;
    private Switch aSwitch;
    private Realm realm;
    private String name ;
    private  String phone;
    private RealmAsyncTask realmtask;
    private DatabaseHelper myDb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Realm.init (this);
//        RealmConfiguration configuration = new RealmConfiguration.Builder().name("com.example.myapplication").build();
//        Realm.setDefaultConfiguration(configuration);

        myDb = new DatabaseHelper(this);

        aSwitch = (Switch)findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked== true){
                   openMain2();

                }
            }

            private void openMain2() {
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                startActivities(new Intent[]{i});
            }


        });
        //init
        scannerView = (ZXingScannerView)findViewById(R.id.zxscan);
        scannerView.resumeCameraPreview(MainActivity.this);
        txtResult =(TextView)findViewById(R.id.txt_result);
        //yeu cau mo camera
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(MainActivity.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this, "Hay chap nhan yeu cau", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }


    @Override
    public void handleResult(Result rawResult) {
        //day la raw
        processRawResult(rawResult.getText());
        boolean checkGate = myDb.checkCustomer(phone);
        if (checkGate){
            boolean result = myDb.insertData(name,phone);
            if (result){
                Toast.makeText(this, "insert success", Toast.LENGTH_SHORT).show();
                txtResult.setText("TRUE");
            }else {
                Toast.makeText(this, "insert false", Toast.LENGTH_SHORT).show();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            txtResult.setText("ERROR ! CODE HAS BEEN USEDED");
        }

    }


    private void processRawResult(String text) {

        String chuoi = text;

        String[] arr = chuoi.split("20");
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < chuoi.length(); i+=2) {
            String str = chuoi.substring(i, i+2);
            output.append((char)Integer.parseInt(str, 16));
        }
        // System.out.println(output);
        String[] nameAndPhone = output.toString().split("/");
         name = nameAndPhone[0];
         phone = nameAndPhone[1];
       txtResult.setText(text);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scannerView.resumeCameraPreview(MainActivity.this);

    }
    @Override
    protected void onDestroy() {
        scannerView.stopCamera();
        super.onDestroy();
        realmtask.cancel();
        realm.close();
    }


}
