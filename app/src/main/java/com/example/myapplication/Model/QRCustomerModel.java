package com.example.myapplication.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class QRCustomerModel extends RealmObject {
    private String name ;
    private String phone;
    @PrimaryKey
    private String id ;
    public QRCustomerModel(){

    }
    public QRCustomerModel(String id, String name, String phone){
    this.id = id;
    this.name = name;
    this.phone= phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }
}
