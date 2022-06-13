package com.Irrigation.SAIAFarming.model;

import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.*;
import java.util.HashMap;

@XmlRootElement (name="status")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"req_id", "http_code", "status_code", "message"})
public class ResponseStatus {
    @XmlElement(name = "req_id")
    private String reqId;

    @XmlElement(name = "http_code")
    private HttpStatus httpCode;

    @XmlElement(name = "status_code")
    private String statusCode;

    @XmlElement(name = "message")
    private String msg;


    public ResponseStatus() {}


    public ResponseStatus(String reqId, HttpStatus httpCode, String statusCode, String msg) {
        this.reqId = reqId;
        this.httpCode = httpCode;
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(HttpStatus httpCode) {
        this.httpCode = httpCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
