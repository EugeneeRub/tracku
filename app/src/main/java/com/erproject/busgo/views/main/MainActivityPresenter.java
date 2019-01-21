package com.erproject.busgo.views.main;

import com.erproject.busgo.data.Repository;
import com.erproject.busgo.data.data.responses.CityFinderResponse;
import com.erproject.busgo.data.sharedPreferences.LocalSharedPreferences;
import com.erproject.busgo.data.sharedPreferences.LocalSharedPreferencesSource;

import javax.inject.Inject;

import rx.Subscriber;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    private final Repository repository;
    @Inject
    LocalSharedPreferencesSource sharedPreferencesSource;
    private MainActivityContract.View view;

    @Inject
    MainActivityPresenter(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void loadCurrentCity() {
        repository.getCityByIp().subscribe(new Subscriber<CityFinderResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.showCurrentCityDialog(new CityFinderResponse(false));
            }

            @Override
            public void onNext(CityFinderResponse cityFinderResponse) {
                if (cityFinderResponse.getCity() != null)
                    cityFinderResponse.setStatus(true);
                else
                    cityFinderResponse.setStatus(false);
                view.showCurrentCityDialog(cityFinderResponse);
            }
        });
    }

    @Override
    public void saveStringInPref(LocalSharedPreferences localSharedPreferences, String city) {
        sharedPreferencesSource.setString(localSharedPreferences, city);
    }

    @Override
    public String getStringFromPref(LocalSharedPreferences pref) {
        return sharedPreferencesSource.getString(pref);
    }

    @Override
    public void takeView(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
