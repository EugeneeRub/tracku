package com.erproject.busgo.views.registration;

import com.erproject.busgo.base.mvpInterfaces.BasePresenter;
import com.erproject.busgo.base.mvpInterfaces.BaseView;

public interface RegistrationActivityContract {
    interface View extends BaseView {
        void doneRegistration(String username, String password, String token);

        void setEmailError(String error);

        void showToastError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void sendRegistration(String username, String email, String phone, String password,
                              String unique);
    }
}
