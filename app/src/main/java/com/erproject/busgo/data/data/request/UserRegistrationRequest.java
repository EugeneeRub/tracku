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

    public UserRegistrationRequest(String mUsername, String mEmail, String mPassword, String mPhoneNumber) {
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mPhoneNumber = mPhoneNumber;
        this.mUsername = mUsername;
    }

    public UserRegistrationRequest(String mEmail, String mPassword) {
        this.mEmail = mEmail;
        this.mPassword = mPassword;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }
}
