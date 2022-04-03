package com.Irrigation.SAIAFarming.model;

import javax.xml.bind.annotation.*;
import java.util.HashMap;

@XmlRootElement (name="status")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"req_id", "http_code", "status_code", "message"})
public class ResponseData {
    @XmlElement(name = "req_id")
    private String reqId;

    @XmlElement(name = "http_code")
    private int httpCode;

    @XmlElement(name = "status_code")
    private int statusCode;

    @XmlElement(name = "message")
    private String msg;

    @XmlElement(name = "data")
    public HashMap<String, Object> data = null;


    public ResponseData() {}



    public ResponseData(String reqId, int httpCode, int statusCode, String text)
    {
        this.reqId = reqId;
        this.httpCode = httpCode;
        this.statusCode = statusCode;
        this.msg = text;
    }

    public ResponseData(String reqId, int httpCode, int statusCode, HashMap<String, Object> data)
    {
        this.reqId = reqId;
        this.httpCode = httpCode;
        this.statusCode = statusCode;
        this.data = data;
    }


    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}
