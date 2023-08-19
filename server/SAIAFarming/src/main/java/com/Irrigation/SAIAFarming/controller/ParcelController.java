package com.Irrigation.SAIAFarming.controller;

import com.Irrigation.SAIAFarming.dto.ParcelDTO;
import com.Irrigation.SAIAFarming.entity.FarmDatabase;
import com.Irrigation.SAIAFarming.entity.ParcelDatabase;
import com.Irrigation.SAIAFarming.entity.usermanagement.UserRegistration;
import com.Irrigation.SAIAFarming.exception.CustomApplicationException;
import com.Irrigation.SAIAFarming.model.ResponseData;
import com.Irrigation.SAIAFarming.repository.UserRepository;
import com.Irrigation.SAIAFarming.services.ETService;
import com.Irrigation.SAIAFarming.utils.RequestContext;
import com.Irrigation.SAIAFarming.utils.ResponseCode;
import com.Irrigation.SAIAFarming.utils.Utils;
import com.SAIAFarm.SAIAFarm.ClientSaiaFarmApplication;
//import com.SAIAFarm.SAIAFarm.Response.SaiaFarmData;
import com.SAIAFarm.SAIAFarm.Response.SaiaFarmData;
import com.SAIAFarm.SAIAFarm.Response.SaiaiParcelData;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.wololo.geojson.Feature;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.aspectj.util.LangUtil.isEmpty;

@RestController
@Path("/api")
public class ParcelController extends BaseController{

    @Autowired
    private ETService etService;

    protected static final Log logger = LogFactory.getLog(ParcelController.class);
    @Autowired
    UserRepository saiaUserRepository;
    @PostMapping("/post_parceldata")

    public ResponseData PostMsqlData(@RequestBody List<Feature> featureList) throws Exception {

        final String reqID = UUID.randomUUID().toString();

        List<ParcelDatabase> parcelDetailList = new ArrayList<>();

        List<Feature> featureListReturned = new ArrayList<>();
        RequestContext.clear();
     //   SaiaiParcelData parcelData = null;
        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();

        for (Feature feature : featureList) {

            String userId = feature.getProperties().get("userId").toString();
            String farmId = feature.getProperties().get("farmId").toString();
            //String parcel_name = feature.getProperties().get("parcel_name").toString();
            //String location = feature.getGeometry().toString();
            String parcelName = feature.getProperties().get("parcelName").toString();

            String area = feature.getProperties().get("area").toString();
            String category = feature.getProperties().get("category").toString();
            String application = feature.getProperties().get("application").toString();
            String applicationEntityId = feature.getProperties().get("applicationEntityId").toString();
            String belongsTo = feature.getProperties().get("belongsTo").toString();
            String hasAgriCrop = feature.getProperties().get("hasAgriCrop").toString();
            String hasAgriSoil = feature.getProperties().get("hasAgriSoil").toString();
            String lastPlantedAt = feature.getProperties().get("lastPlantedAt").toString();
            String waterStressmean = feature.getProperties().get("waterStressmean").toString();
            String coordinates = feature.getGeometry().toString();

            System.out.println("check 1parcel");
            JSONObject jo = new JSONObject();
            jo.put("application", application);
            jo.put("applicationEntityId", applicationEntityId);
            System.out.println("relatedSource josn" +jo.toString());
            String relatedSource = jo.toString();

            if (isEmpty(userId)) {
                logger.warn("Invalid request param `userId`: " + userId);

                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_ID.toString());

            }
            //here checking if a user is valid user or not
            UserRegistration user_register_info = saiaUserRepository.findById(userId);
            if(user_register_info == null)
            {
                throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, ResponseCode.CLIENT_USER_ID_NOT_EXISTING.toString());
            }
//////////from here need to change response code please check

            if (isEmpty(parcelName)) {
                logger.warn("Invalid request param `parcel_name`: " + parcelName);
                /*throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);*/
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_PARCEL_NAME.toString());

            }

            if (isEmpty(area)) {
                logger.warn("Invalid request param `area`: " + area);
                /*throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);*/
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_CATEGORY.toString());

            }
            if (isEmpty(category)) {
                logger.warn("Invalid request param `farmName`: " + category);
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_NAME.toString());

            }
            if (isEmpty(application)) {
                logger.warn("Invalid request param `farm_type`: " + application);
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_TYPE.toString());

            }
            if (isEmpty(applicationEntityId)) {
                logger.warn("Invalid request param `farm_size`: " + applicationEntityId);
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_SIZE.toString());

            }

            //int userIdFarmNameCheck = CheckUserIdFarmName(userId,farmName);
            //if(userIdFarmNameCheck==Utils.OK){
                //ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
//                    farmData = dao.farmPostIntoDataBase(farmer_id,farmsizecategory, farmName,farm_type,farm_size,coordinates,userId);
                //farmData = dao.farmPostIntoDataBase(farmer_id,farmsizecategory, farmName,farm_type,farm_size,coordinates,userId,String.valueOf(farmaddress));
                //retData.put("data", farmData);
            //}


            int userIdParcelNameCheck = CheckUserIdParcelName(userId, parcelName);
            if(userIdParcelNameCheck== Utils.OK){
                ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
//SaiaiParcelData parcelPostIntoDataBase(String userId, String farm_id, String location, String area, String category, String relatedSource, String belongsTo, String hasAgriCrop, String hasAgriSoil, String lastPlantedAt, String waterStressmean)
                List<SaiaiParcelData> parcelData;
                parcelData = dao.parcelPostIntoDataBase(userId,farmId, coordinates,area,category,String.valueOf(relatedSource),belongsTo,hasAgriCrop,hasAgriSoil,lastPlantedAt,waterStressmean,parcelName);
                retData.put("data", parcelData);

            //}


        }}

        ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);
        return resData;
    }


    private int CheckUserIdParcelName(String userId, String parcelName) throws SQLException, ClassNotFoundException {

        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        List<SaiaiParcelData> userIdParcelNameExisting = dao.CheckParcelDataDuplicate(userId, parcelName);
        System.out.println("this is check"+userIdParcelNameExisting);

        if(userIdParcelNameExisting.isEmpty()){
            return Utils.OK;


        }
        else {
            throw new CustomApplicationException(HttpStatus.CONFLICT, ResponseCode.CLIENT_HAS_PARCEL_WITH_USER_ID.toString());
        }

    }
    @GetMapping("/parceldata")

    public ResponseData getparcel(@QueryParam(value = "parcelId") int parcelId) throws SQLException, ClassNotFoundException, ParseException {
        RequestContext.clear(); //clear pre-existing data
        // generating UUID for this new request
        final String reqID = UUID.randomUUID().toString();
        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        if(isEmpty(String.valueOf(parcelId))){
            logger.warn("Invalid request param `parcelId`: " + parcelId);
            //throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_ID);
        }


        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();

        List<SaiaiParcelData> parcelInfo = dao.SingleParcelData(parcelId);
        System.out.println(parcelInfo);
//        for(SaiaiParcelData oneSingleParcel: parcelInfo){
//            retSaiparcelData.add(oneSingleParcel);
//        }

        //Here we have to filter the data (use only desired fields)
        HashMap<String, Object> retData =new LinkedHashMap<>();
        retData.put("parcel_info", parcelInfo.get(0));
        RequestContext.clear();//clear the thread before return

        ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

        return responseData;
    }
/*
    @GetMapping("/parceldatauid")

    public ResponseData getparceluid(@QueryParam(value = "userId") String userId) throws SQLException, ClassNotFoundException, ParseException {
        RequestContext.clear(); //clear pre-existing data
        // generating UUID for this new request
        final String reqID = UUID.randomUUID().toString();
        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);
        System.out.println("asas");
        if(isEmpty(String.valueOf(userId))){
            logger.warn("Invalid request param `userId`: " + userId);
            System.out.println("UserId is empty returning all Parcel data");
            ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
            //dao.readDataBase();
            List<SaiaiParcelData> parcelData =  dao.ParcelData();
            //String something = new String(String.valueOf(dao.FarmData()));
            //String something = new String(String.valueOf(dao.Comment()));
            //return parcelData;
            //throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_ID);

            System.out.println(parcelData);
//        for(SaiaiParcelData oneSingleParcel: parcelInfo){
//            retSaiparcelData.add(oneSingleParcel);
//        }

            //Here we have to filter the data (use only desired fields)
            HashMap<String, Object> retData =new LinkedHashMap<>();
            retData.put("parcel_info", parcelData.get(0));
            RequestContext.clear();//clear the thread before return

            ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

            return responseData;


        }
        HashMap<String, Object> retData =new LinkedHashMap<>();

        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();

        List<SaiaiParcelData> parcelInfo = dao.SingleParcelData_uid(userId);
        System.out.println(parcelInfo);
//        for(SaiaiParcelData oneSingleParcel: parcelInfo){
//            retSaiparcelData.add(oneSingleParcel);
//        }

        //Here we have to filter the data (use only desired fields)
        if(parcelInfo.size()!=0)
            retData.put("parcel_info", parcelInfo.get(0));
        else
            System.out.println("Invalid User ID");
        RequestContext.clear();//clear the thread before return

        ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

        return responseData;
    }

*/
    @GetMapping("/get_parcelsdata")
    public List<SaiaiParcelData> StringData(@QueryParam(value = "userId") String userId ) throws Exception {
        RequestContext.clear(); //clear pre-existing data
        // generating UUID for this new request
        final String reqID = UUID.randomUUID().toString();
        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);
        System.out.println("asas");
        if(isEmpty(String.valueOf(userId)) || userId==null){
            logger.warn("Invalid request param `userId`: " + userId);
            System.out.println("UserId is empty returning all Parcel data");
            ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
            //dao.readDataBase();
            List<SaiaiParcelData> parcelData =  dao.ParcelData();
            //String something = new String(String.valueOf(dao.FarmData()));
            //String something = new String(String.valueOf(dao.Comment()));
            //return parcelData;
            //throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_ID);

            System.out.println(parcelData);
//        for(SaiaiParcelData oneSingleParcel: parcelInfo){
//            retSaiparcelData.add(oneSingleParcel);
//        }
            /*
            //Here we have to filter the data (use only desired fields)
            HashMap<String, Object> retData =new LinkedHashMap<>();
            retData.put("parcel_info", parcelData.get(0));
            RequestContext.clear();//clear the thread before return

            ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

            return responseData;
            */
            return parcelData;

        }
        HashMap<String, Object> retData =new LinkedHashMap<>();

        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();

        List<SaiaiParcelData> parcelInfo = dao.SingleParcelData_uid(userId);
        System.out.println(parcelInfo);
//        for(SaiaiParcelData oneSingleParcel: parcelInfo){
//            retSaiparcelData.add(oneSingleParcel);
//        }

        //Here we have to filter the data (use only desired fields)
        if(parcelInfo.size()!=0)
           // retData.put("parcel_info", parcelInfo.get(0));
            return parcelInfo;
        else
            System.out.println("Invalid User ID");
        /*
        RequestContext.clear();//clear the thread before return

        ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

        return responseData;
        System.out.println("I am here for farm data");
        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        //dao.readDataBase();
         //String something = new String(String.valueOf(dao.FarmData()));
        //String something = new String(String.valueOf(dao.Comment()));
        return parcelData;
          */
        return parcelInfo;
    }

    @PostMapping("/update_parcel")
    public ResponseData updateParcel(@RequestBody ParcelDTO parcelInfo) throws SQLException, ParseException, ClassNotFoundException, java.text.ParseException {
        final String reqID = UUID.randomUUID().toString();
        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);
        Integer parcelId = parcelInfo.getParcelId();

        String category = parcelInfo.getCategory();

        String hasAgriCrop=parcelInfo.getHasAgriCrop();
        String lastPlantedAt=parcelInfo.getLastPlantedAt();
        String parcelName=parcelInfo.getParcelName();
        //  String getLocation;


        if (logger.isInfoEnabled())
            logger.info("ParcelInfo data: " + parcelInfo);



        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();

        List<SaiaiParcelData> parcelDB = dao.SingleParcelData(parcelInfo.getParcelId());
       // lastPlantedAt=parcelDB.get(0).getLastPlantedAt();
       // getLocation=parcelinfo.get(0).getLocation();
        System.out.println(parcelInfo);
//        for(SaiaiParcelData oneSingleParcel: parcelInfo){
//            retSaiparcelData.add(oneSingleParcel);
//        }


        //retData.put("parcel_info", parcelInfo.get(0));
        RequestContext.clear();//clear the thread before return

        //  ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

        //return responseData;
        if (parcelId==null) {
            logger.warn("Invalid request param `Parcel ID`: " + parcelId);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME.toString());

        }




        if (isEmpty(parcelInfo.getCategory())) {
            category=parcelDB.get(0).getCategory();
        }

        if (isEmpty(parcelInfo.getHasAgriCrop())) {
            hasAgriCrop=parcelDB.get(0).getHasAgriCrop();
        }
       if (lastPlantedAt==null) {
            lastPlantedAt=parcelDB.get(0).getLastPlantedAt();

        }
//        if (getLocation==null) {
//            getLocation=parcelinfo.get(0).getLocation();
//        }

        if (isEmpty(parcelName)) {
            parcelName=parcelDB.get(0).getParcelName();

        }

        List<SaiaiParcelData> parcelData = dao.parcelUpdateDB(parcelId,category,hasAgriCrop,lastPlantedAt,parcelName);
        System.out.println(parcelData);

        HashMap<String, Object> retData =new LinkedHashMap<>();
        retData.put("parcel_info", parcelData.get(0));
        RequestContext.clear();//clear the thread before return

        ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

        return responseData;

    }
    @DeleteMapping("/delete_parcel")
    public ResponseData deleteParcel(@RequestParam(value = "parcelId") Integer parcelID) throws Exception {
        RequestContext.clear(); //clear pre-existing data
        // generating UUID for this new request
        final String reqID = UUID.randomUUID().toString();
        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        if(isEmpty(String.valueOf(parcelID))){
            logger.warn("Invalid request param `userId`: " + parcelID);
            System.out.println("ParcelName is empty. Please provide a valid parcel name.");

        }

        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();

        List<SaiaiParcelData> parcelInfo = dao.deleteParcel(parcelID);
        System.out.println(parcelInfo);




        HashMap<String, Object> retData =new LinkedHashMap<>();
        retData.put("parcel_info", parcelInfo.get(0));
        RequestContext.clear();//clear the thread before return

        ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

        return responseData;
    }

    @PostMapping("/et_parceldata")

    public ResponseData ETParcelData(@RequestBody List<Feature> featureList) throws Exception {

        final String reqID = UUID.randomUUID().toString();

        List<ParcelDatabase> parcelDetailList = new ArrayList<>();
        //List<FarmDatabase> farmsList = new ArrayList<>();
        List<Feature> featureListReturned = new ArrayList<>();
        RequestContext.clear();
        SaiaFarmData etparcelData = null;
        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();

        for (Feature feature : featureList) {
            String userId = feature.getProperties().get("userId").toString();
            String parcelName = feature.getProperties().get("parcelName").toString();
            String sdate = feature.getProperties().get("sdate").toString();
            String edate = feature.getProperties().get("edate").toString();



            if (isEmpty(userId)) {
                logger.warn("Invalid request param `userId`: " + userId);
                /*throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);*/
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_ID.toString());

            }
            if (isEmpty(parcelName)) {
                logger.warn("Invalid request param `parcelName`: " + parcelName);
                /*throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);*/
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_PARCEL_NAME.toString());

            }
            //here checking if a user is valid user or not
            UserRegistration user_register_info = saiaUserRepository.findById(userId);
            if(user_register_info == null)
            {
                throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, ResponseCode.CLIENT_USER_ID_NOT_EXISTING.toString());
            }

            int userIdParcelNameCheck = ETCheckUserIdParcelName(userId,parcelName);
            if(userIdParcelNameCheck==Utils.OK){
                ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();

                List<SaiaiParcelData> etparcelInfo = dao.ETParcelData(parcelName);

                String response = etService.getEtDataParcel(etparcelInfo, sdate, edate);


                //System.out.println("getting response"+response);



                retData.put("data", response);
            }


        }

        ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);
        return resData;


    }
    private int ETCheckUserIdParcelName(String userId, String parcelName) throws SQLException, ClassNotFoundException {

        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        List<SaiaiParcelData> userIdFarmNameExisting = dao.ETCheckParcelDataExist(userId, parcelName);


        if(userIdFarmNameExisting.isEmpty()){

            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_HAS_NO_PARCEL_WITH_USER_ID.toString());

        }
        else {
            return Utils.OK;
        }

    }

    @PostMapping("/wd_parceldata")

    public ResponseData WDParcelData(@RequestBody List<Feature> featureList) throws Exception {

        final String reqID = UUID.randomUUID().toString();

        List<ParcelDatabase> parcelDetailList = new ArrayList<>();
        //List<FarmDatabase> farmsList = new ArrayList<>();
        List<Feature> featureListReturned = new ArrayList<>();
        RequestContext.clear();
        SaiaFarmData wdparcelData = null;
        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();

        for (Feature feature : featureList) {
            String userId = feature.getProperties().get("userId").toString();
            String parcelName = feature.getProperties().get("parcelName").toString();
            String sdate = feature.getProperties().get("sdate").toString();
            String edate = feature.getProperties().get("edate").toString();



            if (isEmpty(userId)) {
                logger.warn("Invalid request param `userId`: " + userId);
                /*throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);*/
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_ID.toString());

            }
            if (isEmpty(parcelName)) {
                logger.warn("Invalid request param `parcelName`: " + parcelName);
                /*throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);*/
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_PARCEL_NAME.toString());

            }
            //here checking if a user is valid user or not
            UserRegistration user_register_info = saiaUserRepository.findById(userId);
            if(user_register_info == null)
            {
                throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, ResponseCode.CLIENT_USER_ID_NOT_EXISTING.toString());
            }

            int userIdParcelNameCheck = ETCheckUserIdParcelName(userId,parcelName);
            if(userIdParcelNameCheck==Utils.OK){
                ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();

                List<SaiaiParcelData> etparcelInfo = dao.ETParcelData(parcelName);

                String response = etService.getWdDataParcel(etparcelInfo, sdate, edate);


                //System.out.println("getting response"+response);



                retData.put("data", response);
            }


        }

        ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);
        return resData;


    }

}
