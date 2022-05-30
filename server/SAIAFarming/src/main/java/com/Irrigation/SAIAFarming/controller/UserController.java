package com.Irrigation.SAIAFarming.controller;


import com.Irrigation.SAIAFarming.SampleRequest;
import com.Irrigation.SAIAFarming.entity.usermanagement.UserLogin;
import com.Irrigation.SAIAFarming.entity.usermanagement.UserRegistration;
import com.Irrigation.SAIAFarming.exception.ErrorException;
import com.Irrigation.SAIAFarming.exception.RESTException;
import com.Irrigation.SAIAFarming.model.ResponseData;
import com.Irrigation.SAIAFarming.model.ResponseStatus;
import com.Irrigation.SAIAFarming.model.User;
import com.Irrigation.SAIAFarming.repository.UserRepository;
import com.Irrigation.SAIAFarming.utils.RequestContext;
import com.Irrigation.SAIAFarming.utils.ResponseCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
@Path("/api")
public class UserController extends BaseController{
    ResponseData responseData;

    @Autowired
    UserRepository saiaUserRepository;
    // to set logger
    protected static final Log logger = LogFactory.getLog(com.Irrigation.SAIAFarming.controller.UserController.class);

    @Context
    Request request;

    @POST
    @Path("/user/registration")
    @Produces({MediaType.APPLICATION_JSON})
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
            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_NAME);
        }
        if (isEmpty(password)) {
            logger.warn("Invalid request param `mobileNum`: " + password);
            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_MOBILE_NUM);
        }
        if (isEmpty(user_type)) {
            logger.warn("Invalid request param `emailId`: " + user_type);
            throwBadRequestException(ResponseCode.CLIENT_INVALID_REQ_PARAM_USER_TYPE);
        }

        // add reqId to request context for tracking
        RequestContext.add("reqID", reqID);

        try {

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
                throwErrorException(Response.Status.CONFLICT, ResponseCode.CLIENT_USER_MOBILE_EXISTING);
            }
            // clear up the thread local request context before we return
            RequestContext.clear();

            /*if (logger.isInfoEnabled())
                logger.info("Returning farmer data: " + responseData.toString(false));*/

            return responseData;
        } catch (RESTException re) {
            logger.error("REST Exception:" + re.getMessage(), re);
            throw re;

        }
        catch (ErrorException ee) {
            logger.error("Error Exception:" + ee.getMessage(), ee);
            throw ee;
        }
        catch (Exception e) {
            RequestContext.clear();
            logger.error("Internal Error. Reason:" + e.getMessage(), e);
            final ResponseStatus status = new ResponseStatus(reqID, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    ResponseCode.SERVER_INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.SERVER_INTERNAL_SERVER_ERROR.getText());
            throw new RESTException(Response.Status.INTERNAL_SERVER_ERROR, status);
        }
    }

    @POST
    @Path("/user/login")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @SampleRequest("http://localhost:9099/api/user/login")
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
        UserRegistration user = saiaUserRepository.findByid(userid);

        try{
            if(user == null){
                logger.warn("Invalid request param `userId`: " + userid);
                throwErrorException(Response.Status.UNAUTHORIZED, ResponseCode.CLIENT_INVALID_REQ_PARAM_USERID_PASSWORD);
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
                    throwErrorException(Response.Status.UNAUTHORIZED, ResponseCode.CLIENT_INVALID_REQ_PARAM_USERID_PASSWORD);
                }
            }
            // clear up the thread local request context before we return
            RequestContext.clear();
            if (logger.isInfoEnabled())
                logger.info("Returning user data: " + responseData.toString(false));
            return responseData;
        } catch (RESTException re) {
            logger.error("REST Exception:" + re.getMessage(), re);
            throw re;
        }catch (Exception e) {
            RequestContext.clear();
            logger.error("Internal Error. Reason:" + e.getMessage(), e);
            final ResponseStatus status = new ResponseStatus(reqID, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    ResponseCode.SERVER_INTERNAL_SERVER_ERROR.getCode(),
                    ResponseCode.SERVER_INTERNAL_SERVER_ERROR.getText());
            throw new RESTException(Response.Status.INTERNAL_SERVER_ERROR, status);
        }

            }




}