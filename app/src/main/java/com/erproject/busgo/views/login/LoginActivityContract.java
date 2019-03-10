package com.erproject.busgo.views.login;

import com.erproject.busgo.base.mvp.BasePresenter;
import com.erproject.busgo.base.mvp.BaseView;

public interface LoginActivityContract {
    interface View extends BaseView {
        void doneLogin(String username, String password, String token);
        void showToastError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void sendLogin(String email, String password);
    }
}
