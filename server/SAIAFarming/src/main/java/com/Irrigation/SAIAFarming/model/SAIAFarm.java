package com.Irrigation.SAIAFarming.model;

public class SAIAFarm {
    String userId;
    String farmer_id;
    String farmsizecategory;
    String farmName;
    String farm_coordinates;
    String farm_type;
    String farm_size;

    public SAIAFarm() {
    }

    public SAIAFarm(String userId,String farmer_id, String farmsizecategory, String farmName, String farm_coordinates, String farm_type, String farm_size) {
        this.userId = userId;
        this.farmer_id = farmer_id;
        this.farmsizecategory = farmsizecategory;
        this.farmName = farmName;
        this.farm_coordinates = farm_coordinates;
        this.farm_type = farm_type;
        this.farm_size = farm_size;


    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getFarmsizecategory() {
        return farmsizecategory;
    }

    public void setFarmsizecategory(String farmsizecategory) {
        this.farmsizecategory = farmsizecategory;
    }



    public String getFarm_coordinates() {
        return farm_coordinates;
    }

    public void setFarm_coordinates(String farm_coordinates) {
        this.farm_coordinates = farm_coordinates;
    }

    public String getFarm_type() {
        return farm_type;
    }

    public void setFarm_type(String farm_type) {
        this.farm_type = farm_type;
    }

    public String getFarm_size() {
        return farm_size;
    }

    public void setFarm_size(String farm_size) {
        this.farm_size = farm_size;
    }
}
