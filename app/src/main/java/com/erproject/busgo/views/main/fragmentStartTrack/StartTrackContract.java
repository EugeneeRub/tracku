package com.erproject.busgo.views.main.fragmentStartTrack;

import android.content.Context;

import com.erproject.busgo.base.mvpInterfaces.BasePresenter;
import com.erproject.busgo.base.mvpInterfaces.BaseView;
import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.List;

public interface StartTrackContract {
    interface View extends BaseView {
        Context getmContext();

        void updateState(List<UserModel> mUser);

        void stopTracking();
    }

    interface Presenter extends BasePresenter<View> {
        void updateDatabase(String name);

        String getBasePhone();
    }
}
