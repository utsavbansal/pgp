package com.Irrigation.SAIAFarming.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vividsolutions.jts.geom.Geometry;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
    @Column(name = "user_id")
    private String userId;

    @Column(name = "type", columnDefinition = "varchar(10) default 'SAIAFarmer'")
    private String type;

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

    /*@NotNull
    @Column(name = "village")
    private String village;*/

    //@NotNull
    @Column(name = "farmer_address_coordinates",columnDefinition = "Geometry")
    private com.vividsolutions.jts.geom.Geometry farmer_address_coordinates;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    public FarmerDatabase() {
    }

    //@CreationTimestamp
//    @Column(name = "local_date", columnDefinition = "DATE")
//    private LocalDate localDate;
//    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "modify_date")
//    private Date modifyDate;
//    @UpdateTimestamp
//    @Temporal(TemporalType.DATE)
//    @Column(name = "DataChange_LastTime")
//    private Date dataChangeLastModifiedTime;
   // @Column(name = "dateModified", nullable = false )
    @Column(name="dateCreated", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")

    //@CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date dateCreated;

    @Column(name="dateModified", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")

    //@UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date dateModified;
//    @PrePersist
//    protected void prePersist() {
////        if (this.dataChangeCreatedTime == null) dataChangeCreatedTime = new Date();
//        if (this.dataChangeLastModifiedTime == null) dataChangeLastModifiedTime = new Date();
//    }
//    @PrePersist
//    protected void setDateModified() {
//        this.dateModified = new Date();
//    }


//    @PrePersist
//    public void setCreatedat() {
//        this.created_at =  new Timestamp((new Date()).getTime());
//    }
    ///@Column(name = "dateModified", nullable = false)

    //@CreationTimestamp
    //@LastModifiedDate
    //private Date dateModified;
    ///private Date modifyDate;
   // private LocalDate dateModified;


//    public Date getModifyDate() {
//        return modifyDate;
//    }
//
//    public void setModifyDate(Date modifyDate) {
//        this.modifyDate = modifyDate;
//    }

    public FarmerDatabase(String head_name, String gender, String son_wife_daughter, String literacy, String address, Geometry farmer_address_coordinates) {
    }

    public int getFarmer_id() {
        return farmer_id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setType(String type) {
        this.type = type;
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


    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public com.vividsolutions.jts.geom.Geometry getFarmer_address_coordinates() {
        return farmer_address_coordinates;
    }

    public void setFarmer_address_coordinates(com.vividsolutions.jts.geom.Geometry farmer_address_coordinates) {
        this.farmer_address_coordinates = farmer_address_coordinates;
    }



    public FarmerDatabase(int farmer_id, String head_name, String gender, String son_wife_daughter, String literacy, Geometry farmer_address_coordinates, String address, List<FarmDatabase> farmDatabase) {
        this.farmer_id = farmer_id;
        this.head_name = head_name;
        this.gender = gender;
        this.son_wife_daughter = son_wife_daughter;
        this.literacy = literacy;
        //this.village = village;
        this.farmer_address_coordinates = farmer_address_coordinates;
        this.address = address;
        this.farmDatabase = farmDatabase;
    }

    @OneToMany( mappedBy = "farmerDatabase", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<FarmDatabase> farmDatabase;

    public List<FarmDatabase> getFarmDatabase() {
        return farmDatabase;
    }

    public void setFarmDatabase(List<FarmDatabase> farmDatabase) {
        this.farmDatabase = farmDatabase;
    }
}
