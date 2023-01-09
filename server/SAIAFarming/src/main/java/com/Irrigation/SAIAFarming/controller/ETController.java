package com.Irrigation.SAIAFarming.controller;

//import com.SAIAFarm.Irrigation.repository.DbImgesRepository;

//import model.Comment;

//import com.Irrigation.SAIAFarming.entity.UserDatabase;
import com.Irrigation.SAIAFarming.exception.CustomApplicationException;
import com.Irrigation.SAIAFarming.model.*;
        import com.Irrigation.SAIAFarming.services.ETService;
        import com.Irrigation.SAIAFarming.utils.ResponseCode;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
        import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
        import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
        import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import static org.apache.logging.log4j.util.Strings.isEmpty;
import static org.aspectj.util.LangUtil.isEmpty;
//import static com.Irrigation.SAIAFarming.controller.BaseController.mtVariantList;
@Service
@RestController
@Path("/api")
public class ETController extends BaseController {



    // to set logger
    protected static final Log logger = LogFactory.getLog(com.Irrigation.SAIAFarming.controller.FarmerController.class);



    @Context
    Request request;

    @Autowired
    private ETService etService;
    @PostMapping("/et_data")
    public ResponseData getEtData(@RequestBody ETModel et) throws Exception {
        String modelRetData = etService.getEtData(et);
        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();
        final String reqID = UUID.randomUUID().toString();

        if (modelRetData.equalsIgnoreCase("")) {
            throw new CustomApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.SERVER_INTERNAL_SERVER_ERROR.toString());
        } else {
            retData.put("data", modelRetData);
            ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);
            return resData;

        }

    }

    @PostMapping("/et_image_data")
    public ResponseData getEtImageData(@RequestBody ETModel et)  throws Exception{
        String modelRetData = etService.getEtImageData(et);
        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();
        final String reqID = UUID.randomUUID().toString();

        if (modelRetData.equalsIgnoreCase("")) {
            throw new CustomApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.SERVER_INTERNAL_SERVER_ERROR.toString());
        } else {
            retData.put("data", modelRetData);
            ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);
            return resData;

        }
    }

    @PostMapping("/wd_data")
    public ResponseData getWdData(@RequestBody ETModel wd) throws Exception {
        String modelRetData = etService.getWdData(wd);
        LinkedHashMap<String, Object> retData = new LinkedHashMap<>();
        final String reqID = UUID.randomUUID().toString();

        if (modelRetData.equalsIgnoreCase("")) {
            throw new CustomApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.SERVER_INTERNAL_SERVER_ERROR.toString());
        } else {
            retData.put("data", modelRetData);
            ResponseData resData = new ResponseData(reqID, ResponseCode.SUCCESS.getCode(), Response.Status.OK.getStatusCode(), retData);
            return resData;

        }

    }

}
