package com.erproject.busgo.data.data.request.fbRegistration;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.util.HashMap;
import java.util.Map;

public class FbConnectedUser {
    @PropertyName("latitude")
    private Double mLatitude = 0d;
    @PropertyName("longitude")
    private Double mLongitude = 0d;
    @PropertyName("last_time")
    private String mLastTimeActive = "";// 01.01.2018 11:10
    @PropertyName("phone")
    private String mPhone = "";
    @PropertyName("is_tracking")
    private Boolean mIsTracking = false;

    public FbConnectedUser() {
    }

    public FbConnectedUser(Double mLatitude, Double mLongitude, String mLastTimeActive, String mPhone) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mLastTimeActive = mLastTimeActive;
        this.mPhone = mPhone;
        this.mIsTracking = false;
    }

    @Exclude
    public Map<String, Object> getMappedObject() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("latitude", mLatitude);
        map.put("longitude", mLongitude);
        map.put("last_time", mLastTimeActive);
        map.put("phone", mPhone);
        return map;
    }

    public Double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getmLastTimeActive() {
        return mLastTimeActive;
    }

    public void setmLastTimeActive(String mLastTimeActive) {
        this.mLastTimeActive = mLastTimeActive;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public Boolean getmIsTracking() {
        return mIsTracking;
    }

    public void setmIsTracking(Boolean mIsTracking) {
        this.mIsTracking = mIsTracking;
    }
}
