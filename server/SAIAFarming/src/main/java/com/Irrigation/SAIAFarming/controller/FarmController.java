package com.Irrigation.SAIAFarming.controller;

import com.Irrigation.SAIAFarming.SampleRequest;
import com.Irrigation.SAIAFarming.entity.FarmDatabase;
import com.Irrigation.SAIAFarming.model.ResponseData;
import com.Irrigation.SAIAFarming.model.ResponseStatus;
import com.Irrigation.SAIAFarming.model.SAIAFarm;
//import com.Irrigation.SAIAFarming.model.SAIAFarmer;
import com.Irrigation.SAIAFarming.repository.FarmDatabaseRepository;
import com.Irrigation.SAIAFarming.utils.RequestContext;
import com.Irrigation.SAIAFarming.utils.ResponseCode;
import com.SAIAFarm.SAIAFarm.ClientSaiaFarmApplication;
import com.SAIAFarm.SAIAFarm.Response.SaiaFarmData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.*;

import static org.aspectj.util.LangUtil.isEmpty;


@RestController
@Path("/api")
public class FarmController extends BaseController {


    @Autowired
    FarmDatabaseRepository saiaFarmRepository;

    // to set logger
    protected static final Log logger = LogFactory.getLog(com.Irrigation.SAIAFarming.controller.FarmController.class);


    @Context
    Request request;

    //API will use to get all farm data (admin use only)
    @GET
    @Path("/get_farmdata")
    @Produces({MediaType.APPLICATION_JSON})
    @SampleRequest("http://localhost:9099/api/get_farmdata")
    public ArrayList<SAIAFarm> StringData() throws Exception {

        System.out.println("I am here for farm data");
        ClientSaiaFarmApplication dao = new ClientSaiaFarmApplication();
        //dao.readDataBase();
        ArrayList farmData =  dao.FarmData();
        //String something = new String(String.valueOf(dao.Comment()));
        return farmData;

    }

    //API to post farm data
    @POST
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


            /*FarmerDatabase farmm = new FarmerDatabase();
            farmm.setFarmer_id(1);*/


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



    }

    @GET
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
            FarmDatabase farDetail = new FarmDatabase(farmInfo.getFarm_id(), farmInfo.getFarm_name(), farmInfo.getFarm_coordinates(), farmInfo.getFarmsizecategory(), farmInfo.getFarm_type(), farmInfo.getFarm_size());
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




    }
}
