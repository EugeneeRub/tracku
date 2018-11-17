package com.erproject.busgo.dependencyinjection;

import com.erproject.busgo.dependencyinjection.scoped.ActivityScoped;
import com.erproject.busgo.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@SuppressWarnings("unused")
@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract MainActivity mainActivityPedestrian();
}
