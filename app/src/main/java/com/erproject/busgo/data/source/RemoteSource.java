package com.erproject.busgo.data.source;

import android.app.Application;
import androidx.annotation.NonNull;

import com.erproject.busgo.data.data.request.UserRegistrationRequest;
import com.erproject.busgo.data.data.responses.BaseResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class RemoteSource implements Source.IAuth {

    private final RemoteApi remoteApiApiBase;

    @Inject
    Application context;

    @Inject
    RemoteSource(RemoteApi remoteApiApiBase) {
        this.remoteApiApiBase = remoteApiApiBase;
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
