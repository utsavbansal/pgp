package com.Irrigation.SAIAFarming.entity.usermanagement;

import javax.xml.bind.annotation.XmlElement;

public class UserLogin {
    @XmlElement(name = "user_id")
    private String userid;

    @XmlElement(name = "password")
    private String password;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserLogin(String userid, String password){
        this.userid = userid;
        this.password = password;
    }

}
