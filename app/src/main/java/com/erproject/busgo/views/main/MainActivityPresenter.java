package com.erproject.busgo.views.main;

import com.erproject.busgo.data.Repository;
import com.erproject.busgo.data.sharedPreferences.LocalSharedPreferences;
import com.erproject.busgo.data.sharedPreferences.LocalSharedPreferencesSource;

import javax.inject.Inject;

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
