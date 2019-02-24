package com.erproject.busgo.views.main.fragmentMap;

import android.support.annotation.Nullable;

import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.List;

import javax.inject.Inject;

public class MapPresenter implements MapContract.Presenter {

    @Nullable
    private MapContract.View mView;
    private List<UserModel> savedActiveUsers;

    @Inject
    MapPresenter() {
    }

    @Override
    public void takeView(MapContract.View view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        this.mView = null;
    }

    @Override
    public void checkAndShow(List<UserModel> activeUsers) {
        savedActiveUsers = activeUsers;
        if (mView != null) {
            mView.stopShowingUsers();
            mView.showUsers(activeUsers);
        }
    }

    List<UserModel> getSavedActiveUsers() {
        return savedActiveUsers;
    }
}
