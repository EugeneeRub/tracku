package com.erproject.busgo.views.main.fragmentMap;

import android.location.Location;

import com.erproject.busgo.base.mvpInterfaces.BasePresenter;
import com.erproject.busgo.base.mvpInterfaces.BaseView;
import com.erproject.busgo.data.data.request.fbRegistration.FbConnectedUser;
import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.List;

public interface MapContract {
    interface View extends BaseView {
        void showUsers(List<UserModel> activeUsers);

        void stopShowingUsers();

        void showUserOnMap(FbConnectedUser user);
    }

    interface Presenter extends BasePresenter<View> {

        void checkAndShow(List<UserModel> activeUsers);

        void saveLastLocation(Location lastLocation);
    }
}
