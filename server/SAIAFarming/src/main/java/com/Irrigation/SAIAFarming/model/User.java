package com.Irrigation.SAIAFarming.model;

import javax.xml.bind.annotation.XmlElement;

public class User {
    @XmlElement(name = "user_id")
    String userid;

    @XmlElement(name = "user_name")
    String username;

    @XmlElement(name = "password")
    private String password;

    @XmlElement(name = "mobile_num")
    private String mobilenum;

    @XmlElement(name = "user_type")
    private String usertype;

    public User() {
    }

    public User(String userid, String username, String mobilenum, String usertype) {
        this.userid = userid;
        this.username = username;
        this.mobilenum = mobilenum;
        this.usertype = usertype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
