package com.Irrigation.SAIAFarming.utils;

public enum ResponseCode {
    SUCCESS(0, "Request succeeded"),
    //OK(200, "Accepted"),
    FAILED(1, "Request failed"),
    UNKNOWN(2, "Unknown"),
    SERVER_INTERNAL_SERVER_ERROR(501, "Internal_Server_error"),
    CLIENT_USER_ID_NOT_EXISTING(1012, "User_is_not_registered_please_register_the_user"),
    CLIENT_INVALID_REQ_PARAM_USER_NAME(1013,"Invalid_user_name"),
    CLIENT_INVALID_REQ_PARAM_GENDER(1014,"Invalid_gender"),
    CLIENT_INVALID_REQ_PARAM_RELATION(1015,"Invalid_Relation"),
    CLIENT_INVALID_REQ_PARAM_LITERACY(1016,"Invalid_education"),
    CLIENT_INVALID_REQ_PARAM_VILLAGE_NAME(1017,"Invalid_vilage_name"),
    CLIENT_INVALID_REQ_PARAM_ADDRESS_COORDINATES(1018,"Invalid_Address_Coordinates/Please provide coordinates"),
    CLIENT_INVALID_REQ_PARAM_MOBILE_NUM(1019,"Invalid_Mobile_Number"),
    CLIENT_INVALID_REQ_PARAM_USER_TYPE(1020,"Invalid_User_Type"),
    CLIENT_INVALID_REQ_PARAM_USERID_PASSWORD(1034,"Invalid_username_or_password"),

    CLIENT_INVALID_REQ_PARAM_DATE(1035,"Invalid_date"),
    CLIENT_INVALID_REQ_PARAM_FARMER_ID(0000,"Invalid farmer Id"),

    CLIENT_INVALID_REQ_PARAM_FARM_CATEGORY(1022,"Invalid farm size category"),
    CLIENT_INVALID_REQ_PARAM_FARM_ID(0000,"Invalid farm Id"),
    CLIENT_INVALID_REQ_PARAM_FARM_NAME(1023,"Invalid farm Name"),
    CLIENT_INVALID_REQ_PARAM_FARM_TYPE(1022,"Invalid farm Type"),
    CLIENT_INVALID_REQ_PARAM_FARM_SIZE(1033,"Invalid size of farm"),
    CLIENT_HAS_FARM_WITH_USER_ID(1024,"Farm_name_already_exists,_please_try_again_with_a_different_Farm_name"),
    CLIENT_INVALID_REQ_PARAM_USER_ID(1025,"Invalid_user_id"),
    CLIENT_REQ_FARM_WITH_USER_ID_IS_CONFLICT(1027,"Farm_name_repeated, please_provide_different_Farm_name_for_each_farm"),

    CLIENT_USER_MOBILE_EXISTING(1021, "Mobile_Number_already_In_Use_please_register_with_different_Mobile_number"),

    CLIENT_HAS_FARMER_WITH_USER_ID(1028,"Farmer_already_exists_with_this_user_id"),
    CLIENT_HAS_NO_FARM_WITH_USER_ID(1024,"Farm_Not_Present_With_This_Name"),
    CLIENT_INVALID_REQ_PARAM_FARM_PARCEL_NAME(1029,"Invalid parcel name"),
    CLIENT_HAS_PARCEL_WITH_USER_ID(1030,"Parcel_name_already_exists,_please_try_again_with_a_different_Parcel_name"),
    CLIENT_INVALID_REQ_PARAM_PARCEL_NAME(1032,"Invalid farm Name"),
    CLIENT_HAS_NO_PARCEL_WITH_USER_ID(1031,"Parcel_Not_Present_With_This_Name");


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
