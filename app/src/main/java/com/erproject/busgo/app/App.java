package com.erproject.busgo.app;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.erproject.busgo.BuildConfig;
import com.erproject.busgo.R;
import com.erproject.busgo.di.application.DaggerApplicationComponent;
import com.mapbox.mapboxsdk.Mapbox;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class App extends DaggerApplication {
    private SharedPreferences preferences;
    private SharedPreferences.Editor mEditor;
    private static App app = null;

    private static final String THEME_MODE = "THEME_MODE";

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().application(this).build();
    }

    @Override
    @SuppressWarnings("all")
    public void onCreate() {
        super.onCreate();
        Mapbox.getInstance(this, BuildConfig.ACCESS_TOKEN_MAP);

        app = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = preferences.edit();
    }

    public int getThemeMode() {
        return preferences.getInt(THEME_MODE, 0);
    }

    public void setThemeMode(int themeMode) {
        mEditor.putInt(THEME_MODE, themeMode).commit();
    }

    public static App getInstance() {
        return app;
    }
}
