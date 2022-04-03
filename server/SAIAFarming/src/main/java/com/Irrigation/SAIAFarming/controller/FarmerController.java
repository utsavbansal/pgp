package com.Irrigation.SAIAFarming.controller;

//import com.SAIAFarm.Irrigation.repository.DbImgesRepository;

//import model.Comment;

import com.Irrigation.SAIAFarming.SampleRequest;
//import com.Irrigation.SAIAFarming.entity.UserDatabase;
import com.Irrigation.SAIAFarming.entity.FarmerDatabase;
import com.Irrigation.SAIAFarming.exception.RESTException;
import com.Irrigation.SAIAFarming.model.CommentModel;
import com.Irrigation.SAIAFarming.model.ResponseData;
import com.Irrigation.SAIAFarming.model.ResponseStatus;
import com.Irrigation.SAIAFarming.model.SAIAFarmer;
import com.Irrigation.SAIAFarming.repository.FarmerDatabaseRepository;
import com.Irrigation.SAIAFarming.utils.ResponseCode;
import com.SAIAFarm.SAIAFarm.ClientSaiaFarmApplication;
import com.SAIAFarm.SAIAFarm.Response.SaiaFarmerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static org.apache.logging.log4j.util.Strings.isEmpty;
//import static com.Irrigation.SAIAFarming.controller.BaseController.mtVariantList;

@RestController
@Path("/api")
public class FarmerController extends BaseController{


    @Autowired
    FarmerDatabaseRepository saiaFarmerRepository;
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
        try{

        String user = comment.getUser();
        String email = comment.getEmail();
        String website = comment.getWebsite();
        String datestr = comment.getDate();
        String summary = comment.getSummary();
        String commentVal = comment.getComment();
        Date date = validateDateParam("s_date", datestr, ResponseCode.CLIENT_INVALID_REQ_PARAM_DATE);
        System.out.println(date);
        //IrrigationDBClient dao = new IrrigationDBClient();
        //dao.readDataBase();
            if (isEmpty(user)) {
                logger.warn("Invalid request param `user`: " + user);
                throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);
            }
       CommentModel commentData = new CommentModel(user,email, website, datestr, summary, commentVal);
        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();

        retData.put("data", commentData);

       // ResponseData resData = new ResponseData(reqID, ResponseCode.OK.getCode(), ResponseCode.SUCCESS.getCode(), retData);

        ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);

       /* CommentModel responseData = new CommentModel(com*//*mentData.getUser(), commentData.getEmail(), commentData.getWebsite(), datestr,
                commentData.getSummary(), commentData.getComment());*/

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

    }


    @GET
    @Path("/get_farmersdata")
    @Produces({MediaType.APPLICATION_JSON})
    @SampleRequest("http://localhost:9099/api/get_farmersdata")
    public ArrayList<SAIAFarmer> StringData() throws Exception {

        System.out.println("I am here 1");
        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        //dao.readDataBase();
        ArrayList farmerData =  dao.FarmerData();
        //String something = new String(String.valueOf(dao.Comment()));
        return farmerData;

    }

    @GET
    @Path("/farmer_data")
    @Produces({MediaType.APPLICATION_JSON})
    @SampleRequest("http://localhost:9099/api/farmer_data?farmer_id=1")
    public ResponseData getfarmer(@QueryParam(value = "farmer_id") int farmer_id){
        RequestContext.clear(); //clear pre-existing data
        // generating UUID for this new request
        final String reqID = UUID.randomUUID().toString();
        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        if(isEmpty(String.valueOf(farmer_id))){
            logger.warn("Invalid request param `farmer_id`: " + farmer_id);
            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_FARMER_ID);
        }
        try{
            //get the farmer data from DB using farmer_id, if farmer_id is not present then gives error
            HashMap<String, Object> retData =new LinkedHashMap<>();
            FarmerDatabase farmerInfo = saiaFarmerRepository.findById(farmer_id).get();
            if (farmerInfo.equals(Optional.empty())){
                throwErrorException(Response.Status.UNAUTHORIZED, ResponseCode.CLIENT_INVALID_REQ_PARAM_FARMER_ID);
            }
            retData.put("farmer_info", farmerInfo);
            RequestContext.clear();//clear the thread before return
            //ResponseData responseData = new ResponseData(reqID, 200, 200, retData);
            ResponseData responseData = new ResponseData(reqID, Response.Status.OK.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

            return responseData;

        }
        catch (RESTException re){
            logger.error("Rest Exception: " + re.getMessage(), re);
            throw re;
        }
        catch (Exception e)
        {
            RequestContext.clear();
            logger.error("Internal Error" + e.getMessage(), e);
            final ResponseStatus status = new ResponseStatus(reqID, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    ResponseCode.SERVER_INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.SERVER_INTERNAL_SERVER_ERROR.getText());
            throw new RESTException(Response.Status.INTERNAL_SERVER_ERROR, status);
        }
    }


    @POST
    @Path("/post_farmerdata")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @SampleRequest("http://localhost:9099/api/post_comment_final")
    public ResponseData PostMsqlData(@RequestBody SAIAFarmer farmerDetail) throws Exception {


        // clear any pre-existing context
        RequestContext.clear();

        // generate UUID for this new request
        final String reqID = UUID.randomUUID().toString();

        // update RequestContext for tracking with reqID
        RequestContext.add("reqID", reqID);

        //final MediaType mediaType = request.selectVariant(mtVariantList).getMediaType();
        try{

//            FarmerDatabase farmm = new FarmerDatabase();
//            farmm.setFarmer_id(1);


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
                logger.warn("Invalid request param `user`: " + head_name);
                throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);
            }
            ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
           /// SAIAFarmer farmerData = new SAIAFarmer(head_name,gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
            LinkedHashMap<String, Object> retData = new LinkedHashMap<>();


            // ResponseData resData = new ResponseData(reqID, ResponseCode.OK.getCode(), ResponseCode.SUCCESS.getCode(), retData);

            SaiaFarmerData farmerData = dao.farmerPostIntoDataBase(head_name,gender, son_wife_daughter, literacy, village, farmer_address_coordinates, address_landmark);
            ///SAIAFarmer responseData = new SAIAFarmer(farmerData.getHead_name(), farmerData.getGender(), farmerData.getSon_wife_daughter(), farmerData.getLiteracy(), farmerData.getVillage(), farmerData.getFarmer_address_coordinates(), farmerData.getAddress_landmark());
            retData.put("data", farmerData);

            ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);

            /* CommentModel responseData = new CommentModel(com*//*mentData.getUser(), commentData.getEmail(), commentData.getWebsite(), datestr,
                commentData.getSummary(), commentData.getComment());*/

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

    }


}
