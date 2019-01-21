package com.erproject.busgo.data.data.responses;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    @SerializedName("statusCode")
    protected String mStatusCode;
    @SerializedName("statusCodeValue")
    protected Long mStatusCodeValue;
    @SerializedName("token")
    protected String mToken;

    public String getmStatusCode() {
        return mStatusCode;
    }

    public Long getmStatusCodeValue() {
        return mStatusCodeValue;
    }

    public String getmToken() {
        return mToken;
    }
}
