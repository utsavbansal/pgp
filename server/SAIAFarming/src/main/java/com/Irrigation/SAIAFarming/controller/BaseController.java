package com.Irrigation.SAIAFarming.controller;

import com.Irrigation.SAIAFarming.exception.RESTException;
import com.Irrigation.SAIAFarming.model.ResponseStatus;
import com.Irrigation.SAIAFarming.utils.RequestContext;
import com.Irrigation.SAIAFarming.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import com.Irrigation.SAIAFarming.model.ResponseData;
import com.Irrigation.SAIAFarming.utils.ResponseCode;
import org.springframework.context.MessageSource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;


public abstract class BaseController {
    @Autowired
    MessageSource messageSource;

//    private static final MediaType[] supportedMediaTypes =
//            {MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_XML_TYPE};
//    protected static final List<Variant> mtVariantList = getVariantList(supportedMediaTypes);
//
//    static List<Variant> getVariantList(MediaType[] mt) {
//        Variant.VariantListBuilder b = Variant.VariantListBuilder.newInstance();
//        for (MediaType t : mt) {
//            b.mediaTypes(t).add();
//        }
//        return b.build();
//    }

    protected void throwBadRequestException(ResponseCode responseCode) {
        ResponseStatus status =
                new ResponseStatus(RequestContext.getString("reqID"), Response.Status.BAD_REQUEST.getStatusCode(),
                        responseCode.getCode(), responseCode.getText());
        throw new RESTException(Response.Status.BAD_REQUEST, MediaType.APPLICATION_JSON.toString(), status);
    }


    protected void throwErrorException(Response.Status httpStatus, ResponseCode responseCode) {

        ResponseData status =
                new ResponseData(RequestContext.getString("reqID"), httpStatus.getStatusCode(),
                        responseCode.getCode(), responseCode.getText());
        throw new RESTException(httpStatus,  MediaType.APPLICATION_JSON.toString(), String.valueOf(status));

    }
    protected Date validateDateParam(String name, String value,
                                     ResponseCode responseCode) {
        Date date = Utils.formatDateStr(value);
        if (date == null) {
            //logger.warn("Invalid request param of date type: ["+name+"]");
            throwBadRequestException(responseCode);
        }
        return date;
    }

}
