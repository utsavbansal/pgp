package com.SAIAFarm.SAIAFarm.Response;

public class SaiaiParcelData {
    String parcelId;
    String userId;
    String farm_id;
    String type;
    String location;
    String area;
    String category;
    String relatedSource;
    String belongsTo;
    String hasAgriCrop;
    String hasAgriSoil;
    String lastPlantedAt;
    String date_created;
    String date_modified;
    String  waterStressmean;
    String parcelName;

    public SaiaiParcelData(String parcelId, String userId, String farm_id, String type, String location, String area, String category, String relatedSource, String belongsTo, String hasAgriCrop, String hasAgriSoil, String lastPlantedAt, String date_created, String date_modified, String waterStressmean, String parcelName) {
        this.parcelId =parcelId;
        this.userId = userId;
        this.farm_id = farm_id;
        this.type = type;
        this.location = location;
        this.area = area;
        this.category = category;
        this.relatedSource = relatedSource;
        this.belongsTo = belongsTo;
        this.hasAgriCrop = hasAgriCrop;
        this.hasAgriSoil = hasAgriSoil;
        this.lastPlantedAt = lastPlantedAt;
        this.date_created = date_created;
        this.date_modified = date_modified;
        this.waterStressmean = waterStressmean;
        this.parcelName = parcelName;
    }
    public SaiaiParcelData(String userId, String farm_id, String location, String area, String category, String relatedSource, String belongsTo, String hasAgriCrop, String hasAgriSoil, String lastPlantedAt, String waterStressmean, String parcelName) {
        this.userId = userId;
        this.farm_id = farm_id;
        this.location = location;
        this.area = area;
        this.category = category;
        this.relatedSource = relatedSource;
        this.belongsTo = belongsTo;
        this.hasAgriCrop = hasAgriCrop;
        this.hasAgriSoil = hasAgriSoil;
        this.lastPlantedAt = lastPlantedAt;
        this.waterStressmean = waterStressmean;
        this.parcelName = parcelName;

    }
    public String getParcelId() { return parcelId; }

    public void setParcelId(String parcelId) { this.parcelId = parcelId; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFarm_id() {
        return farm_id;
    }

    public void setFarm_id(String farm_id) {
        this.farm_id = farm_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
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

    public String getLastPlantedAt() {
        return lastPlantedAt;
    }

    public void setLastPlantedAt(String lastPlantedAt) {
        this.lastPlantedAt = lastPlantedAt;
    }

    public String getWaterStressmean() {
        return waterStressmean;
    }

    public void setWaterStressmean(String waterStressmean) {
        this.waterStressmean = waterStressmean;
    }

    public String getParcelName() {
        return parcelName;
    }

    public void setParcelName(String parcelName) {
        this.parcelName = parcelName;
    }

    public SaiaiParcelData(String location, String parcelName) {

        this.location = location;
        this.parcelName = parcelName;

    }
}
