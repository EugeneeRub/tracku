package com.erproject.busgo.data.source;

import com.erproject.busgo.data.data.responses.CityFinderResponse;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface RemoteCityFinderApi {

    //region FIND_PLACE
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    @GET("check")
    Observable<CityFinderResponse> getCityByIp(@Query("access_key") String key);
    //endregion
}
