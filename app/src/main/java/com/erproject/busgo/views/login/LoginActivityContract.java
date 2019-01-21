package com.erproject.busgo.views.login;

import com.erproject.busgo.base.mvpInterfaces.BasePresenter;
import com.erproject.busgo.base.mvpInterfaces.BaseView;

public interface LoginActivityContract {
    interface View extends BaseView {
        void doneLogin(String username, String password, String token);
    }

    interface Presenter extends BasePresenter<View> {
        void sendLogin(String email, String password);
    }
}
