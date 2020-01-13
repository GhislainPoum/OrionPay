package com.poum.orionpay.Models;

public class Transaction {
    private String id;
    private String main_no_id;
    private String main_smsField_id;

    public Transaction(String id, String main_no_id, String main_smsField_id) {
        this.id = id;
        this.main_no_id = main_no_id;
        this.main_smsField_id = main_smsField_id;
    }

    public  Transaction(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain_no_id() {
        return main_no_id;
    }

    public void setMain_no_id(String main_no_id) {
        this.main_no_id = main_no_id;
    }

    public String getMain_smsField_id() {
        return main_smsField_id;
    }

    public void setMain_smsField_id(String main_smsField_id) {
        this.main_smsField_id = main_smsField_id;
    }
}
