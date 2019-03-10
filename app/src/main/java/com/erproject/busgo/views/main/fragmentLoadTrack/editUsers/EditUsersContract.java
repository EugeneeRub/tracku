package com.erproject.busgo.views.main.fragmentLoadTrack.editUsers;

import android.content.Context;

import com.erproject.busgo.base.mvp.BasePresenter;
import com.erproject.busgo.base.mvp.BaseView;
import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.List;

public interface EditUsersContract {
    interface View extends BaseView {
        void setItems(List<UserModel> list);
        Context getmContext();
    }

    interface Presenter extends BasePresenter<View> {

        void updateUser(UserModel model);
    }
}
