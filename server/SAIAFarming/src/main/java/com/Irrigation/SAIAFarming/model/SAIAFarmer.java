package com.Irrigation.SAIAFarming.model;

import com.vividsolutions.jts.geom.Geometry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;

public class SAIAFarmer {
    String user_id;
    String head_name  ;
    String gender;
    String son_wife_daughter ;
    String literacy ;
    String village ;
    Geometry farmer_address_coordinates ;
    String address_landmark ;
    //@FullTextField
    Address address;

    /*org.wololo.geojson.Geometry;
    org.locationtech.jts.geom.Geometry;*/



    public SAIAFarmer() {
    }



    /*public SAIAFarmer(String user_id, String head_name, String gender, String son_wife_daughter, String literacy, String village, Geometry farmer_address_coordinates, String address_landmark) {
        this.user_id = user_id;
        this.head_name = head_name;
        this.gender = gender;
        this.son_wife_daughter = son_wife_daughter;
        this.literacy = literacy;
        this.village = village;
        this.farmer_address_coordinates = farmer_address_coordinates;
        this.address_landmark = address_landmark;
    }*/
    public SAIAFarmer(String user_id, String head_name, String gender, String son_wife_daughter, String literacy, String village, Geometry farmer_address_coordinates, String address_landmark, Address address) {
        this.user_id = user_id;
        this.head_name = head_name;
        this.gender = gender;
        this.son_wife_daughter = son_wife_daughter;
        this.literacy = literacy;
        this.village = village;
        this.farmer_address_coordinates = farmer_address_coordinates;
        this.address_landmark = address_landmark;
        this.address = address;
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

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public Geometry getFarmer_address_coordinates() {
        return farmer_address_coordinates;
    }

    public void setFarmer_address_coordinates(Geometry farmer_address_coordinates) {
        this.farmer_address_coordinates = farmer_address_coordinates;
    }

    public String getAddress_landmark() {
        return address_landmark;
    }

    public void setAddress_landmark(String address_landmark) {
        this.address_landmark = address_landmark;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
