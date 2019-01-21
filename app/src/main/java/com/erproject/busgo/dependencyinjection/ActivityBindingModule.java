package com.erproject.busgo.dependencyinjection;

import com.erproject.busgo.dependencyinjection.scoped.ActivityScoped;
import com.erproject.busgo.views.login.LoginActivity;
import com.erproject.busgo.views.main.MainActivity;
import com.erproject.busgo.views.main.MainActivityModule;
import com.erproject.busgo.views.registration.RegistrationActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@SuppressWarnings("unused")
@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract RegistrationActivity registrationActivity();

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract LoginActivity loginActivity();
}
