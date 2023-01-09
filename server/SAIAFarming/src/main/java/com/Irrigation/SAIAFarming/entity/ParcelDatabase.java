package com.Irrigation.SAIAFarming.entity;

import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "agri_parcel")
public class ParcelDatabase implements Serializable {

    private static final long serialVersionUID = 6796098319658059439L;

    @NotNull
    @Column(name = "user_id")
    private String userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parcel_id")
    private int parcelId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "farm_id")
    private FarmDatabase farmDatabase;
//    @Column(name = "farmId")
//    private int farmId;
    @Column(name = "type", columnDefinition = "varchar(10) default 'AgriParcel'")
    private String type;

    @Column(name="dateCreated", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    @Column(name="dateModified", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.DATE)
    private Date dateModified;

    @Column(name = "location",columnDefinition = "Geometry")
    private com.vividsolutions.jts.geom.Geometry location;

    @NotNull
    @Column(name = "parcel_name")
    private String parcelName;

    @NotNull
    @Column(name = "area")
    private String area;

    @Column(name = "category")
    private String category;

    @Column(name = "relatedSource")
    private String relatedSource;

    @NotNull
    @Column(name = "belongsTo")
    private String belongsTo;

    @Column(name = "hasAgriCrop")
    private String hasAgriCrop;

    @Column(name = "hasAgriSoil")
    private String hasAgriSoil;

    @Column(name = "lastPlantedAt")
    private Date lastPlantedAt;

    @Column(name= "waterStressmean")
    private String waterStressmean;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getParcelId() {
        return parcelId;
    }

    public void setParcelId(int parcelId) {
        this.parcelId = parcelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Geometry getLocation() {
        return location;
    }

    public void setLocation(Geometry location) {
        this.location = location;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRelatedSource() {
        return relatedSource;
    }

    public void setRelatedSource(String relatedSource) {
        this.relatedSource = relatedSource;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    public String getHasAgriCrop() {
        return hasAgriCrop;
    }

    public void setHasAgriCrop(String hasAgriCrop) {
        this.hasAgriCrop = hasAgriCrop;
    }

    public String getHasAgriSoil() {
        return hasAgriSoil;
    }

    public void setHasAgriSoil(String hasAgriSoil) {
        this.hasAgriSoil = hasAgriSoil;
    }

    public Date getLastPlantedAt() {
        return lastPlantedAt;
    }

    public void setLastPlantedAt(Date lastPlantedAt) {
        this.lastPlantedAt = lastPlantedAt;
    }

    public String getWaterStressmean() { return waterStressmean; }

    public void setWaterStressmean(String waterStressmean) { this.waterStressmean = waterStressmean; }
}
