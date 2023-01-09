package com.Irrigation.SAIAFarming.controller;

//import com.SAIAFarm.Irrigation.repository.DbImgesRepository;

//import model.Comment;

import com.Irrigation.SAIAFarming.SampleRequest;
//import com.Irrigation.SAIAFarming.entity.UserDatabase;
import com.Irrigation.SAIAFarming.entity.usermanagement.UserRegistration;
import com.Irrigation.SAIAFarming.exception.CustomApplicationException;
import com.Irrigation.SAIAFarming.model.*;
import com.Irrigation.SAIAFarming.repository.FarmerDatabaseRepository;
import com.Irrigation.SAIAFarming.repository.UserRepository;
import com.Irrigation.SAIAFarming.services.ETService;
import com.Irrigation.SAIAFarming.utils.RequestContext;
import com.Irrigation.SAIAFarming.utils.ResponseCode;
import com.Irrigation.SAIAFarming.utils.Utils;
import com.SAIAFarm.SAIAFarm.ClientSaiaFarmApplication;
import com.SAIAFarm.SAIAFarm.Response.SaiaFarmData;
import com.SAIAFarm.SAIAFarm.Response.SaiaFarmerData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vividsolutions.jts.io.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wololo.geojson.Feature;

//import static org.apache.logging.log4j.util.Strings.isEmpty;
import static org.aspectj.util.LangUtil.isEmpty;
//import static com.Irrigation.SAIAFarming.controller.BaseController.mtVariantList;

@RestController
@Path("/api")
public class FarmerController extends BaseController {


    @Autowired
    FarmerDatabaseRepository saiaFarmerRepository;
    @Autowired
    UserRepository saiaUserRepository;
    // to set logger
    protected static final Log logger = LogFactory.getLog(com.Irrigation.SAIAFarming.controller.FarmerController.class);


    @Context
    Request request;

    //    @Autowired
//    Request request;
//this api is to show data not to post in DB
    @POST
    @Path("/post_comment")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @SampleRequest("http://localhost:9099/api/post_comment")
    public ResponseData PostMsqlData(@RequestBody CommentModel comment) throws Exception {


        // clear any pre-existing context
        RequestContext.clear();

        // generate UUID for this new request
        final String reqID = UUID.randomUUID().toString();

        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        //final MediaType mediaType = request.selectVariant(mtVariantList).getMediaType();


            String user = comment.getUser();
            String email = comment.getEmail();
            String website = comment.getWebsite();
            String datestr = comment.getDate();
            String summary = comment.getSummary();
            String commentVal = comment.getComment();
            //Date date = validateDateParam("s_date", datestr, ResponseCode.CLIENT_INVALID_REQ_PARAM_DATE);
            //System.out.println(date);
            //IrrigationDBClient dao = new IrrigationDBClient();
            //dao.readDataBase();
            if (isEmpty(user)) {
                logger.warn("Invalid request param `user`: " + user);
               // throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);
            }
            CommentModel commentData = new CommentModel(user, email, website, datestr, summary, commentVal);
            LinkedHashMap<String, Object> retData = new LinkedHashMap<>();

            retData.put("data", commentData);

            // ResponseData resData = new ResponseData(reqID, ResponseCode.OK.getCode(), ResponseCode.SUCCESS.getCode(), retData);

            ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);

            /* CommentModel responseData = new CommentModel(com*//*mentData.getUser(), commentData.getEmail(), commentData.getWebsite(), datestr,
                commentData.getSummary(), commentData.getComment());*/

            //String something = new String(String.valueOf(dao.Comment()));
            return resData;



    }


    @GetMapping("/get_farmersdata")

    public ArrayList<SAIAFarmer> StringData() throws Exception {

        System.out.println("I am here 1");
        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        //dao.readDataBase();
        ArrayList farmerData = dao.FarmerData();

        String something = new String(String.valueOf(dao.FarmerData()));
        //System.out.println(something);
        return farmerData;

    }

    
    

    /*@GET
    @Path("/farmer_data")
    @Produces({MediaType.APPLICATION_JSON})
    @SampleRequest("http://localhost:9099/api/farmer_data?farmer_id=1")
    public ResponseData getfarmer(@QueryParam(value = "farmer_id") int farmer_id) {
        RequestContext.clear(); //clear pre-existing data
        // generating UUID for this new request
        final String reqID = UUID.randomUUID().toString();
        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        if (isEmpty(String.valueOf(farmer_id))) {
            logger.warn("Invalid request param `farmer_id`: " + farmer_id);
            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_FARMER_ID);
        }
        try {
            //get the farmer data from DB using farmer_id, if farmer_id is not present then gives error
            HashMap<String, Object> retData = new LinkedHashMap<>();
            FarmerDatabase farmerInfo = saiaFarmerRepository.findById(farmer_id).get();
            if (farmerInfo.equals(Optional.empty())) {
                throwErrorException(Response.Status.UNAUTHORIZED, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARMER_ID);
            }
            retData.put("farmer_info", farmerInfo);
            RequestContext.clear();//clear the thread before return
            //ResponseData responseData = new ResponseData(reqID, 200, 200, retData);
            ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

            return responseData;

        } catch (RESTException re) {
            logger.error("Rest Exception: " + re.getMessage(), re);
            throw re;
        } catch (Exception e) {
            RequestContext.clear();
            logger.error("Internal Error" + e.getMessage(), e);
            final ResponseStatus status = new ResponseStatus(reqID, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    ResponseCode.SERVER_INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.SERVER_INTERNAL_SERVER_ERROR.getText());
            throw new RESTException(Response.Status.INTERNAL_SERVER_ERROR, status);
        }
    }*/


    @GetMapping("/farmerdata")

    public ArrayList<SAIAFarmer> getfarmer(@QueryParam(value = "farmer_id") int farmer_id) throws SQLException, ClassNotFoundException, ParseException {

        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        //dao.readDataBase();
        ArrayList farmerDetail = dao.SingleFarmerDetail(farmer_id);

        //String something = new String(String.valueOf(dao.FarmerData()));
        //System.out.println(something);
        return farmerDetail;

    }
    
    
    /*@GET
    @Path("/farmer_data/jpa/")
    @Produces({MediaType.APPLICATION_JSON})
    @SampleRequest("http://localhost:9099/api/farmer_data?farmer_id=1")
    public ResponseData getfarmer(@QueryParam(value = "farmer_id") int farmer_id) {
        RequestContext.clear(); //clear pre-existing data
        // generating UUID for this new request
        final String reqID = UUID.randomUUID().toString();
        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        if (isEmpty(String.valueOf(farmer_id))) {
            logger.warn("Invalid request param `farmer_id`: " + farmer_id);
            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_FARMER_ID);
        }
        try {
            //get the farmer data from DB using farmer_id, if farmer_id is not present then gives error
            HashMap<String, Object> retData = new LinkedHashMap<>();
            //com.vividsolutions.jts.geom.Geometry farmerInfo = saiaFarmerRepository.findByFarmerId(farmer_id).getFarmer_address_coordinates();
            FarmerDatabase farmerInfo = saiaFarmerRepository.findById(farmer_id).get();
            FarmerDatabase farmerDetails = new FarmerDatabase(farmerInfo.getHead_name(), farmerInfo.getGender(), farmerInfo.getSon_wife_daughter(), farmerInfo.getLiteracy(), farmerInfo.getVillage(), farmerInfo.getAddress_landmark(), farmerInfo.getFarmer_address_coordinates());

            if (farmerInfo.equals(Optional.empty())) {
                throwErrorException(Response.Status.UNAUTHORIZED, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARMER_ID);
            }
            retData.put("farmer_info", farmerDetails);
            RequestContext.clear();//clear the thread before return
            //ResponseData responseData = new ResponseData(reqID, 200, 200, retData);
            ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

            return responseData;

        } catch (RESTException re) {
            logger.error("Rest Exception: " + re.getMessage(), re);
            throw re;
        } catch (Exception e) {
            RequestContext.clear();
            logger.error("Internal Error" + e.getMessage(), e);
            final ResponseStatus status = new ResponseStatus(reqID, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    ResponseCode.SERVER_INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.SERVER_INTERNAL_SERVER_ERROR.getText());
            throw new RESTException(Response.Status.INTERNAL_SERVER_ERROR, status);
        }
    }
*/

   /* @POST
    @Path("/post_farmerdata")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @SampleRequest("http://localhost:9099/api/post_farmerdata")
    public ResponseData PostMsqlData(@RequestBody SAIAFarmer farmerDetail) throws Exception {

        String user_id = farmerDetail.getUser_id();

        String head_name = farmerDetail.getHead_name();
        String gender = farmerDetail.getGender();
        String son_wife_daughter = farmerDetail.getSon_wife_daughter();
        String literacy = farmerDetail.getLiteracy();
        String village = farmerDetail.getVillage();
        String farmer_address_coordinates = farmerDetail.getFarmer_address_coordinates();
        String address_landmark = farmerDetail.getAddress_landmark();

        //Date date = validateDateParam("s_date", datestr, ResponseCode.CLIENT_INVALID_REQ_PARAM_DATE);
        //System.out.println(date);
        //IrrigationDBClient dao = new IrrigationDBClient();
        //dao.readDataBase();
        if (isEmpty(head_name)) {
            logger.warn("Invalid request param `head_name`: " + head_name);
            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);
        }
        if (isEmpty(gender)) {
            logger.warn("Invalid request param `gender`: " + gender);
            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);
        }
        if (isEmpty(son_wife_daughter)) {
            logger.warn("Invalid request param `son_wife_daughter`: " + son_wife_daughter);
            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_RELATION);
        }
        if (isEmpty(literacy)) {
            logger.warn("Invalid request param `literacy`: " + literacy);
            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_LITERACY);
        }
        if (isEmpty(village)) {
            logger.warn("Invalid request param `village`: " + village);
            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_VILLAGE_NAME);
        }
        if (isEmpty(farmer_address_coordinates)) {
            logger.warn("Invalid request param `farmer_address_coordinates`: " + farmer_address_coordinates);
            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_ADDRESS_COORDINATES);
        }


        // clear any pre-existing context
        RequestContext.clear();

        // generate UUID for this new request
        final String reqID = UUID.randomUUID().toString();

        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        //final MediaType mediaType = request.selectVariant(mtVariantList).getMediaType();
        try {
            LinkedHashMap<String, Object> retData = new LinkedHashMap<>();

            Optional<UserRegistration> user_register_info = saiaUserRepository.findById(user_id);
            SaiaFarmerData farmerData = null;
            if (user_register_info.equals(Optional.empty())) {
                throwErrorException(Response.Status.UNAUTHORIZED, ResponseCode.CLIENT_USER_ID_NOT_EXISTING);
            } else {

//            FarmerDatabase farmm = new FarmerDatabase();
//            farmm.setFarmer_id(1);


                ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
                /// SAIAFarmer farmerData = new SAIAFarmer(head_name,gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
                *//*LinkedHashMap<String, Object> retData = new LinkedHashMap<>();*//*


                // ResponseData resData = new ResponseData(reqID, ResponseCode.OK.getCode(), ResponseCode.SUCCESS.getCode(), retData);

                farmerData = dao.farmerPostIntoDataBase(head_name, gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
                ///SAIAFarmer responseData = new SAIAFarmer(farmerData.getHead_name(), farmerData.getGender(), farmerData.getSon_wife_daughter(), farmerData.getLiteracy(), farmerData.getVillage(), farmerData.getFarmer_address_coordinates(), farmerData.getAddress_landmark());

            }
            retData.put("data", farmerData);


            ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);

            *//* CommentModel responseData = new CommentModel(com*//**//*mentData.getUser(), commentData.getEmail(), commentData.getWebsite(), datestr,
                commentData.getSummary(), commentData.getComment());*//*

            //String something = new String(String.valueOf(dao.Comment()));
            return resData;

        }
        catch (RESTException re) {
            logger.error("REST Exception:" + re.getMessage(), re);
            throw re;
        } catch (Exception e) {
            RequestContext.clear();
            logger.error("Internal Error. Reason:" + e.getMessage(), e);
            final ResponseStatus status = new ResponseStatus(reqID, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    ResponseCode.SERVER_INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.SERVER_INTERNAL_SERVER_ERROR.getText());
            throw new RESTException(Response.Status.INTERNAL_SERVER_ERROR,MediaType.APPLICATION_JSON.toString(), status);
        }
        //return  status;

    }*/


/*    @PostMapping("/post_farmerdata")

    public ResponseData PostMsqlData(@RequestBody Feature feature) throws Exception {

        final String reqID = UUID.randomUUID().toString();

//        String head_name = fullDetails.getOtherDetails().getHead_name();
//        String gender = fullDetails.getOtherDetails().getGender();
//        Geometry farmer_address_coordinates = fullDetails.getCoordinates().getCoordinates();

        //String user_id = "anch4545";
        String user_id = feature.getProperties().get("user_id").toString();

        String head_name = feature.getProperties().get("head_name").toString();
        String gender = feature.getProperties().get("gender").toString();
        String son_wife_daughter = feature.getProperties().get("son_wife_daughter").toString();
        String literacy = feature.getProperties().get("literacy").toString();
        String village = feature.getProperties().get("village").toString();   /////TBD: why only village?
        String address_landmark = feature.getProperties().get("address_landmark").toString();
        //String coordinates = feature.getProperties().get("farmer_address_coordinates").toString();
        //Geometry coordinates = feature.getGeometry();
        String coordinates = feature.getGeometry().toString();

        //String new_coordinates = Json.Pa;
//            try{
//                JSONObject object = new JSONObject(coordinates.toString()); ///////////to parse the GeoJson
//                String key = object.getString("type");
//                //JsonArray array = (JsonArray) object.get("coordinates");
//            }catch (JSONException jsonException){
//
//            }
        if (isEmpty(head_name)) {
            logger.warn("Invalid request param `head_name`: " + head_name);
//            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME.toString());

        }
        if (isEmpty(gender)) {
            logger.warn("Invalid request param `gender`: " + gender);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_GENDER.toString());

        }
        if (isEmpty(son_wife_daughter)) {
            logger.warn("Invalid request param `son_wife_daughter`: " + son_wife_daughter); ////TBD:what does it contains? relationship from whome to whome?
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_RELATION.toString());

        }
        if (isEmpty(literacy)) {
            logger.warn("Invalid request param `literacy`: " + literacy);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_LITERACY.toString());

        }
        if (isEmpty(village)) {
            logger.warn("Invalid request param `village`: " + village);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_VILLAGE_NAME.toString());

        }
//        if (isEmpty(feature.getProperties())) {
//            logger.warn("Invalid request param `farmer_address_coordinates`: " + coordinates);
//            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_ADDRESS_COORDINATES.toString());
//        }

        //String head_name = "abc";
        //String gender = farmerDetail.getGender();
        //String son_wife_daughter = "ram";
        //String literacy = "10";
        //String village = "kamand";
        //String farmer_address_coordinates = farmerDetail.getFarmer_address_coordinates();
        //String address_landmark = "north";

        //String son_wife_daughter = farmerDetail.getSon_wife_daughter();


        //Date date = validateDateParam("s_date", datestr, ResponseCode.CLIENT_INVALID_REQ_PARAM_DATE);
        //System.out.println(date);
        //IrrigationDBClient dao = new IrrigationDBClient();
        //dao.readDataBase();


        // clear any pre-existing context
        RequestContext.clear();


        // generate UUID for this new request
        //final String reqID = UUID.randomUUID().toString();

        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

            LinkedHashMap<String, Object> retData = new LinkedHashMap<>();

            UserRegistration user_register_info = saiaUserRepository.findById(user_id);
            SaiaFarmerData farmerData = null;
            if (user_register_info == null) {
                throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, ResponseCode.CLIENT_USER_ID_NOT_EXISTING.toString());
            } else {

                ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
                /// SAIAFarmer farmerData = new SAIAFarmer(head_name,gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
                //LinkedHashMap<String, Object> retData = new LinkedHashMap<>();


                // ResponseData resData = new ResponseData(reqID, ResponseCode.OK.getCode(), ResponseCode.SUCCESS.getCode(), retData);

                //comment to work with json farmerData = dao.farmerPostIntoDataBase(head_name, gender, son_wife_daughter, literacy, village, address_landmark, coordinates);
                ///SAIAFarmer responseData = new SAIAFarmer(farmerData.getHead_name(), farmerData.getGender(), farmerData.getSon_wife_daughter(), farmerData.getLiteracy(), farmerData.getVillage(), farmerData.getFarmer_address_coordinates(), farmerData.getAddress_landmark());

            }

            //final MediaType mediaType = request.selectVariant(mtVariantList).getMediaType();

            //SaiaFarmerData farmerData = null;


//            FarmerDatabase farmm = new FarmerDatabase();
//            farmm.setFarmer_id(1);


            //ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
            /// SAIAFarmer farmerData = new SAIAFarmer(head_name,gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
//            LinkedHashMap<String, Object> retData = new LinkedHashMap<>();


            // ResponseData resData = new ResponseData(reqID, ResponseCode.OK.getCode(), ResponseCode.SUCCESS.getCode(), retData);

            //       farmerData = dao.farmerPostIntoDataBase(head_name, gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
            ///SAIAFarmer responseData = new SAIAFarmer(farmerData.getHead_name(), farmerData.getGender(), farmerData.getSon_wife_daughter(), farmerData.getLiteracy(), farmerData.getVillage(), farmerData.getFarmer_address_coordinates(), farmerData.getAddress_landmark());


            //HashMap<String, Object> retData = new HashMap<>();
            retData.put("data", farmerData);


            ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);

//             CommentModel responseData = new CommentModel(commentData.getUser(), commentData.getEmail(), commentData.getWebsite(), datestr,
//                commentData.getSummary(), commentData.getComment());

            //String something = new String(String.valueOf(dao.Comment()));
            return resData;




    }*/
    //Converting to NGSI-V2 Format
    @PostMapping("/post_farmerdata")

    public ResponseData PostMsqlData(@RequestBody Feature feature) throws Exception {

        final String reqID = UUID.randomUUID().toString();

       /* String head_name = fullDetails.getOtherDetails().getHead_name();
        String gender = fullDetails.getOtherDetails().getGender();
        Geometry farmer_address_coordinates = fullDetails.getCoordinates().getCoordinates();*/

        //String user_id = "anch4545";
        String user_id = feature.getProperties().get("user_id").toString();

        String head_name = feature.getProperties().get("head_name").toString();
        String gender = feature.getProperties().get("gender").toString();
        String son_wife_daughter = feature.getProperties().get("son_wife_daughter").toString();
        String literacy = feature.getProperties().get("literacy").toString();
        String village = feature.getProperties().get("village").toString();
        String address_landmark = feature.getProperties().get("address_landmark").toString();
        //String coordinates = feature.getProperties().get("farmer_address_coordinates").toString();
        //Geometry coordinates = feature.getGeometry();
        String coordinates = feature.getGeometry().toString();
        /*Address address = new Address();
        String address_landmark1 = address.getAddress_landmark();
        String village1 = address.getVillage();*/

        /*String jsonString = new JSONObject()
                .put("user", user_id)
                .put("head_name", head_name)
                .put("son_wife_daughter", son_wife_daughter)
                .put("literacy", literacy)
                .put("village", village)
                .put("address_landmark", address_landmark)
                .put("coordinates", coordinates)
                .toString();*/

//        Address reqAdd = new Address();
//        reqAdd.setVillage(village);
//        reqAdd.setAddress_landmark(address_landmark);
//        reqAdd.toString();
//        Gson gson = new Gson();

        /*String address_json = new JSONObject()
                .put("type", "AgriFarm")

                .put("address", new JSONObject().put("village", village).put("address_landmark",address_landmark))
                .toString();*/

        JSONObject jo = new JSONObject();
        jo.put("village", village);
        jo.put("address_landmark", address_landmark);
        System.out.println("Address josn" +jo.toString());
        String address = jo.toString();
        //Address address = gson.fromJson(jo.toString(), Address.class);

        /*String address_json = new JSONObject()
                .put("address", new JSONObject().put("village", village).put("address_landmark",address_landmark))
                .toString();*/
        /*String address_json = new JSONObject()
                .put("address", new JSONObject().put("village", village).put("address_landmark",address_landmark))
                .toString();*/

        /*String jsonString = new JSONObject()
                .put("JSON1", "Hello World!")
                .put("JSON2", "Hello my World!")
                .put("JSON3", new JSONObject().put("key1", "value1"))
                .toString();*/
        System.out.println(address);
        //System.out.println(jsonString);
        //System.out.println(address_json);


        //String new_coordinates = Json.Pa;
            /*try{
                JSONObject object = new JSONObject(coordinates.toString()); ///////////to parse the GeoJson
                String key = object.getString("type");
                //JsonArray array = (JsonArray) object.get("coordinates");
            }catch (JSONException jsonException){

            }*/
        if (isEmpty(head_name)) {
            logger.warn("Invalid request param `head_name`: " + head_name);
            /*throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);*/
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME.toString());

        }
        if (isEmpty(gender)) {
            logger.warn("Invalid request param `gender`: " + gender);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_GENDER.toString());

        }
        if (isEmpty(son_wife_daughter)) {
            logger.warn("Invalid request param `son_wife_daughter`: " + son_wife_daughter);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_RELATION.toString());

        }
        if (isEmpty(literacy)) {
            logger.warn("Invalid request param `literacy`: " + literacy);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_LITERACY.toString());

        }
        if (isEmpty(village)) {
            logger.warn("Invalid request param `village`: " + village);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_VILLAGE_NAME.toString());

        }
        /*if (isEmpty(feature.getProperties())) {
            logger.warn("Invalid request param `farmer_address_coordinates`: " + coordinates);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_ADDRESS_COORDINATES.toString());
        }*/

        //String head_name = "abc";
        //String gender = farmerDetail.getGender();
        //String son_wife_daughter = "ram";
        //String literacy = "10";
        //String village = "kamand";
        //String farmer_address_coordinates = farmerDetail.getFarmer_address_coordinates();
        //String address_landmark = "north";

        //String son_wife_daughter = farmerDetail.getSon_wife_daughter();


        //Date date = validateDateParam("s_date", datestr, ResponseCode.CLIENT_INVALID_REQ_PARAM_DATE);
        //System.out.println(date);
        //IrrigationDBClient dao = new IrrigationDBClient();
        //dao.readDataBase();


        // clear any pre-existing context
        RequestContext.clear();


        // generate UUID for this new request
        //final String reqID = UUID.randomUUID().toString();

        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();

        UserRegistration user_register_info = saiaUserRepository.findById(user_id);
        SaiaFarmerData farmerData = null;
        //String farmerData = "notnull";

        //int CheckDupFarmer = CheckDupFarmer(user_id);
//        if(CheckDupFarmer==Utils.OK){
//            ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
//            farmData = dao.farmPostIntoDataBase(farmer_id,farmsizecategory, farmName,farm_type,farm_size,coordinates,userId);
//            retData.put("data", farmData);
//        }

        if (user_register_info == null) {
            throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, ResponseCode.CLIENT_USER_ID_NOT_EXISTING.toString());
        } else {
            int CheckDupFarmer = CheckDupFarmer(user_id);
        if(CheckDupFarmer==Utils.OK) {

            ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
            /// SAIAFarmer farmerData = new SAIAFarmer(head_name,gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
            //*LinkedHashMap<String, Object> retData = new LinkedHashMap<>();*//*


            // ResponseData resData = new ResponseData(reqID, ResponseCode.OK.getCode(), ResponseCode.SUCCESS.getCode(), retData);


            farmerData = dao.farmerPostIntoDataBase(user_id, head_name, gender, son_wife_daughter, literacy, coordinates, String.valueOf(address));
            ///SAIAFarmer responseData = new SAIAFarmer(farmerData.getHead_name(), farmerData.getGender(), farmerData.getSon_wife_daughter(), farmerData.getLiteracy(), farmerData.getVillage(), farmerData.getFarmer_address_coordinates(), farmerData.getAddress_landmark());
            retData.put("data", farmerData);

        }}

        //final MediaType mediaType = request.selectVariant(mtVariantList).getMediaType();

        //SaiaFarmerData farmerData = null;


//            FarmerDatabase farmm = new FarmerDatabase();
//            farmm.setFarmer_id(1);


        //ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        /// SAIAFarmer farmerData = new SAIAFarmer(head_name,gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
        /*LinkedHashMap<String, Object> retData = new LinkedHashMap<>();*/


        // ResponseData resData = new ResponseData(reqID, ResponseCode.OK.getCode(), ResponseCode.SUCCESS.getCode(), retData);

        //       farmerData = dao.farmerPostIntoDataBase(head_name, gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
        ///SAIAFarmer responseData = new SAIAFarmer(farmerData.getHead_name(), farmerData.getGender(), farmerData.getSon_wife_daughter(), farmerData.getLiteracy(), farmerData.getVillage(), farmerData.getFarmer_address_coordinates(), farmerData.getAddress_landmark());


        //HashMap<String, Object> retData = new HashMap<>();
        //retData.put("data", farmerData);
        //retData.put("address", addressString);

        ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);

         /*CommentModel responseData = new CommentModel(commentData.getUser(), commentData.getEmail(), commentData.getWebsite(), datestr,
                commentData.getSummary(), commentData.getComment());*/

        //String something = new String(String.valueOf(dao.Comment()));
        return resData;
        //return addressString;




    }

    private int CheckDupFarmer(String user_id) throws SQLException, ClassNotFoundException, ParseException {

        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        List<SaiaFarmerData> FarmerExisting = dao.CheckDupFarmer(user_id);
        System.out.println("this is check"+FarmerExisting);

        if(FarmerExisting.isEmpty()){
            return Utils.OK;

        }
        else {
            throw new CustomApplicationException(HttpStatus.CONFLICT, ResponseCode.CLIENT_HAS_FARMER_WITH_USER_ID.toString());
        }

    }
    /*@PostMapping("/postv2_farmerdata")

    public ResponseData PostMsqlDatav2(@RequestBody Feature feature) throws Exception {

        final String reqID = UUID.randomUUID().toString();

//        String head_name = fullDetails.getOtherDetails().getHead_name();
//        String gender = fullDetails.getOtherDetails().getGender();
//        Geometry farmer_address_coordinates = fullDetails.getCoordinates().getCoordinates();

        //String user_id = "anch4545";
        String user_id = feature.getProperties().get("user_id").toString();

        String head_name = feature.getProperties().get("head_name").toString();
        String gender = feature.getProperties().get("gender").toString();
        String son_wife_daughter = feature.getProperties().get("son_wife_daughter").toString();
        String literacy = feature.getProperties().get("literacy").toString();
        String village = feature.getProperties().get("village").toString();
        String address_landmark = feature.getProperties().get("address_landmark").toString();
        //String coordinates = feature.getProperties().get("farmer_address_coordinates").toString();
        //Geometry coordinates = feature.getGeometry();
        String coordinates = feature.getGeometry().toString();
        //String coordinate_type = feature.getProperties().get("coordinate_type").toString();
        //String coordinate = feature.getProperties().get("coordinate").toString();
//        Address address = new Address();
//        String address_landmark1 = address.getAddress_landmark();
//        String village1 = address.getVillage();

//        String jsonString = new JSONObject()
//                .put("user", user_id)
//                .put("head_name", head_name)
//                .put("son_wife_daughter", son_wife_daughter)
//                .put("literacy", literacy)
//                .put("village", village)
//                .put("address_landmark", address_landmark)
//                .put("coordinates", coordinates)
//                .toString();

//        Address reqAdd = new Address();
//        reqAdd.setVillage(village);
//        reqAdd.setAddress_landmark(address_landmark);
//        reqAdd.toString();
//        Gson gson = new Gson();

//        String address_json = new JSONObject()
//                .put("type", "AgriFarm")
//
//                .put("address", new JSONObject().put("village", village).put("address_landmark",address_landmark))
//                .toString();

        JSONObject jo = new JSONObject();
        jo.put("village", village);
        jo.put("address_landmark", address_landmark);
        System.out.println("Address josn" +jo.toString());
        String address = jo.toString();
        //Address address = gson.fromJson(jo.toString(), Address.class);

//        JSONObject coord =new JSONObject();
//        coord.put("coordinate_type", coordinate_type);
//        coord.put("coordinate", coordinate);
//        String coordinates =coord.toString();

//        String address_json = new JSONObject()
//                .put("address", new JSONObject().put("village", village).put("address_landmark",address_landmark))
//                .toString();
//        String address_json = new JSONObject()
//                .put("address", new JSONObject().put("village", village).put("address_landmark",address_landmark))
//                .toString();

//        String jsonString = new JSONObject()
//                .put("JSON1", "Hello World!")
//                .put("JSON2", "Hello my World!")
//                .put("JSON3", new JSONObject().put("key1", "value1"))
//                .toString();
        System.out.println(address);
        //System.out.println(jsonString);
        //System.out.println(address_json);


        //String new_coordinates = Json.Pa;
//            try{
//                JSONObject object = new JSONObject(coordinates.toString()); ///////////to parse the GeoJson
//                String key = object.getString("type");
//                //JsonArray array = (JsonArray) object.get("coordinates");
//            }catch (JSONException jsonException){
//
//            }
        if (isEmpty(head_name)) {
            logger.warn("Invalid request param `head_name`: " + head_name);
            *//*throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);*//*
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME.toString());

        }
        if (isEmpty(gender)) {
            logger.warn("Invalid request param `gender`: " + gender);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_GENDER.toString());

        }
        if (isEmpty(son_wife_daughter)) {
            logger.warn("Invalid request param `son_wife_daughter`: " + son_wife_daughter);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_RELATION.toString());

        }
        if (isEmpty(literacy)) {
            logger.warn("Invalid request param `literacy`: " + literacy);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_LITERACY.toString());

        }
        if (isEmpty(village)) {
            logger.warn("Invalid request param `village`: " + village);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_VILLAGE_NAME.toString());

        }
//        if (isEmpty(feature.getProperties())) {
//            logger.warn("Invalid request param `farmer_address_coordinates`: " + coordinates);
//            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_ADDRESS_COORDINATES.toString());
//        }

        //String head_name = "abc";
        //String gender = farmerDetail.getGender();
        //String son_wife_daughter = "ram";
        //String literacy = "10";
        //String village = "kamand";
        //String farmer_address_coordinates = farmerDetail.getFarmer_address_coordinates();
        //String address_landmark = "north";

        //String son_wife_daughter = farmerDetail.getSon_wife_daughter();


        //Date date = validateDateParam("s_date", datestr, ResponseCode.CLIENT_INVALID_REQ_PARAM_DATE);
        //System.out.println(date);
        //IrrigationDBClient dao = new IrrigationDBClient();
        //dao.readDataBase();


        // clear any pre-existing context
        RequestContext.clear();


        // generate UUID for this new request
        //final String reqID = UUID.randomUUID().toString();

        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();

        UserRegistration user_register_info = saiaUserRepository.findById(user_id);
        SaiaFarmerData farmerData = null;
        //String farmerData = "notnull";

        if (user_register_info == null) {
            throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, ResponseCode.CLIENT_USER_ID_NOT_EXISTING.toString());
        } else {

            ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
            /// SAIAFarmer farmerData = new SAIAFarmer(head_name,gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
//            LinkedHashMap<String, Object> retData = new LinkedHashMap<>();*


            // ResponseData resData = new ResponseData(reqID, ResponseCode.OK.getCode(), ResponseCode.SUCCESS.getCode(), retData);


            farmerData = dao.farmerPostIntoDataBase(head_name, gender, son_wife_daughter, literacy, coordinates, String.valueOf(address));
            ///SAIAFarmer responseData = new SAIAFarmer(farmerData.getHead_name(), farmerData.getGender(), farmerData.getSon_wife_daughter(), farmerData.getLiteracy(), farmerData.getVillage(), farmerData.getFarmer_address_coordinates(), farmerData.getAddress_landmark());

        }

        //final MediaType mediaType = request.selectVariant(mtVariantList).getMediaType();

        //SaiaFarmerData farmerData = null;


//            FarmerDatabase farmm = new FarmerDatabase();
//            farmm.setFarmer_id(1);


        //ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        /// SAIAFarmer farmerData = new SAIAFarmer(head_name,gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
//        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();


        // ResponseData resData = new ResponseData(reqID, ResponseCode.OK.getCode(), ResponseCode.SUCCESS.getCode(), retData);

        //       farmerData = dao.farmerPostIntoDataBase(head_name, gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
        ///SAIAFarmer responseData = new SAIAFarmer(farmerData.getHead_name(), farmerData.getGender(), farmerData.getSon_wife_daughter(), farmerData.getLiteracy(), farmerData.getVillage(), farmerData.getFarmer_address_coordinates(), farmerData.getAddress_landmark());


        //HashMap<String, Object> retData = new HashMap<>();
        retData.put("data", farmerData);
        //retData.put("address", addressString);

        ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);

//         CommentModel responseData = new CommentModel(commentData.getUser(), commentData.getEmail(), commentData.getWebsite(), datestr,
//                commentData.getSummary(), commentData.getComment());

        //String something = new String(String.valueOf(dao.Comment()));
        return resData;





    }*/


}
