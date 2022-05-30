package com.Irrigation.SAIAFarming.entity.usermanagement;

import com.Irrigation.SAIAFarming.utils.Utils;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Locale;
import java.util.Random;


@Entity
@Table(name = "user")
public class UserRegistration implements Serializable {
    private static final long serialVersionUID = 6796098319658059439L;

    @Id
    @Column(name = "user_id")
    private String id;

    @NotNull
    @Column(name = "user_name")
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Column(name = "user_type")
    private String usertype;

    @NotNull
    @Column(name = "mobile_num", unique = true)
    private String mobilenum;

    private int genRand(){
        Random gen = new Random();
        int min = 1000;
        int max = 10000;
        int result = gen.nextInt(max-min+1) + min;
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId() {
        // "\\s" is to remove all the spaces
        String user_name_creation = this.getUsername().toLowerCase().replaceAll("\\s+", "");
        int ran_num = genRand();
        if (user_name_creation.length() < Utils.USER_ID_NAME_CHRACTERS)
            user_name_creation = user_name_creation + ran_num;

        this.id = user_name_creation.substring(0,Utils.USER_ID_NAME_CHRACTERS) + ran_num;

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

    public void setPassword(String password) throws NoSuchAlgorithmException, NoSuchProviderException {

        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }
}
