package com.erproject.busgo.data.sharedPreferences;

import org.jetbrains.annotations.NotNull;

public enum LocalSharedPreferences {
    CITY("CITY");

    private String mType;

    LocalSharedPreferences(String mType) {
        this.mType = mType;
    }

    @NotNull
    public String toString() {
        return mType;
    }
}