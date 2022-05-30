package com.Irrigation.SAIAFarming.model;

public class OtherDetails {

    String head_name  ;
    String gender;

    public OtherDetails() {

    }


    public String getHead_name() {
        return head_name;
    }

    public void setHead_name(String head_name) {
        this.head_name = head_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public OtherDetails(String name, String gender) {
        this.head_name = name;
        this.gender = gender;
    }
}
