package com.erproject.busgo.data.data.request.fbRegistration;

import com.google.firebase.database.PropertyName;

import java.util.Map;

public class FbUserRegistration {
    @PropertyName("register_phone") private String mRegisterPhone;
    @PropertyName("email_suffix") private String mEmailSuffix;
    @PropertyName("child_users") private Map<String, Object> mapOfUsers;

    public FbUserRegistration() {
    }

    public FbUserRegistration(String mRegisterPhone, String mEmailSuffix,
                              Map<String, Object> mapOfUsers) {
        this.mRegisterPhone = mRegisterPhone;
        this.mEmailSuffix = mEmailSuffix;
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
}
