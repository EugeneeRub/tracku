package com.erproject.busgo.data.sharedPreferences;


import androidx.annotation.NonNull;

public enum LocalSharedPreferences {
    CITY("CITY");

    private String mType;

    LocalSharedPreferences(String mType) {
        this.mType = mType;
    }

    @NonNull
    public String toString() {
        return mType;
    }
}