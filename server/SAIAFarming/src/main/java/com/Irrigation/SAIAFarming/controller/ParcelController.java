package com.Irrigation.SAIAFarming.controller;

import com.Irrigation.SAIAFarming.entity.ParcelDatabase;
import com.Irrigation.SAIAFarming.entity.usermanagement.UserRegistration;
import com.Irrigation.SAIAFarming.exception.CustomApplicationException;
import com.Irrigation.SAIAFarming.model.ResponseData;
import com.Irrigation.SAIAFarming.repository.UserRepository;
import com.Irrigation.SAIAFarming.utils.RequestContext;
import com.Irrigation.SAIAFarming.utils.ResponseCode;
import com.Irrigation.SAIAFarming.utils.Utils;
import com.SAIAFarm.SAIAFarm.ClientSaiaFarmApplication;
//import com.SAIAFarm.SAIAFarm.Response.SaiaFarmData;
import com.SAIAFarm.SAIAFarm.Response.SaiaFarmData;
import com.SAIAFarm.SAIAFarm.Response.SaiaiParcelData;
import com.vividsolutions.jts.io.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.wololo.geojson.Feature;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.*;

import static org.aspectj.util.LangUtil.isEmpty;

@RestController
@Path("/api")
public class ParcelController extends BaseController{

    protected static final Log logger = LogFactory.getLog(ParcelController.class);
    @Autowired
    UserRepository saiaUserRepository;
    @PostMapping("/post_parceldata")

    public ResponseData PostMsqlData(@RequestBody List<Feature> featureList) throws Exception {

        final String reqID = UUID.randomUUID().toString();

        List<ParcelDatabase> parcelDetailList = new ArrayList<>();

        List<Feature> featureListReturned = new ArrayList<>();
        RequestContext.clear();
        SaiaiParcelData parcelData = null;
        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();

        for (Feature feature : featureList) {

            String userId = feature.getProperties().get("userId").toString();
            String farmId = feature.getProperties().get("farmId").toString();
            //String parcel_name = feature.getProperties().get("parcel_name").toString();
            //String location = feature.getGeometry().toString();

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


            //int userIdFarmNameCheck = CheckUserIdParcelName(userId);
            //if(userIdFarmNameCheck== Utils.OK){
                ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
//SaiaiParcelData parcelPostIntoDataBase(String userId, String farm_id, String location, String area, String category, String relatedSource, String belongsTo, String hasAgriCrop, String hasAgriSoil, String lastPlantedAt, String waterStressmean)
                parcelData = dao.parcelPostIntoDataBase(userId,farmId, coordinates,area,category,String.valueOf(relatedSource),belongsTo,hasAgriCrop,hasAgriSoil,lastPlantedAt,waterStressmean);
                retData.put("data", parcelData);
            //}


        }

        ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);
        return resData;


    }

    /*private int CheckUserIdParcelName(String userId) throws SQLException, ClassNotFoundException {

        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        List<SaiaiParcelData> userIdParcelNameExisting = dao.CheckParcelDataDuplicate(userId);
        System.out.println("this is check"+userIdParcelNameExisting);

        if(userIdParcelNameExisting.isEmpty()){
            return Utils.OK;


        }
        else {
            throw new CustomApplicationException(HttpStatus.CONFLICT, ResponseCode.CLIENT_HAS_FARM_WITH_USER_ID.toString());
        }

    }*/
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
        HashMap<String, Object> retData =new LinkedHashMap<>();

        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();

        List<SaiaiParcelData> parcelInfo = dao.SingleParcelData(parcelId);
        System.out.println(parcelInfo);
//        for(SaiaiParcelData oneSingleParcel: parcelInfo){
//            retSaiparcelData.add(oneSingleParcel);
//        }

        //Here we have to filter the data (use only desired fields)

        retData.put("parcel_info", parcelInfo.get(0));
        RequestContext.clear();//clear the thread before return

        ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

        return responseData;
    }

    @GetMapping("/get_parcelsdata")
    public List<SaiaiParcelData> StringData() throws Exception {

        System.out.println("I am here for farm data");
        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        //dao.readDataBase();
        List<SaiaiParcelData> parcelData =  dao.ParcelData();
        //String something = new String(String.valueOf(dao.FarmData()));
        //String something = new String(String.valueOf(dao.Comment()));
        return parcelData;

    }
}
