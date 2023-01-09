package com.Irrigation.SAIAFarming.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.JSONArray;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ETModel {

    String userId;
    String sdate;
    String edate;
    List <Float> coordinates;


    public ETModel() {
    }

    public ETModel(String userId, String sdate, String edate, List <Float> coordinates) {
        this.userId = userId;
        this.sdate = sdate;
        this.edate = edate;
        this.coordinates = coordinates;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

//    public List<Integer> getCoordinates() {
//        return coordinates;
//    }
//
//    public void setCoordinates(List<Integer> coordinates) {
//        this.coordinates = coordinates;

    public List<Float> getCoordinates() {
        List<Float> coordList = coordinates;
        return coordList;
    }

    public void setCoordinates(List<Float> coordinates) {
        this.coordinates = coordinates;
    }

}

