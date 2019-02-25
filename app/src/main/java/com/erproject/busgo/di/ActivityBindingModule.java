package com.erproject.busgo.di;

import com.erproject.busgo.di.scoped.ActivityScoped;
import com.erproject.busgo.views.login.LoginActivity;
import com.erproject.busgo.views.main.MainActivity;
import com.erproject.busgo.views.main.MainActivityModule;
import com.erproject.busgo.views.main.fragmentLoadTrack.phones.PhonesActivity;
import com.erproject.busgo.views.registration.RegistrationActivity;
import com.erproject.busgo.views.settings.SettingsActivity;

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
    abstract RegistrationActivity bindRegistrationActivity();

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract LoginActivity bindLoginActivity();

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract SettingsActivity bindSettingsActivity();

    @ActivityScoped
    @ContributesAndroidInjector()
    abstract PhonesActivity bindPhonesActivity();
}
