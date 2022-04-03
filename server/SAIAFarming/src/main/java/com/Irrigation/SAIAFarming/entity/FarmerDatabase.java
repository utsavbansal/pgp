package com.Irrigation.SAIAFarming.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.io.Serializable;

@Entity
@Table(name = "saia_farmers")
public class FarmerDatabase implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6796098319658059439L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farmer_id")
    private int farmer_id;

    @NotNull
    @Column(name = "head_name")
    private String head_name;

    @NotNull
    @Column(name = "gender")
    private String gender;

    @NotNull
    @Column(name = "son_wife_daughter")
    private String son_wife_daughter;

    @NotNull
    @Column(name = "literacy")
    private String literacy;

    @NotNull
    @Column(name = "village")
    private String village;

    //@NotNull
    @Column(name = "farmer_address_coordinates")
    private Point farmer_address_coordinates;

    @Column(name = "address_landmark")
    private String address_landmark;

    public FarmerDatabase() {
    }

    public int getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(int farmer_id) {
        this.farmer_id = farmer_id;
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

    public Point getFarmer_address_coordinates() {
        return farmer_address_coordinates;
    }

    public void setFarmer_address_coordinates(Point farmer_address_coordinates) {
        this.farmer_address_coordinates = farmer_address_coordinates;
    }

    public String getAddress_landmark() {
        return address_landmark;
    }

    public void setAddress_landmark(String address_landmark) {
        this.address_landmark = address_landmark;
    }

    public FarmerDatabase(String head_name, String gender, String son_wife_daughter, String literacy, String village, Point farmer_address_coordinates, String address_landmark) {
        this.head_name = head_name;
        this.gender = gender;
        this.son_wife_daughter = son_wife_daughter;
        this.literacy = literacy;
        this.village = village;
        this.farmer_address_coordinates = farmer_address_coordinates;
        this.address_landmark = address_landmark;
    }

    @OneToOne( mappedBy = "farmerDatabase", cascade = CascadeType.ALL)
    @JsonIgnore
    private FarmDatabase farmDatabase;

    public FarmDatabase getFarmDatabase() {
        return farmDatabase;
    }

    public void setFarmDatabase(FarmDatabase farmDatabase) {
        this.farmDatabase = farmDatabase;
    }
}
