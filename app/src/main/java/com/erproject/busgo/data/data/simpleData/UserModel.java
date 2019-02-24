package com.erproject.busgo.data.data.simpleData;

import android.os.Parcel;
import android.os.Parcelable;

import com.erproject.busgo.data.data.request.fbRegistration.FbConnectedUser;

public class UserModel implements Parcelable {
    private String name;
    private FbConnectedUser user;
    private boolean isMonitorUsed;

    public UserModel() {
    }

    public UserModel(String name, FbConnectedUser user, boolean isMonitorUsed) {
        this.name = name;
        this.user = user;
        this.isMonitorUsed = isMonitorUsed;
    }

    UserModel(Parcel in) {
        name = in.readString();
        user = in.readParcelable(FbConnectedUser.class.getClassLoader());
        isMonitorUsed = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(user, flags);
        dest.writeByte((byte) (isMonitorUsed ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(FbConnectedUser user) {
        this.user = user;
    }

    public void setMonitorUsed(boolean monitorUsed) {
        isMonitorUsed = monitorUsed;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public FbConnectedUser getUser() {
        return user;
    }

    public boolean isMonitorUsed() {
        return isMonitorUsed;
    }

}
