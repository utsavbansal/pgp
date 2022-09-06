package com.Irrigation.SAIAFarming.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "agri_farm")

public class FarmDatabase implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6796098319658059439L;

    @NotNull
    @Column(name = "user_id")
    private String userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_id",unique = true)
    private int farm_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "farmer_id")
    //@Column(name = "farmer_id")
    private FarmerDatabase farmerDatabase;
    @NotNull
    @Column(name = "type", columnDefinition = "varchar(10) default 'AgriFarm'")
    private String type;
    @NotNull
    @Column(name = "farm_name")
    private String farmName;

    //@NotNull
    /*@Column(name = "farm_coordinates")
    private Point farm_coordinates;*/

    @Column(name = "farm_coordinates",columnDefinition = "Geometry")
    private com.vividsolutions.jts.geom.Geometry farm_coordinates;

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

    @Column(name="dateCreated", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")

    //@CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    @Column(name="dateModified", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")

    //@UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date dateModified;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

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


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

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

    public Geometry getFarm_coordinates() {
        return farm_coordinates;
    }

    public void setFarm_coordinates(Geometry farm_coordinates) {
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

    public FarmDatabase(int farm_id, String farmName, Geometry farm_coordinates, String farmsizecategory, String farm_type, String farm_size, String address) {
        this.farm_id = farm_id;
        this.farmName = farmName;
        this.farm_coordinates = farm_coordinates;
        this.farmsizecategory = farmsizecategory;
        this.farm_type = farm_type;
        this.farm_size = farm_size;
        this.address = address;
    }
    @OneToMany( mappedBy = "farmDatabase", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ParcelDatabase> parcelDatabases;

    public List<ParcelDatabase> getParcelDatabases() {
        return parcelDatabases;
    }

    public void setParcelDatabases(List<ParcelDatabase> parcelDatabases) {
        this.parcelDatabases = parcelDatabases;
    }
}
