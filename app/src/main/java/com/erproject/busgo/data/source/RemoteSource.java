package com.erproject.busgo.data.source;

import android.app.Application;
import android.support.annotation.NonNull;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.request.UserRegistrationRequest;
import com.erproject.busgo.data.data.responses.BaseResponse;
import com.erproject.busgo.data.data.responses.CityFinderResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class RemoteSource implements Source.ICityFinder, Source.IAuth {

    private final RemoteCityFinderApi remoteCityFinderApi;
    private final RemoteApi remoteApiApiBase;

    @Inject
    Application context;

    @Inject
    RemoteSource(RemoteCityFinderApi remoteCityFinderApi, RemoteApi remoteApiApiBase) {
        this.remoteCityFinderApi = remoteCityFinderApi;
        this.remoteApiApiBase = remoteApiApiBase;
    }

    @NonNull
    @Override
    public Observable<CityFinderResponse> getCityByIp() {
        return remoteCityFinderApi.getCityByIp(context.getString(R.string.API_KEY_PLACE_FINDER))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    @Override
    public Observable<BaseResponse> signUp(UserRegistrationRequest body) {
        return remoteApiApiBase.signUp(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    @Override
    public Observable<BaseResponse> signIn(UserRegistrationRequest body) {
        return remoteApiApiBase.signIn(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
