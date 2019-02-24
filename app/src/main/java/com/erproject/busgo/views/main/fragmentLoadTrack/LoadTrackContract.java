package com.erproject.busgo.views.main.fragmentLoadTrack;

import android.content.Context;

import com.erproject.busgo.base.mvpInterfaces.BasePresenter;
import com.erproject.busgo.base.mvpInterfaces.BaseView;
import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.List;

public interface LoadTrackContract {
    interface View extends BaseView {
        Context getmContext();

        void updateState(List<UserModel> mUser);

        void showUsersOnMap(List<UserModel> list);
    }

    interface Presenter extends BasePresenter<View> {
        void updateDatabase();

        void stopCallBack();

        void stopLoading();
    }
}
