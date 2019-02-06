package com.erproject.busgo.app;

import com.erproject.busgo.R;
import com.erproject.busgo.di.application.DaggerApplicationComponent;
import com.mapbox.mapboxsdk.Mapbox;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class App extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Mapbox.getInstance(this, getString(R.string.ACCESS_TOKEN_MAP));
    }
}
