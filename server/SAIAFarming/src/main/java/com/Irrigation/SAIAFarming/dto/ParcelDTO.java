package com.Irrigation.SAIAFarming.dto;

import com.Irrigation.SAIAFarming.entity.FarmDatabase;
import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ParcelDTO {


    private int parcelId;

    private String parcelName;

    private String category;
    private String hasAgriCrop;
    private String lastPlantedAt;


    public String getLastPlantedAt() {
        return lastPlantedAt;
    }

    public void setLastPlantedAt(String lastPlantedAt) {
        this.lastPlantedAt = lastPlantedAt;
    }



    public int getParcelId() {
        return parcelId;
    }

    public void setParcelId(int parcelId) {
        this.parcelId = parcelId;
    }



    public String getParcelName() {
        return parcelName;
    }

    public void setParcelName(String parcelName) {
        this.parcelName = parcelName;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getHasAgriCrop() {
        return hasAgriCrop;
    }

    public void setHasAgriCrop(String hasAgriCrop) {
        this.hasAgriCrop = hasAgriCrop;
    }


}
