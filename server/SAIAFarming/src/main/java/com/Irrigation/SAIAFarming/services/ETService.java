package com.Irrigation.SAIAFarming.services;

import com.Irrigation.SAIAFarming.model.ETModel;
import com.SAIAFarm.SAIAFarm.Response.SaiaFarmData;
import com.SAIAFarm.SAIAFarm.Response.SaiaiParcelData;
import org.json.JSONException;

import java.util.List;

public interface ETService {
    public String getEtData(ETModel et) throws JSONException;
    public String getEtDataFarm(List<SaiaFarmData> etfarmsInfo, String sDate, String eDate) throws JSONException;

    public String getEtDataParcel(List<SaiaiParcelData> etparcelInfo, String sDate, String eDate) throws JSONException;
    public String getEtImageData(ETModel et) throws JSONException;
    public String getWdData(ETModel wd) throws JSONException;
    public String getWdDataParcel(List<SaiaiParcelData> etparcelInfo, String sDate, String eDate) throws JSONException;

}
