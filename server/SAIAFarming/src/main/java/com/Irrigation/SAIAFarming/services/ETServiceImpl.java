package com.Irrigation.SAIAFarming.services;

import com.Irrigation.SAIAFarming.model.ETModel;
import com.SAIAFarm.SAIAFarm.Response.SaiaFarmData;
import com.SAIAFarm.SAIAFarm.Response.SaiaiParcelData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ETServiceImpl implements ETService{

    @Value("${external.api.url}")
    private String baseUrl;

    @Autowired
    private  RestTemplate restTemplate;

    @Override
    public String getEtData(ETModel et) throws JSONException {
        ETModel model = null;
        List<Float> coord;
        String newET = "";
        try{


        JSONObject etdataObjcet = new JSONObject();
        etdataObjcet.put("sdate", et.getSdate());
        etdataObjcet.put("edate", et.getEdate());

        coord = et.getCoordinates();

        JSONArray coordArray = new JSONArray();
        for(int i = 0; i < coord.size(); i++) {
                coordArray.put(coord.get(i));
            }
        etdataObjcet.put("coordinates", coordArray);

            //HttpEntity<String> requestBody = new HttpEntity<>(etdataObjcet.toString(), headers);



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(etdataObjcet.toString(), headers);
        newET = restTemplate.postForObject(baseUrl, httpEntity, String.class);
/*
        if(newET.getStatusCode() == HttpStatus.CREATED) {
            String retData = newET.getBody();
        }*/
        System.out.println("et data : "+newET);
        return newET;
}
          catch(HttpStatusCodeException he) {
            System.out.println("Caught Rest Template Exception: " + he.getResponseBodyAsString());
        }
     return  newET;

    }

    @Override
    public String getEtDataFarm(List<SaiaFarmData> etfarmsInfo, String sDate, String eDate) throws JSONException {

        String newET = "";
        try{
           // ETModel etData = new ETModel(userId, sdate, edate, coordArray);

            String coor = etfarmsInfo.get(0).getFarm_coordinates();
            System.out.println("coordinates: "+coor);
            String coord=coor.replaceAll("[^a-zA-Z0-9,.) ]","");
            String coordi=coord.replace("POLYGON","[");
            String coordin=coordi.replaceFirst("[)]","]");
            String coordina=coordin.replace(")","");
            String coordinate=coordina.replace(" ",", ");
            System.out.println("Desired coordinates"+coordinate);

            System.out.println("sdate"+sDate);
            System.out.println("edate"+eDate);
            System.out.println("coordinates"+coordinate);

            String replace = coordinate.replace("[","");
            System.out.println(replace);
            String replace1 = replace.replace("]","");
            System.out.println(replace1);
            List<String> myList = new ArrayList<String>(Arrays.asList(replace1.split(",")));
            System.out.println(myList.toString());


            JSONArray coordArray = new JSONArray();
            for(int i = 0; i < myList.size(); i++) {

                coordArray.put(Float.parseFloat(myList.get(i)));
            }
            System.out.println("json coordinta"+ coordArray);

            JSONObject etdataObjcet = new JSONObject();
            etdataObjcet.put("sdate",sDate);
            etdataObjcet.put("edate", eDate);

            etdataObjcet.put("coordinates", coordArray);

            //HttpEntity<String> requestBody = new HttpEntity<>(etdataObjcet.toString(), headers);



            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> httpEntity = new HttpEntity<>(etdataObjcet.toString(), headers);
            newET = restTemplate.postForObject(baseUrl, httpEntity, String.class);

            System.out.println("et data : "+newET);
            return newET;
        }
        catch(HttpStatusCodeException he) {
            System.out.println("Caught Rest Template Exception: " + he.getResponseBodyAsString());
        }
        return  newET;

    }

    @Override
    public String getEtDataParcel(List<SaiaiParcelData> etparcelInfo, String sDate, String eDate) throws JSONException {

        String newET = "";
        try{
            // ETModel etData = new ETModel(userId, sdate, edate, coordArray);

            String coor = etparcelInfo.get(0).getLocation();
            System.out.println("coordinates: "+coor);
            String coord=coor.replaceAll("[^a-zA-Z0-9,.) ]","");
            String coordi=coord.replace("POLYGON","[");
            String coordin=coordi.replaceFirst("[)]","]");
            String coordina=coordin.replace(")","");
            String coordinate=coordina.replace(" ",", ");
            System.out.println("Desired coordinates"+coordinate);

            System.out.println("sdate"+sDate);
            System.out.println("edate"+eDate);
            System.out.println("coordinates"+coordinate);

            String replace = coordinate.replace("[","");
            System.out.println(replace);
            String replace1 = replace.replace("]","");
            System.out.println(replace1);
            List<String> myList = new ArrayList<String>(Arrays.asList(replace1.split(",")));
            System.out.println(myList.toString());


            JSONArray coordArray = new JSONArray();
            for(int i = 0; i < myList.size(); i++) {

                coordArray.put(Float.parseFloat(myList.get(i)));
            }
            System.out.println("json coordinta"+ coordArray);

            JSONObject etdataObjcet = new JSONObject();
            etdataObjcet.put("sdate",sDate);
            etdataObjcet.put("edate", eDate);

            etdataObjcet.put("coordinates", coordArray);

            //HttpEntity<String> requestBody = new HttpEntity<>(etdataObjcet.toString(), headers);



            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> httpEntity = new HttpEntity<>(etdataObjcet.toString(), headers);
            newET = restTemplate.postForObject(baseUrl, httpEntity, String.class);

            System.out.println("et parcel data : "+newET);
            return newET;
        }
        catch(HttpStatusCodeException he) {
            System.out.println("Caught Rest Template Exception: " + he.getResponseBodyAsString());
        }
        return  newET;

    }


    @Override
    public String getEtImageData(ETModel et) throws JSONException {
        ETModel model = null;
        List<Float> coord;
        String newET = "";
        try{


            JSONObject etdataObjcet = new JSONObject();
            etdataObjcet.put("sdate", et.getSdate());
            etdataObjcet.put("edate", et.getEdate());

            coord = et.getCoordinates();

            JSONArray coordArray = new JSONArray();
            for(int i = 0; i < coord.size(); i++) {
                coordArray.put(coord.get(i));
            }
            etdataObjcet.put("coordinates", coordArray);

            //HttpEntity<String> requestBody = new HttpEntity<>(etdataObjcet.toString(), headers);



            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> httpEntity = new HttpEntity<>(etdataObjcet.toString(), headers);


            String Url = baseUrl+"/image_openeo_data";
            System.out.println(Url);
            newET = restTemplate.postForObject(Url, httpEntity, String.class);
/*
        if(newET.getStatusCode() == HttpStatus.CREATED) {
            String retData = newET.getBody();
        }*/
            System.out.println("et data : "+newET);
            return newET;
        }
        catch(HttpStatusCodeException he) {
            System.out.println("Caught Rest Template Exception: " + he.getResponseBodyAsString());
        }
        return  newET;

    }

    @Override
    public String getWdData(ETModel wd) throws JSONException {
        ETModel model = null;
        List<Float> coord;
        String newWD = "";
        try{


            JSONObject etdataObjcet = new JSONObject();
            etdataObjcet.put("sdate", wd.getSdate());
            etdataObjcet.put("edate", wd.getEdate());

            coord = wd.getCoordinates();

            JSONArray coordArray = new JSONArray();
            for(int i = 0; i < coord.size(); i++) {
                coordArray.put(coord.get(i));
            }
            etdataObjcet.put("coordinates", coordArray);

            //HttpEntity<String> requestBody = new HttpEntity<>(etdataObjcet.toString(), headers);



            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String Url = baseUrl+"/wd_data";

            HttpEntity<String> httpEntity = new HttpEntity<>(etdataObjcet.toString(), headers);
            newWD = restTemplate.postForObject(Url, httpEntity, String.class);
/*
        if(newET.getStatusCode() == HttpStatus.CREATED) {
            String retData = newET.getBody();
        }*/
            System.out.println("wd data : "+newWD);
            return newWD;
        }
        catch(HttpStatusCodeException he) {
            System.out.println("Caught Rest Template Exception: " + he.getResponseBodyAsString());
        }
        return  newWD;

    }

    @Override
    public String getWdDataParcel(List<SaiaiParcelData> etparcelInfo, String sDate, String eDate) throws JSONException {

        String newWD = "";
        try{
            // ETModel etData = new ETModel(userId, sdate, edate, coordArray);

            String coor = etparcelInfo.get(0).getLocation();
            System.out.println("coordinates: "+coor);
            String coord=coor.replaceAll("[^a-zA-Z0-9,.) ]","");
            String coordi=coord.replace("POLYGON","[");
            String coordin=coordi.replaceFirst("[)]","]");
            String coordina=coordin.replace(")","");
            String coordinate=coordina.replace(" ",", ");
            System.out.println("Desired coordinates"+coordinate);

            System.out.println("sdate"+sDate);
            System.out.println("edate"+eDate);
            System.out.println("coordinates"+coordinate);

            String replace = coordinate.replace("[","");
            System.out.println(replace);
            String replace1 = replace.replace("]","");
            System.out.println(replace1);
            List<String> myList = new ArrayList<String>(Arrays.asList(replace1.split(",")));
            System.out.println(myList.toString());


            JSONArray coordArray = new JSONArray();
            for(int i = 0; i < myList.size(); i++) {

                coordArray.put(Float.parseFloat(myList.get(i)));
            }
            System.out.println("json coordinta"+ coordArray);

            JSONObject etdataObjcet = new JSONObject();
            etdataObjcet.put("sdate",sDate);
            etdataObjcet.put("edate", eDate);

            etdataObjcet.put("coordinates", coordArray);

            //HttpEntity<String> requestBody = new HttpEntity<>(etdataObjcet.toString(), headers);



            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> httpEntity = new HttpEntity<>(etdataObjcet.toString(), headers);

            String Url = baseUrl+"/wd_data";
            newWD = restTemplate.postForObject(Url, httpEntity, String.class);

            System.out.println("WD parcel data : "+newWD);
            return newWD;
        }
        catch(HttpStatusCodeException he) {
            System.out.println("Caught Rest Template Exception: " + he.getResponseBodyAsString());
        }
        return  newWD;

    }

}
