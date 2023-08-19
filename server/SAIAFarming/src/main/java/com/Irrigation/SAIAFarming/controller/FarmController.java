package com.Irrigation.SAIAFarming.controller;

import com.Irrigation.SAIAFarming.SampleRequest;
import com.Irrigation.SAIAFarming.entity.FarmDatabase;
import com.Irrigation.SAIAFarming.entity.usermanagement.UserRegistration;
import com.Irrigation.SAIAFarming.exception.CustomApplicationException;
import com.Irrigation.SAIAFarming.model.*;
//import com.Irrigation.SAIAFarming.model.SAIAFarmer;
import com.Irrigation.SAIAFarming.repository.FarmDatabaseRepository;
import com.Irrigation.SAIAFarming.repository.UserRepository;
import com.Irrigation.SAIAFarming.services.ETService;
import com.Irrigation.SAIAFarming.utils.RequestContext;
import com.Irrigation.SAIAFarming.utils.ResponseCode;
import com.Irrigation.SAIAFarming.utils.Utils;
import com.SAIAFarm.SAIAFarm.ClientSaiaFarmApplication;
import com.SAIAFarm.SAIAFarm.Response.SaiaFarmData;
import com.SAIAFarm.SAIAFarm.Response.SaiaFarmerData;
import com.vividsolutions.jts.io.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.wololo.geojson.Feature;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.*;

import static org.aspectj.util.LangUtil.isEmpty;


@RestController
@Path("/api")
public class FarmController extends BaseController {
    @Autowired
    private ETService etService;


    @Autowired
    FarmDatabaseRepository saiaFarmRepository;

    @Autowired
    UserRepository saiaUserRepository;

    // to set logger
    //protected static final Log logger = LogFactory.getLog(com.Irrigation.SAIAFarming.controller.FarmController.class);
    protected static final Log logger = LogFactory.getLog(FarmController.class);

    @Context
    Request request;

    //API will use to get all farm data (admin use only)
    @GetMapping("/get_farmsdata")
    public List<SaiaFarmData> StringData() throws Exception {

        System.out.println("I am here for farm data");
        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        //dao.readDataBase();
        List<SaiaFarmData> farmData =  dao.FarmData();
        String something = new String(String.valueOf(dao.FarmData()));
        //String something = new String(String.valueOf(dao.Comment()));
        return farmData;

    }



    //API to post farm data

    @PostMapping("/post_farmdata")

    public ResponseData PostMsqlData(@RequestBody List<Feature> featureList) throws Exception {

        final String reqID = UUID.randomUUID().toString();

        List<FarmDatabase> farmDetailList = new ArrayList<>();
        //List<FarmDatabase> farmsList = new ArrayList<>();
        List<Feature> featureListReturned = new ArrayList<>();
        RequestContext.clear();
        List<SaiaFarmData> farmData = null;
        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();

        for (Feature feature : featureList) {
            String farmer_id = feature.getProperties().get("farmer_id").toString();
            String userId = feature.getProperties().get("userId").toString();
            String farmsizecategory = feature.getProperties().get("farmsizecategory").toString();
            String farmName = feature.getProperties().get("farm_name").toString();
            String farm_type = feature.getProperties().get("farm_type").toString();
            String farm_size = feature.getProperties().get("farm_size").toString();
            String village = feature.getProperties().get("village").toString();
            String address_landmark = feature.getProperties().get("address_landmark").toString();
            String coordinates = feature.getGeometry().toString();


            JSONObject jo = new JSONObject();
            jo.put("village", village);
            jo.put("address_landmark", address_landmark);
            System.out.println("Address josn" +jo.toString());
            String farmaddress = jo.toString();

            if (isEmpty(userId)) {
                logger.warn("Invalid request param `userId`: " + userId);
                /*throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);*/
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_ID.toString());

            }
            //here checking if a user is valid user or not
            UserRegistration user_register_info = saiaUserRepository.findById(userId);
            if(user_register_info == null)
            {
                throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, ResponseCode.CLIENT_USER_ID_NOT_EXISTING.toString());
            }


            if (isEmpty(farmsizecategory)) {
                logger.warn("Invalid request param `farmsizecategory`: " + farmsizecategory);
                /*throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);*/
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_CATEGORY.toString());

            }
            if (isEmpty(farmName)) {
                logger.warn("Invalid request param `farmName`: " + farmName);
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_NAME.toString());

            }
            if (isEmpty(farm_type)) {
                logger.warn("Invalid request param `farm_type`: " + farm_type);
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_TYPE.toString());

            }
            if (isEmpty(farm_size)) {
                logger.warn("Invalid request param `farm_size`: " + farm_size);
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_SIZE.toString());

            }

                //int userIdFarmNameCheck = CheckUserIdFarmName(userId,farm_name,farmDetailList);

                //int userIdFarmNameCheck = CheckUserIdFarmName(userId,farm_name,featureList);
                int userIdFarmNameCheck = CheckUserIdFarmName(userId,farmName);
                if(userIdFarmNameCheck==Utils.OK){
                    ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
//                    farmData = dao.farmPostIntoDataBase(farmer_id,farmsizecategory, farmName,farm_type,farm_size,coordinates,userId);
                    farmData = dao.farmPostIntoDataBase(farmer_id,farmsizecategory, farmName,farm_type,farm_size,coordinates,userId,String.valueOf(farmaddress));
                    retData.clear();
                    retData.put("data", farmData);
                }

            //FarmDatabase farmDetail =

               // Feature addedFarmInfo = AddedFarmByUserIdFarmName(userId,farm_name,userIdFarmNameCheck,feature);
                //featureListReturned.add(addedFarmInfo);

                /*ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
                farmData = dao.farmPostIntoDataBase(farmer_id,farmsizecategory, farm_name,farm_type,farm_size,coordinates,userId);
                retData.put("data", farmData);*/
                //ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);
              //  return resData;
        }
        /*for(FarmDatabase farm:farmsList)
        {
            FarmDatabase farmInfo = saiaFarmRepository.save(farm);
            logger.info("farm is stored to db: "+farmInfo);
        }*/
        ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(),retData );
        return resData;


    }

    /*private int CheckUserIdFarmName(String userId, String farmName, List<Feature> featureList) {
        int x = 0;
        String tempUserId =
        for(FarmDatabase feature : featureList)
        {
            String tempUserId = feature.getProperties().get("userId").toString();
            String tempFarmName = feature.getProperties().get("farm_name").toString();
            UserRegistration user = saiaUserRepository.findById(tempUserId);
            //Optional<FarmDatabase> farm = Optional.ofNullable(saiaFarmRepository.findByFarmName(tempFarmName));
            //FarmDatabase farm = saiaFarmRepository.findByFarmName(tempFarmName);
            if(user.equals(Optional.empty()) ){

                return Utils.USERID_NOT_EXISTING;

            }
            if ((tempUserId.equals(userId)) && (tempFarmName.equals(farmName)))
                x++;
            System.out.println("check1");
            if(x>1)
                System.out.println("check2");
            return Utils.USERID_FARM_NAME_DUPLICATE;
        }
        return Utils.OK;
    }*/


    private int CheckUserIdFarmName(String userId, String farmName) throws SQLException, ClassNotFoundException {
        //int x = 0;

            //UserRegistration user = saiaUserRepository.findById(userId);
            //Optional<FarmDatabase> farm = Optional.ofNullable(saiaFarmRepository.findByFarmName(tempFarmName));
            //FarmDatabase farm = saiaFarmRepository.findByFarmName(farmName);
            ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
            List<SaiaFarmData> userIdFarmNameExisting = dao.CheckFarmDataDuplicate(userId, farmName);
            System.out.println("this is check"+userIdFarmNameExisting);
            //if(userIdFarmNameExisting.equals(Optional.empty()) ){
            if(userIdFarmNameExisting.isEmpty()){
                return Utils.OK;
                //return Utils.USERID_NOT_EXISTING;
                //throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, ResponseCode.CLIENT_USER_ID_NOT_EXISTING.toString());

            }
            else {
                throw new CustomApplicationException(HttpStatus.CONFLICT, ResponseCode.CLIENT_HAS_FARM_WITH_USER_ID.toString());
            }
            /*if (userIdFarmNameExisting != null)
            {
                //return Utils.USERID_FARM_NAME_DUPLICATE;
                throw new CustomApplicationException(HttpStatus.CONFLICT, ResponseCode.CLIENT_HAS_FARM_WITH_USER_ID.toString());
            }*/

        //return Utils.OK;
        //return
    }
    /*private int CheckUserIdFarmName(String userId, String farmName, List<Feature> featureList) {
        int x = 0;
        for(Feature feature : featureList)
        {
            String tempUserId = feature.getProperties().get("userId").toString();
            String tempFarmName = feature.getProperties().get("farm_name").toString();
            UserRegistration user = saiaUserRepository.findById(tempUserId);
            //Optional<FarmDatabase> farm = Optional.ofNullable(saiaFarmRepository.findByFarmName(tempFarmName));
            //FarmDatabase farm = saiaFarmRepository.findByFarmName(tempFarmName);
            if(user.equals(Optional.empty()) ){

                return Utils.USERID_NOT_EXISTING;

            }
            if ((tempUserId.equals(userId)) && (tempFarmName.equals(farmName)))
                x++;
                System.out.println("check1");
            if(x>1)
                System.out.println("check2");
                return Utils.USERID_FARM_NAME_DUPLICATE;
        }
        return Utils.OK;
    }*/


    /*private int CheckUserIdFarmName(String userId, String farmName, List<FarmDatabase> farmDetailList) {
        int x = 0;
        for(FarmDatabase farmDetail: farmDetailList)
        {
            String tempUserId = farmDetail.getuserId();
            String tempFarmName = farmDetail.getFarmName();
            Optional<UserRegistration> user = Optional.ofNullable(saiaUserRepository.findById(tempUserId));
            if(user.equals(Optional.empty()) ){
                return Utils.USERID_NOT_EXISTING;
            }
            if ((tempUserId.equals(userId)) && (tempFarmName.equals(farmName)))
                x++;
            if(x>1)
                return Utils.USERID_FARM_NAME_DUPLICATE;
        }
        return Utils.OK;
    }*/


    /*private Feature AddedFarmByUserIdFarmName(String userId, String farmName, int userIdFarmNameCheck, Feature farmDetail) {


        if (userIdFarmNameCheck != Utils.USERID_FARM_NAME_DUPLICATE) {
            if(userIdFarmNameCheck != Utils.USERID_NOT_EXISTING){
                FarmDatabase FarmIsExisting = saiaFarmRepository.findByUserIdAndFarmName(userId, farmName);
                if (FarmIsExisting == null) {
                    return farmDetail;
                }
                else{
                    logger.error("Farm name: " + farmName + " already existing for given user: " + userId);
                    throw new CustomApplicationException(HttpStatus.CONFLICT, ResponseCode.CLIENT_HAS_FARM_WITH_USER_ID.toString());
                }
            }
            else{
                logger.error( " given user does not exists: " + userId);
                throw new CustomApplicationException(HttpStatus.NOT_FOUND, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_ID.toString());
            }
        }
        else {
            logger.error("Land name: " + farmName + " repeating in request. Please provide different land names for each land");
            throw new CustomApplicationException(HttpStatus.CONFLICT, ResponseCode.CLIENT_REQ_FARM_WITH_USER_ID_IS_CONFLICT.toString());
        }
        //return farmDetail;
    }*/

    /*private FarmDatabase getAddedFarmByUserIdFarmName(String userId, String farmName, int userIdFarmNameCheck, FarmDatabase farmDetail) {

        if (userIdFarmNameCheck != Utils.USERID_FARM_NAME_DUPLICATE) {
            if(userIdFarmNameCheck != Utils.USERID_NOT_EXISTING){
                FarmDatabase FarmIsExisting = saiaFarmRepository.findByUserIdAndFarmName(userId, farmName);
                if (FarmIsExisting == null) {
                                        return farmDetail;
                }
                else{
                    logger.error("Farm name: " + farmName + " already existing for given user: " + userId);
                    throw new CustomApplicationException(HttpStatus.CONFLICT, ResponseCode.CLIENT_HAS_FARM_WITH_USER_ID.toString());

                }
            }
            else{
                logger.error( " given user does not exists: " + userId);
                throw new CustomApplicationException(HttpStatus.NOT_FOUND, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_ID.toString());
                
            }
        }
        else {
            logger.error("Land name: " + farmName + " repeating in request. Please provide different land names for each land");
            throw new CustomApplicationException(HttpStatus.CONFLICT, ResponseCode.CLIENT_REQ_FARM_WITH_USER_ID_IS_CONFLICT.toString());

        }
        //return farmDetail;
    }*/

    /*@POST
    @Path("/post_farmdata")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @SampleRequest("http://localhost:9099/api/post_farmdata")
    public ResponseData PostMsqlData(@RequestBody SAIAFarm farmDetail) throws Exception {


        // clear any pre-existing context
        RequestContext.clear();

        // generate UUID for this new request
        final String reqID = UUID.randomUUID().toString();

        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        //final MediaType mediaType = request.selectVariant(mtVariantList).getMediaType();


            *//*FarmerDatabase farmm = new FarmerDatabase();
            farmm.setFarmer_id(1);*//*


            String farmer_id = farmDetail.getFarmer_id();
            String farmsizecategory = farmDetail.getFarmsizecategory();
            String farm_name = farmDetail.getFarm_name();
            String farm_coordinates = farmDetail.getFarm_coordinates();
            String farm_type = farmDetail.getFarm_type();
            String farm_size = farmDetail.getFarm_size();

            //Date date = validateDateParam("s_date", datestr, ResponseCode.CLIENT_INVALID_REQ_PARAM_DATE);
            //System.out.println(date);
            //IrrigationDBClient dao = new IrrigationDBClient();
            //dao.readDataBase();
            if (isEmpty(farmsizecategory)) {
                logger.warn("Invalid request param `user`: " + farmsizecategory);

            }
            if (isEmpty(farmsizecategory)) {
                logger.warn("Invalid request param `user`: " + farmsizecategory);

            }
            ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
            /// SAIAFarmer farmerData = new SAIAFarmer(head_name,gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
            LinkedHashMap<String, Object> retData = new LinkedHashMap<>();


            // ResponseData resData = new ResponseData(reqID, ResponseCode.OK.getCode(), ResponseCode.SUCCESS.getCode(), retData);

            SaiaFarmData farmData = dao.farmPostIntoDataBase(farmer_id, farmsizecategory,farm_name, farm_coordinates, farm_type, farm_size);
            ///SAIAFarmer responseData = new SAIAFarmer(farmerData.getHead_name(), farmerData.getGender(), farmerData.getSon_wife_daughter(), farmerData.getLiteracy(), farmerData.getVillage(), farmerData.getFarmer_address_coordinates(), farmerData.getAddress_landmark());
            retData.put("data", farmData);

            ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);

            return resData;



    }*/

   /* @GET
    @Path("/farm_data")
    @Produces({MediaType.APPLICATION_JSON})
    @SampleRequest("http://localhost:9099/api/farm_data?farm_id=1")
    public ResponseData getfarm(@QueryParam(value = "farm_id") int farm_id){
        RequestContext.clear(); //clear pre-existing data
        // generating UUID for this new request
        final String reqID = UUID.randomUUID().toString();
        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        if(isEmpty(String.valueOf(farm_id))){
            logger.warn("Invalid request param `farm_id`: " + farm_id);
            //throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_ID);
        }

            //get the farm data from DB using farm_id, if farm_id is not present then gives error
            HashMap<String, Object> retData =new LinkedHashMap<>();
            //Optional<FarmDatabase> farmInfo = saiaFarmRepository.findById(farm_id);
            FarmDatabase farmInfo = saiaFarmRepository.findOne(farm_id);
            FarmDatabase farDetail = new FarmDatabase(farmInfo.getFarm_id(), farmInfo.getFarmName(), farmInfo.getFarm_coordinates(), farmInfo.getFarmsizecategory(), farmInfo.getFarm_type(), farmInfo.getFarm_size(), farmInfo.getAddress());
            //FarmDatabase farmInfo = saiaFarmRepository.findById(String.valueOf(farm_id)).get();
            if (farmInfo.equals(Optional.empty())){
                //throwErrorException(Response.Status.UNAUTHORIZED, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_ID);
            }
            //Here we have to filter the data (use only desired fields)

            retData.put("farm_info", farDetail);
            RequestContext.clear();//clear the thread before return
            //ResponseData responseData = new ResponseData(reqID, 200, 200, retData);
            ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

            return responseData;




    }*/
    @GetMapping("/farmdata")

    public ResponseData getfarm(@QueryParam(value = "farm_id") int farm_id) throws SQLException, ClassNotFoundException, ParseException {
    //public ResponseData getfarm(@QueryParam(value = "farm_id") int farm_id){
        RequestContext.clear(); //clear pre-existing data
        // generating UUID for this new request
        final String reqID = UUID.randomUUID().toString();
        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        if(isEmpty(String.valueOf(farm_id))){
            logger.warn("Invalid request param `farm_id`: " + farm_id);
            //throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_ID);
        }
        //get the farm data from DB using farm_id, if farm_id is not present then gives error
        HashMap<String, Object> retData =new LinkedHashMap<>();
        //FarmDatabase farmInfo = saiaFarmRepository.findOne(farm_id);
        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        List<SaiaFarmData> retSaifarmData = new ArrayList<>();
        List<SaiaFarmData> farmInfo = dao.SingleFarmData(farm_id);
        System.out.println(farmInfo);
        for(SaiaFarmData oneSingleFarm: farmInfo){
            retSaifarmData.add(oneSingleFarm);
        }

        //Here we have to filter the data (use only desired fields)

        retData.put("farm_info", farmInfo.get(0));
        RequestContext.clear();//clear the thread before return

        ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

        return responseData;
    }

    @GetMapping("/farmer_farm_data")

    public ResponseData getfarms(@QueryParam(value = "userId") String userId) throws SQLException, ClassNotFoundException, ParseException {
        //public ResponseData getfarm(@QueryParam(value = "farm_id") int farm_id){
        RequestContext.clear(); //clear pre-existing data
        // generating UUID for this new request
        final String reqID = UUID.randomUUID().toString();
        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        if(isEmpty(String.valueOf(userId))){
            logger.warn("Invalid request param `userId`: " + userId);
            //throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_ID);
        }
        //get the farm data from DB using farm_id, if farm_id is not present then gives error
        HashMap<String, Object> retData =new LinkedHashMap<>();
        //FarmDatabase farmInfo = saiaFarmRepository.findOne(farm_id);
        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        //List<SaiaFarmData> retSaifarmsData = new ArrayList<>();
        List<SaiaFarmData> farmsInfo = dao.FarmerFarmsData(userId);
        System.out.println(farmsInfo);
//        for(SaiaFarmData oneSingleFarm: farmsInfo){
//            retSaifarmsData.add(oneSingleFarm);
//        }

        //Here we have to filter the data (use only desired fields)

        retData.put("userId", farmsInfo);
        RequestContext.clear();//clear the thread before return

        ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

        return responseData;
    }


    @PostMapping("/et_farmdata")

    public ResponseData ETFarmData(@RequestBody List<Feature> featureList) throws Exception {

        final String reqID = UUID.randomUUID().toString();

        List<FarmDatabase> farmDetailList = new ArrayList<>();
        //List<FarmDatabase> farmsList = new ArrayList<>();
        List<Feature> featureListReturned = new ArrayList<>();
        RequestContext.clear();
        SaiaFarmData etfarmData = null;
        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();

        for (Feature feature : featureList) {
            String userId = feature.getProperties().get("userId").toString();
            String farmName = feature.getProperties().get("farm_name").toString();
            String sdate = feature.getProperties().get("sdate").toString();
            String edate = feature.getProperties().get("edate").toString();



            if (isEmpty(userId)) {
                logger.warn("Invalid request param `userId`: " + userId);
                /*throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);*/
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_ID.toString());

            }
            if (isEmpty(farmName)) {
                logger.warn("Invalid request param `farmName`: " + farmName);
                /*throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);*/
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARM_NAME.toString());

            }
            //here checking if a user is valid user or not
            UserRegistration user_register_info = saiaUserRepository.findById(userId);
            if(user_register_info == null)
            {
                throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, ResponseCode.CLIENT_USER_ID_NOT_EXISTING.toString());
            }

            int userIdFarmNameCheck = ETCheckUserIdFarmName(userId,farmName);
            if(userIdFarmNameCheck==Utils.OK){
                ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();

                List<SaiaFarmData> etfarmsInfo = dao.ETFarmsData(farmName);

                String response = etService.getEtDataFarm(etfarmsInfo, sdate, edate);


                //System.out.println("getting response"+response);



                retData.put("data", response);
            }


        }

        ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);
        return resData;


    }

    private int ETCheckUserIdFarmName(String userId, String farmName) throws SQLException, ClassNotFoundException {

        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        List<SaiaFarmData> userIdFarmNameExisting = dao.ETCheckFarmDataExist(userId, farmName);
        //System.out.println("this is check"+userIdFarmNameExisting);

        if(userIdFarmNameExisting.isEmpty()){

            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_HAS_NO_FARM_WITH_USER_ID.toString());

        }
        else {
            return Utils.OK;
        }

    }



}
