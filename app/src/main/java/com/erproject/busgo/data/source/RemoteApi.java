package com.erproject.busgo.data.source;

import com.erproject.busgo.data.data.request.UserRegistrationRequest;
import com.erproject.busgo.data.data.responses.BaseResponse;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface RemoteApi {

    //region AUTHENIFICATION
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    @POST("api/registration")
    Observable<BaseResponse> signUp(@Body UserRegistrationRequest body);

    @Headers({"Accept:application/json", "Content-Type:application/json"})
    @POST("api/login")
    Observable<BaseResponse> signIn(@Body UserRegistrationRequest body);
    //endregion AUTHENIFICATION

}
