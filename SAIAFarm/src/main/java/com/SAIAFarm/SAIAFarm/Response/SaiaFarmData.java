package com.SAIAFarm.SAIAFarm.Response;

public class SaiaFarmData {
    String farmer_id;
    String farmsizecategory;  ///farmsize(marginal, small,medium,large)
    String farm_name;
    String farm_coordinates;
    String farm_type;
    String farm_size;   ////Area in Numeric form

    public SaiaFarmData() {
    }

    public SaiaFarmData(String farmer_id, String farmsizecategory, String farm_name, String farm_coordinates, String farm_type, String farm_size) {
        this.farmer_id = farmer_id;
        this.farmsizecategory = farmsizecategory;
        this.farm_name = farm_name;
        this.farm_coordinates = farm_coordinates;
        this.farm_type = farm_type;
        this.farm_size = farm_size;

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


}
