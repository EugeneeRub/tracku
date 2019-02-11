package com.erproject.busgo.di;

import com.erproject.busgo.di.scoped.ActivityScoped;
import com.erproject.busgo.dialogs.EnterCodeDialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DialogBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector
    abstract EnterCodeDialog bindEnterCodeDialog();
}
