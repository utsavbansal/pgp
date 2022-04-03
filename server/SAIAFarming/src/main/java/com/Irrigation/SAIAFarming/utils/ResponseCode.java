package com.Irrigation.SAIAFarming.utils;

public enum ResponseCode {
    SUCCESS(0, "Request succeeded"),
    //OK(200, "Accepted"),
    FAILED(1, "Request failed"),
    UNKNOWN(2, "Unknown"),
    SERVER_INTERNAL_SERVER_ERROR(501, "Internal_Server_error"),
    CLIENT_INVALID_REQ_PARAM_USER_NAME(1013,"Invalid_user_name"),
    CLIENT_INVALID_REQ_PARAM_DATE(1014,"Invalid_date"),
    CLIENT_INVALID_REQ_PARAM_FARMER_ID(1015,"Invalid farmer Id"),
    CLIENT_INVALID_REQ_PARAM_FARM_SIZE(1016,"Invalid size of farm"),
    CLIENT_INVALID_REQ_PARAM_FARM_CATEGORY(1017,"Invalid farm size category"),
    CLIENT_INVALID_REQ_PARAM_FARM_ID(1018,"Invalid farm Id");

    int code;
    String text;

    ResponseCode(int code, String text){
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }


    public String getText() {
        return text;
    }

    public static String getTextByCode(int code) {
        for (ResponseCode responseCode : ResponseCode.values()) {
            if (responseCode.getCode() == code) {
                return responseCode.getText();
            }
        }
        return ResponseCode.UNKNOWN.getText();
    }
    public String toString() {
        return code + " - " + text;
    }


}
