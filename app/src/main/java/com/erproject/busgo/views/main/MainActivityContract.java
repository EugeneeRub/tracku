package com.erproject.busgo.views.main;

import com.erproject.busgo.base.mvpInterfaces.BasePresenter;
import com.erproject.busgo.base.mvpInterfaces.BaseView;
import com.erproject.busgo.data.sharedPreferences.LocalSharedPreferences;

public interface MainActivityContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

        void saveStringInPref(LocalSharedPreferences localSharedPreferences, String city);

        String getStringFromPref(LocalSharedPreferences pref);
    }
}
