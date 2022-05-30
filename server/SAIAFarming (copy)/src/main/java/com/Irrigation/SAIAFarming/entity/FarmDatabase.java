package com.Irrigation.SAIAFarming.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.io.Serializable;

@Entity
@Table(name = "agri_farm")
public class FarmDatabase implements Serializable {



    /**
     *
     */
    private static final long serialVersionUID = 6796098319658059439L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_id")
    private int farm_id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "farmer_id")
    //@Column(name = "farmer_id")
    private FarmerDatabase farmerDatabase;

    @NotNull
    @Column(name = "farm_name")
    private String farm_name;

    //@NotNull
    @Column(name = "farm_coordinates")
    private Point farm_coordinates;

    @NotNull
    @Column(name = "farmsizecategory")
    private String farmsizecategory;


    @NotNull
    @Column(name = "farm_type")
    private String farm_type;

    @NotNull
    @Column(name = "farm_size")
    private String farm_size;

    public FarmDatabase() {
    }

    public int getFarm_id() {
        return farm_id;
    }

    public void setFarm_id(int farm_id) {
        this.farm_id = farm_id;
    }

//    public int getFarmer_id() {
//        return farmer_id;
//    }
//
//    public void setFarmer_id(int farmer_id) {
//        this.farmer_id = farmer_id;
//    }


    public String getFarmsizecategory() {
        return farmsizecategory;
    }

    public void setFarmsizecategory(String farmsizecategory) {
        this.farmsizecategory = farmsizecategory;
    }

    public FarmerDatabase getFarmerDatabase() {
        return farmerDatabase;
    }

    public void setFarmerDatabase(FarmerDatabase farmerDatabase) {
        this.farmerDatabase = farmerDatabase;
    }

    public String getFarm_name() {
        return farm_name;
    }

    public void setFarm_name(String farm_name) {
        this.farm_name = farm_name;
    }

    public Point getFarm_coordinates() {
        return farm_coordinates;
    }

    public void setFarm_coordinates(Point farm_coordinates) {
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

    public FarmDatabase(int farm_id, String farm_name, Point farm_coordinates, String farmsizecategory, String farm_type, String farm_size) {
        this.farm_id = farm_id;
        this.farm_name = farm_name;
        this.farm_coordinates = farm_coordinates;
        this.farmsizecategory = farmsizecategory;
        this.farm_type = farm_type;
        this.farm_size = farm_size;
    }
}
