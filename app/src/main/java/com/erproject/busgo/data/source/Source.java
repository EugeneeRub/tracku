package com.erproject.busgo.data.source;

import androidx.annotation.NonNull;

import com.erproject.busgo.data.data.request.UserRegistrationRequest;
import com.erproject.busgo.data.data.responses.BaseResponse;

import retrofit2.http.Body;
import rx.Observable;

public interface Source {

    interface IAuth {
        @NonNull
        Observable<BaseResponse> signUp(@Body UserRegistrationRequest body);
        @NonNull
        Observable<BaseResponse> signIn(@Body UserRegistrationRequest body);
    }
}
