package com.erproject.busgo.data.data.request.fbRegistration;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

@IgnoreExtraProperties
public class FbUserRegistration {
    private String mRegisterPhone;
    private String mEmailSuffix;
    private String mUniqueCode;
    private Boolean mIsHaveProVersion;
    private Map<String, FbConnectedUser> mapOfUsers;

    public FbUserRegistration() {
    }

    public String getmRegisterPhone() {
        return mRegisterPhone;
    }

    public void setmRegisterPhone(String mRegisterPhone) {
        this.mRegisterPhone = mRegisterPhone;
    }

    public String getmEmailSuffix() {
        return mEmailSuffix;
    }

    public void setmEmailSuffix(String mEmailSuffix) {
        this.mEmailSuffix = mEmailSuffix;
    }

    public Map<String, FbConnectedUser> getMapOfUsers() {
        return mapOfUsers;
    }

    public void setMapOfUsers(Map<String, FbConnectedUser> mapOfUsers) {
        this.mapOfUsers = mapOfUsers;
    }

    public String getmUniqueCode() {
        return mUniqueCode;
    }

    public void setmUniqueCode(String mUniqueCode) {
        this.mUniqueCode = mUniqueCode;
    }

    public Boolean getmIsHaveProVersion() {
        return mIsHaveProVersion;
    }

    public void setmIsHaveProVersion(Boolean mIsHaveProVersion) {
        this.mIsHaveProVersion = mIsHaveProVersion;
    }
}
