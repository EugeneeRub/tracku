package com.erproject.busgo.dependencyinjection;

import com.erproject.busgo.IntroActivity;
import com.erproject.busgo.dependencyinjection.scoped.ActivityScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@SuppressWarnings("unused")
@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector()
    abstract IntroActivity mainActivity();
}
