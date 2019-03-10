package com.erproject.busgo.views.registration;

import com.erproject.busgo.base.mvp.BasePresenter;
import com.erproject.busgo.base.mvp.BaseView;
import com.erproject.busgo.data.data.responses.SignUpResponseError;

public interface RegistrationActivityContract {
    interface View extends BaseView {
        void doneRegistration(String username, String password, String token);

        void setEmailError(SignUpResponseError error);

        void showToastError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void sendRegistration(String username, String email, String phone, String password,
                              String unique);
    }
}
