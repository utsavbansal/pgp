package com.SAIAFarm.SAIAFarm.Response;

public class SaiaFarmerData {
    String farmer_id;
    String user_id;
    String type;
    String head_name  ;
    String gender ;
    String son_wife_daughter ;
    String literacy ;
    //String village ;
    //String address_landmark;
    String farmer_address_coordinates;
    String address;
    String date_created;
    String date_modified;


    public SaiaFarmerData(String user_id, String head_name, String gender, String son_wife_daughter, String literacy, String farmer_address_coordinates, String address) {
        this.head_name = head_name;
        this.gender = gender;
        this.son_wife_daughter = son_wife_daughter;
        this.literacy = literacy;
        //this.village = village;
        //this.address_landmark = address_landmark;
        this.farmer_address_coordinates = farmer_address_coordinates;
        this.address = address;
    }

    public SaiaFarmerData() {
    }

    public SaiaFarmerData(String user_id,String farmer_id, String type, String head_name, String gender, String son_wife_daughter, String literacy, String farmer_address_coordinates, String address, String date_created, String date_modified) {
        this.user_id = user_id;
        this.type = type;
        this.farmer_id = farmer_id;
        this.head_name = head_name;
        this.gender = gender;
        this.son_wife_daughter = son_wife_daughter;
        this.literacy = literacy;
        //this.village = village;
        //this.address_landmark = address_landmark;
        this.farmer_address_coordinates = farmer_address_coordinates;
        this.address = address;
        this.date_created = date_created;
        this.date_modified = date_modified;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SaiaFarmerData(int farmer_id) {
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

    public String getSon_wife_daughter() {
        return son_wife_daughter;
    }

    public void setSon_wife_daughter(String son_wife_daughter) {
        this.son_wife_daughter = son_wife_daughter;
    }

    public String getLiteracy() {
        return literacy;
    }

    public void setLiteracy(String literacy) {
        this.literacy = literacy;
    }



    public String getFarmer_address_coordinates() {
        return farmer_address_coordinates;
    }

    public void setFarmer_address_coordinates(String farmer_address_coordinates) {
        this.farmer_address_coordinates = farmer_address_coordinates;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
