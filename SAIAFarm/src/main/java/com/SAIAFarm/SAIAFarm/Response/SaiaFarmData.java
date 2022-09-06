package com.SAIAFarm.SAIAFarm.Response;

public class SaiaFarmData {
    String farmer_id;
    String farm_id;
    String type;
    String farmsizecategory;  ///farmsize(marginal, small,medium,large)
    String farm_name;
    String farm_type;
    String farm_size;   ////Area in Numeric form
    String farm_coordinates;
    String userId;
    String address;
    String date_created;
    String date_modified;

    public SaiaFarmData(String farmsizecategory, String farm_name, String farm_coordinates, String farm_type, String farm_size) {
    }

    public SaiaFarmData(String farm_id,String farmer_id, String type, String farmsizecategory, String farm_name, String farm_type, String farm_size, String farm_coordinates, String userId, String address, String date_created, String date_modified) {
        this.farm_id = farm_id;
        this.farmer_id = farmer_id;
        this.type = type;
        this.farmsizecategory = farmsizecategory;
        this.farm_name = farm_name;
        this.farm_type = farm_type;
        this.farm_size = farm_size;
        this.farm_coordinates = farm_coordinates;
        this.userId = userId;
        this.address = address;
        this.date_created = date_created;
        this.date_modified = date_modified;

    }

    public SaiaFarmData(String farmer_id, String farmsizecategory, String farm_name, String farm_type, String farm_size, String farm_coordinates, String userId, String address) {
        this.farmer_id = farmer_id;
        this.farmsizecategory = farmsizecategory;
        this.farm_name = farm_name;
        this.farm_type = farm_type;
        this.farm_size = farm_size;
        this.farm_coordinates = farm_coordinates;
        this.userId = userId;
        this.address = address;

    }
    /*public SaiaFarmData(String farmer_id, String farmsizecategory, String farm_name, String farm_type, String farm_size, String farm_coordinates, String userId, String address) {
        this.farmer_id = farmer_id;
        this.farmsizecategory = farmsizecategory;
        this.farm_name = farm_name;
        this.farm_type = farm_type;
        this.farm_size = farm_size;
        this.farm_coordinates = farm_coordinates;
        this.userId = userId;
        this.address = address;

    }*/

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getFarm_name() {
        return farm_name;
    }

    public void setFarm_name(String farm_name) {
        this.farm_name = farm_name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate_created() {
        return date_created;
    }

    public String getDate_modified() {
        return date_modified;
    }
}
