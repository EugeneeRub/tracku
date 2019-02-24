package com.erproject.busgo.views.main.fragmentMap;

import com.erproject.busgo.base.mvpInterfaces.BasePresenter;
import com.erproject.busgo.base.mvpInterfaces.BaseView;
import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.List;

public interface MapContract {
    interface View extends BaseView {
        void showUsers(List<UserModel> activeUsers);

        void stopShowingUsers();
    }

    interface Presenter extends BasePresenter<View> {

        void checkAndShow(List<UserModel> activeUsers);
    }
}
