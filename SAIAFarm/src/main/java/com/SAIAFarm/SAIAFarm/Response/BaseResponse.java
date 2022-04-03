

package com.SAIAFarm.SAIAFarm.Response;

public abstract class BaseResponse {
    private boolean status;
    private int statusCode;
    private String rawResponse;

    BaseResponse(boolean status, int statusCode, String rawResponse) {
        this.status = status;
        this.statusCode = statusCode;
        this.rawResponse = rawResponse;
    }

    public boolean isSuccessful() {
        return (Boolean.TRUE == status);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getRawResponse() {
        return rawResponse;
    }

}
