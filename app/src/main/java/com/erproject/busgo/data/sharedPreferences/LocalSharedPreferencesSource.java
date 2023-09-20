package com.erproject.busgo.data.sharedPreferences;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;

public class LocalSharedPreferencesSource {

    private SharedPreferences mSharedPreferences;

    @Inject
    LocalSharedPreferencesSource(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    @Nullable
    public String getString(@NonNull LocalSharedPreferences name) {
        return mSharedPreferences.getString(name.toString(), null);
    }

    public void setString(@NonNull LocalSharedPreferences name, @NonNull String data) {
        mSharedPreferences.edit().putString(name.toString(), data).apply();
    }

    public void clearAll() {
        mSharedPreferences.edit().clear().apply();
    }

    public void clearSpecs(LocalSharedPreferences... sharedPreferences) {
        for (LocalSharedPreferences preferences : sharedPreferences) {
            mSharedPreferences.edit().remove(preferences.toString()).apply();
        }
    }

}
