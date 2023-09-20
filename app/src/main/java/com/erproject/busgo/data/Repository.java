package com.erproject.busgo.data;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import androidx.annotation.NonNull;

import com.erproject.busgo.data.annotations.Remote;
import com.erproject.busgo.data.data.request.UserRegistrationRequest;
import com.erproject.busgo.data.data.responses.BaseResponse;
import com.erproject.busgo.data.source.Source;

import java.net.ConnectException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class Repository implements  Source.IAuth {

    private final Source.IAuth mAuth;
    @Inject
    Application context;

    @Inject
    Repository(
            @Remote Source.IAuth mAuth) {
        this.mAuth = mAuth;
    }

    private boolean isInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null;
    }

    @NonNull
    @Override
    public Observable<BaseResponse> signUp(UserRegistrationRequest body) {
        if (isInternetConnection()) return mAuth.signUp(body);
        else return rx.Observable.error(new ConnectException("Internet connection is lost!"));
    }

    @NonNull
    @Override
    public Observable<BaseResponse> signIn(UserRegistrationRequest body) {
        if (isInternetConnection()) return mAuth.signIn(body);
        else return rx.Observable.error(new ConnectException("Internet connection is lost!"));
    }
}
