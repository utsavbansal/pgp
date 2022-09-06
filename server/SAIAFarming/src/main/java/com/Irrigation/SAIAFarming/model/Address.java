package com.Irrigation.SAIAFarming.model;

import com.vividsolutions.jts.geom.Geometry;

public class Address {
    String village ;
    String address_landmark ;

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getAddress_landmark() {
        return address_landmark;
    }

    public void setAddress_landmark(String address_landmark) {
        this.address_landmark = address_landmark;
    }
}
