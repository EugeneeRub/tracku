package com.erproject.busgo.views.main.fragmentMap;

import android.location.Location;
import androidx.annotation.Nullable;

import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.List;

import javax.inject.Inject;

public class MapPresenter implements MapContract.Presenter {

    @Nullable
    private MapContract.View mView;
    private List<UserModel> mSavedActiveUsers;
    private Location mLastLocation;

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
        mSavedActiveUsers = activeUsers;
        if (mView != null) {
            mView.stopShowingUsers();
            mView.showUsers(activeUsers);
        }
    }

    @Override
    public void saveLastLocation(Location lastLocation) {
        this.mLastLocation = lastLocation;
    }

    List<UserModel> getmSavedActiveUsers() {
        return mSavedActiveUsers;
    }

    public Location getmLastLocation() {
        return mLastLocation;
    }
}
