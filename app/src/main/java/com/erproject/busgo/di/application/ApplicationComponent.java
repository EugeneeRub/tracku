package com.erproject.busgo.di.application;

import android.app.Application;

import com.erproject.busgo.app.App;
import com.erproject.busgo.data.RepositoryModule;
import com.erproject.busgo.di.ActivityBindingModule;
import com.erproject.busgo.di.DialogBindingModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ApplicationModule.class,
        ActivityBindingModule.class, DialogBindingModule.class, RepositoryModule.class})
public interface ApplicationComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        ApplicationComponent.Builder application(Application application);

        ApplicationComponent build();
    }

}
