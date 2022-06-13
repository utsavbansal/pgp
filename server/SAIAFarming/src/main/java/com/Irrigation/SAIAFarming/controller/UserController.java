package com.Irrigation.SAIAFarming.controller;


import com.Irrigation.SAIAFarming.SampleRequest;
import com.Irrigation.SAIAFarming.entity.usermanagement.UserLogin;
import com.Irrigation.SAIAFarming.entity.usermanagement.UserRegistration;
import com.Irrigation.SAIAFarming.exception.CustomApplicationException;
import com.Irrigation.SAIAFarming.exception.CustomizedExceptionHandling;
import com.Irrigation.SAIAFarming.model.ResponseData;
import com.Irrigation.SAIAFarming.model.ResponseStatus;
import com.Irrigation.SAIAFarming.model.User;
import com.Irrigation.SAIAFarming.repository.UserRepository;
import com.Irrigation.SAIAFarming.utils.RequestContext;
import com.Irrigation.SAIAFarming.utils.ResponseCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.*;

import static org.aspectj.util.LangUtil.isEmpty;

@RestController

public class UserController extends BaseController{
    ResponseData responseData;

    @Autowired
    UserRepository saiaUserRepository;
    // to set logger
    protected static final Log logger = LogFactory.getLog(com.Irrigation.SAIAFarming.controller.UserController.class);

    @Context
    Request request;

    @GetMapping("/exc/{id}")
    public String getTeacherDetails(@PathVariable("id") int id){
        if(id == 1)
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "Id not found");

        return "good";
    }
/*    @POST
    @Path("/user/registration")
    //@Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @SampleRequest("http://localhost:9099/api/user/registration")
    public ResponseData createUser(@RequestBody UserRegistration user) {
        String user_name = user.getUsername();
        String password = user.getPassword();
        String user_type = user.getUsertype();
        String mobile_num = user.getMobilenum();


        RequestContext.clear();
        // generate UUID for this new request
        final String reqID = UUID.randomUUID().toString();

        if (logger.isInfoEnabled())
            logger.info("Input User data: " + user);


        if (isEmpty(user_name)) {
            logger.warn("Invalid request param `userName`: " + user_name);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "empty user name");

        }
        if (isEmpty(password)) {
            logger.warn("Invalid request param `mobileNum`: " + password);

        }
        if (isEmpty(user_type)) {
            logger.warn("Invalid request param `emailId`: " + user_type);

        }

        // add reqId to request context for tracking
        RequestContext.add("reqID", reqID);



        HashMap<String, Object> retData = new LinkedHashMap<>();
        UserRegistration mobilenumData = saiaUserRepository.findBymobilenum(mobile_num);

        if (mobilenumData == null) {
            user.setId();
            UserRegistration userRegistration = saiaUserRepository.save(user);
            List<User> userInfo = new ArrayList<>();
            //User userRet = new User(userRegistration.getId(), userRegistration.getUsername(), userRegistration.getMobilenum(), userRegistration.getUsertype());
            userInfo.add(new User(userRegistration.getId(), userRegistration.getUsername(), userRegistration.getMobilenum(), userRegistration.getUsertype()));
            //userInfo.add(userRet);
            retData.put("user", userInfo);
            responseData = new ResponseData(reqID, Response.Status.CREATED.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);
        } else {
            //throwErrorException(Response.Status.CONFLICT, ResponseCode.CLIENT_USER_MOBILE_EXISTING);
        }
        // clear up the thread local request context before we return
        RequestContext.clear();

            *//*if (logger.isInfoEnabled())
                logger.info("Returning farmer data: " + responseData.toString(false));*//*

        return responseData;


    }*/

    @PostMapping("/login")
    public ResponseData logInByuserId(@RequestBody UserLogin userLogin) {
        String userid = userLogin.getUserid();
        String password = userLogin.getPassword();
        RequestContext.clear();

        // generate UUID for this new request
        final String reqID = UUID.randomUUID().toString();

        // update reqId for Request Context tracking
        RequestContext.add("reqID", reqID);

        HashMap<String, Object> retData = new LinkedHashMap<>();
        if (logger.isInfoEnabled())
            logger.info("Input data: " + "user_id: " + userid);

        // Check if user existing in DB, using user_id as key
        UserRegistration user = saiaUserRepository.findById(userid);


        if(user == null){
            logger.warn("Invalid request param `userId`: " + userid);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME.toString());
        }
        else {
            String hashed = user.getPassword();
            boolean userpass = BCrypt.checkpw(password, hashed);
            if (userpass){
                List<User> userInfo = new ArrayList<>();
                userInfo.add(new User(user.getId(), user.getUsername(), user.getMobilenum(), user.getUsertype()));
                retData.put("user", userInfo);
                responseData = new ResponseData(reqID, Response.Status.ACCEPTED.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);

            }
            else {
                // Incorrect password
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USERID_PASSWORD.toString().toString());
            }
        }
        // clear up the thread local request context before we return
        RequestContext.clear();
        if (logger.isInfoEnabled())
            logger.info("Returning user data: " + responseData.toString(false));
        return responseData;



    }

    @PostMapping("/user/registration")
    public ResponseData createUser(@RequestBody UserRegistration user) {
        String user_name = user.getUsername();
        String password = user.getPassword();
        String user_type = user.getUsertype();
        String mobile_num = user.getMobilenum();


        RequestContext.clear();
        // generate UUID for this new request
        final String reqID = UUID.randomUUID().toString();

        if (logger.isInfoEnabled())
            logger.info("Input User data: " + user);


        if (isEmpty(user_name)) {
            logger.warn("Invalid request param `userName`: " + user_name);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME.toString());

        }
        // exception handle for runtime exception
        //throw new CustomApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "500  - Internal server error");

        if (isEmpty(password)) {
            logger.warn("Invalid request param `password`: " + password);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_USERID_PASSWORD.toString());

        }
        if (isEmpty(user_type)) {
            logger.warn("Invalid request param `usertype`: " + user_type);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST,ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_TYPE.toString());

        }
        int length = mobile_num.length();
        if (isEmpty(mobile_num) || length<10 || length>10) {
            logger.warn("Invalid request param `mobilenum`: " + mobile_num);
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, ResponseCode.CLIENT_INVALID_REQ_PARAM_MOBILE_NUM.toString());

        }




        // add reqId to request context for tracking
        RequestContext.add("reqID", reqID);



            HashMap<String, Object> retData = new LinkedHashMap<>();
            UserRegistration mobilenumData = saiaUserRepository.findBymobilenum(mobile_num);
            //System.out.println(mobilenumData);

            if (mobilenumData == null) {
                user.setId();
                UserRegistration userRegistration = saiaUserRepository.save(user);
                List<User> userInfo = new ArrayList<>();
                //User userRet = new User(userRegistration.getId(), userRegistration.getUsername(), userRegistration.getMobilenum(), userRegistration.getUsertype());
                userInfo.add(new User(userRegistration.getId(), userRegistration.getUsername(), userRegistration.getMobilenum(), userRegistration.getUsertype()));
                //userInfo.add(userRet);
                retData.put("user", userInfo);
                responseData = new ResponseData(reqID, Response.Status.CREATED.getStatusCode(), ResponseCode.SUCCESS.getCode(), retData);
            } else {
                throw new CustomApplicationException(HttpStatus.BAD_REQUEST,ResponseCode.CLIENT_USER_MOBILE_EXISTING.toString());
            }
            // clear up the thread local request context before we return
            RequestContext.clear();

            /*if (logger.isInfoEnabled())
                logger.info("Returning farmer data: " + responseData.toString(false));*/

            return responseData;


    }






}