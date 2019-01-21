package com.erproject.busgo.data.data.responses;

import com.google.gson.annotations.SerializedName;

public class SignUpResponseError {
    @SerializedName("phone")
    private String mPhoneError;
    @SerializedName("email")
    private String mEmailError;
    @SerializedName("username")
    private String mUsernameError;

    public String getmPhoneError() {
        return mPhoneError;
    }

    public String getmEmailError() {
        return mEmailError;
    }

    public String getmUsernameError() {
        return mUsernameError;
    }

}
