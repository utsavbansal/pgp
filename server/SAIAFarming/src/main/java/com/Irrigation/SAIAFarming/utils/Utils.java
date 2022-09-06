package com.Irrigation.SAIAFarming.utils;



import com.SAIAFarm.SAIAFarm.ClientSaiaFarmApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Utils {

    public static  int USER_ID_NAME_CHRACTERS = 4;
    public static final int USERID_NOT_EXISTING = 2;
    public static final int USERID_FARM_NAME_DUPLICATE = 3;
    public static final int OK = 1;
    private static ClientSaiaFarmApplication saiafarmAPI = ClientSaiaFarmApplication.getInstance();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date validateDateParam(String datestr) {
        Date date = formatDateStr(datestr);
        if (date == null) {
            System.out.println("Invalid request param of date type: ["+datestr+"]");

        }
        return date;
    }
    public static Date formatDateStr(String dateStr) {
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            System.out.print("Failed to pare date string: " + dateStr + ". Cause: " + e.getMessage());
           // throwBadRequestException(mediaType, responseCode);

        }
        return null;
    }
}
