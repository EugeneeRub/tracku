package com.erproject.busgo.views.main.fragmentLoadTrack;

import android.content.Context;

import com.erproject.busgo.base.mvpInterfaces.BasePresenter;
import com.erproject.busgo.base.mvpInterfaces.BaseView;
import com.erproject.busgo.data.data.request.fbRegistration.FbUserRegistration;

public interface LoadTrackContract {
    interface View extends BaseView {
        Context getmContext();

        void updateState(FbUserRegistration mUser);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
