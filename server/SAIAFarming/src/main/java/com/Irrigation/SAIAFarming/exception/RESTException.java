
package com.Irrigation.SAIAFarming.exception;



import com.Irrigation.SAIAFarming.model.ResponseStatus;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RESTException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public RESTException(Response.Status internalServerError, ResponseStatus status) {
        this(Response.Status.BAD_REQUEST, MediaType.APPLICATION_JSON, "Bad Request");
    }

    public RESTException(Response.Status status, String type, String message) {
        super(Response.status(status).type(type).entity(message).build());
    }

    public RESTException(Response.Status status, String type, ResponseStatus message) {
        super(Response.status(status).type(type).entity(message).build());
    }
}

