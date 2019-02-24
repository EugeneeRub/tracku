package com.erproject.busgo.data.data.request.fbRegistration;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class FbConnectedUser implements Parcelable {
    private Double mLatitude = 0d;
    private Double mLongitude = 0d;
    private String mLastTimeActive = "";// 01.01.2018 11:10
    private String mPhone = "";
    private Boolean mIsTracking = false;
    private Boolean mIsUsed = false;

    public FbConnectedUser() {
    }

    public FbConnectedUser(Double mLatitude, Double mLongitude, String mLastTimeActive,
                           String mPhone, Boolean mIsTracking, Boolean mIsUsed) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mLastTimeActive = mLastTimeActive;
        this.mPhone = mPhone;
        this.mIsTracking = mIsTracking;
        this.mIsUsed = mIsUsed;
    }

    FbConnectedUser(Parcel in) {
        if (in.readByte() == 0) {
            mLatitude = null;
        } else {
            mLatitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            mLongitude = null;
        } else {
            mLongitude = in.readDouble();
        }
        mLastTimeActive = in.readString();
        mPhone = in.readString();
        byte tmpMIsTracking = in.readByte();
        mIsTracking = tmpMIsTracking == 0 ? null : tmpMIsTracking == 1;
        byte tmpMIsUsed = in.readByte();
        mIsUsed = tmpMIsUsed == 0 ? null : tmpMIsUsed == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mLatitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mLatitude);
        }
        if (mLongitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(mLongitude);
        }
        dest.writeString(mLastTimeActive);
        dest.writeString(mPhone);
        dest.writeByte((byte) (mIsTracking == null ? 0 : mIsTracking ? 1 : 2));
        dest.writeByte((byte) (mIsUsed == null ? 0 : mIsUsed ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FbConnectedUser> CREATOR = new Creator<FbConnectedUser>() {
        @Override
        public FbConnectedUser createFromParcel(Parcel in) {
            return new FbConnectedUser(in);
        }

        @Override
        public FbConnectedUser[] newArray(int size) {
            return new FbConnectedUser[size];
        }
    };

    @Exclude
    public Map<String, Object> getMappedObject() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("latitude", mLatitude);
        map.put("longitude", mLongitude);
        map.put("last_time", mLastTimeActive);
        map.put("phone", mPhone);
        return map;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getLastTimeActive() {
        return mLastTimeActive != null ? mLastTimeActive : "";
    }

    public void setLastTimeActive(String mLastTimeActive) {
        this.mLastTimeActive = mLastTimeActive;
    }

    public String getPhone() {
        return mPhone != null ? mPhone : "";
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public Boolean getIsTracking() {
        return mIsTracking;
    }

    public void setIsTracking(Boolean mIsTracking) {
        this.mIsTracking = mIsTracking;
    }

    public Boolean getIsUsed() {
        return mIsUsed;
    }

    public void setIsUsed(Boolean mIsUsed) {
        this.mIsUsed = mIsUsed;
    }
}
