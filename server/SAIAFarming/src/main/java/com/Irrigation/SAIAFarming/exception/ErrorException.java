package com.Irrigation.SAIAFarming.exception;

import com.Irrigation.SAIAFarming.model.ResponseStatus;
import com.Irrigation.SAIAFarming.utils.RequestContext;
import com.Irrigation.SAIAFarming.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ErrorException extends WebApplicationException {

    @Autowired
    MessageSource messageSource;

    ErrorException(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    public ErrorException() {
        this(Response.Status.SERVICE_UNAVAILABLE, MediaType.APPLICATION_JSON, "Bad Request");
    }
    public ErrorException(Response.Status status, String type, String message) {
        super(Response.status(status).type(type).entity(message).build());
    }

    public ErrorException(Response.Status status, String type, ResponseStatus message) {
        super(Response.status(status).type(type).entity(message).build());
    }
    public void throwErrorException(MediaType mediaType, Response.Status httpStatus, ResponseCode responseCode, MessageSource messageSource) {
        ResponseStatus status =
                new ResponseStatus(RequestContext.getString("reqID"), httpStatus.getStatusCode(),
                        responseCode.getCode(), getActualMessage(responseCode.getText(),messageSource));
        throw new ErrorException(httpStatus,  mediaType.APPLICATION_JSON.toString(), status);
    }

    /*This function is used for getting String message with respect to Accept-Language
    Accept Language basically used via a header in app side*/
    public  String getActualMessage(String text, MessageSource messageSource) {
        return messageSource.getMessage(text, null, LocaleContextHolder.getLocale());
    }
}
