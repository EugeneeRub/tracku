package com.erproject.busgo.data.data.request.fbRegistration;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

@IgnoreExtraProperties
public class FbUserRegistration {
    private String mRegisterPhone;
    private String mEmailSuffix;
    private String mUniqueCode;
    private Map<String, Object> mapOfUsers;

    public FbUserRegistration() {
    }

    public FbUserRegistration(String mRegisterPhone, String mEmailSuffix, String mUniqueCode,
                              Map<String, Object> mapOfUsers) {
        this.mRegisterPhone = mRegisterPhone;
        this.mEmailSuffix = mEmailSuffix;
        this.mUniqueCode = mUniqueCode;
        this.mapOfUsers = mapOfUsers;
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

    public Map<String, Object> getMapOfUsers() {
        return mapOfUsers;
    }

    public void setMapOfUsers(Map<String, Object> mapOfUsers) {
        this.mapOfUsers = mapOfUsers;
    }

    public String getmUniqueCode() {
        return mUniqueCode;
    }

    public void setmUniqueCode(String mUniqueCode) {
        this.mUniqueCode = mUniqueCode;
    }
}
