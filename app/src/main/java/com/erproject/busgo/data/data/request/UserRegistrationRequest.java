package com.erproject.busgo.data.data.request;

import com.google.gson.annotations.SerializedName;

public class UserRegistrationRequest {
    @SerializedName("email")
    private String mEmail;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("phoneNumber")
    private String mPhoneNumber;
    @SerializedName("username")
    private String mUsername;

    private String mUniqueCode;

    public UserRegistrationRequest(String mUsername, String mEmail, String mPassword,
                                   String mUnique, String mPhoneNumber) {
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mPhoneNumber = mPhoneNumber;
        this.mUniqueCode = mUnique;
        this.mUsername = mUsername;
    }

    public UserRegistrationRequest(String mEmail, String mPassword) {
        this.mEmail = mEmail;
        this.mPassword = mPassword;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmUniqueCode() {
        return mUniqueCode;
    }
}
