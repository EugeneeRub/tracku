package com.erproject.busgo.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.erproject.busgo.data.annotations.Remote;
import com.erproject.busgo.data.source.RemoteApi;
import com.erproject.busgo.data.source.RemoteSource;
import com.erproject.busgo.data.source.Source;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@SuppressWarnings("unused")
public abstract class RepositoryModule {
    private static final String BASE_URL = "http://139.162.187.46:1517/";
    private static final String SHARED_PREFERENCES_FILE_KEY = "RepositoryModule.SHARED_PREFERENCES_FILE_KEY";

    @Singleton
    @Provides
    @Named("provideBase")
    static Retrofit provideBase() {
        Gson gson = new GsonBuilder().setLenient().create();
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    static RemoteApi provideRemoteApi(@Named("provideBase") Retrofit retrofit) {
        return retrofit.create(RemoteApi.class);
    }

    @Singleton
    @Provides
    static SharedPreferences provideSharedPreferences(Application context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
    }

    @Singleton
    @Binds
    @Remote
    abstract Source.IAuth provideRemoteAuth(RemoteSource source);
}
