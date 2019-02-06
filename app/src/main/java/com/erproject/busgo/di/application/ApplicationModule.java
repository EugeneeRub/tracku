package com.erproject.busgo.di.application;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

@SuppressWarnings("unused")
@Module
public interface ApplicationModule {
    @Binds
    Context bindContext(Application application);
}
