package com.erproject.busgo.views.main.fragmentStartTrack;

import android.support.annotation.NonNull;

import com.erproject.busgo.BuildConfig;
import com.erproject.busgo.data.data.request.fbRegistration.FbConnectedUser;
import com.erproject.busgo.data.data.request.fbRegistration.FbUserRegistration;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.erproject.busgo.data.exceptions.UserNotFoundException;
import com.erproject.busgo.services.authManager.AuthController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class StartTrackPresenter implements StartTrackContract.Presenter {
    @Nullable
    private StartTrackContract.View mView;
    private DatabaseReference mDatabase;
    private FbUserRegistration mUser;
    private String mUserId;
    private String mChoosedUser;
    private List<UserModel> mList;
    private boolean mIsUpdated;

    private ValueEventListener mValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (mIsUpdated) {
                mUser = dataSnapshot.getValue(FbUserRegistration.class);
                if (mUser != null) update(mUser.getMapOfUsers());
            } else mIsUpdated = true;
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            if (mView != null) mView.showError(databaseError.getMessage());
        }
    };

    @Inject
    StartTrackPresenter() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.mList = new ArrayList<>();
        this.mIsUpdated = true;
    }

    @Override
    public void takeView(StartTrackContract.View view) {
        this.mView = view;
        if (mUser == null) {
            try {
                mUserId = AuthController.getLoginString(mView.getmContext());
                updateUsers(mUserId);
            } catch (UserNotFoundException e) {
                mView.goToLogin();
            }
        }
    }

    private void updateUsers(String id) {
        mDatabase.child("users").child(id).addValueEventListener(mValueEventListener);
    }

    @SuppressWarnings("all")
    private synchronized void update(Map<String, FbConnectedUser> mUsers) {
        if (mUsers == null) return;
        mList.clear();
        HashMap<String, FbConnectedUser> map = new HashMap<>(mUsers);
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            UserModel userModel = new UserModel();
            userModel.setName((String) pair.getKey());
            userModel.setUser((FbConnectedUser) pair.getValue());

            mList.add(userModel);

            it.remove();// avoids a ConcurrentModificationException
        }

        if (mView != null && this.mUser != null) mView.updateState(mList);
    }

    @Override
    public void dropView() {
        this.mView = null;
        stopTracking();
        this.mDatabase.onDisconnect();
    }

    List<UserModel> getList() {
        return mList;
    }

    @Override
    public void updateDatabase(String name) {
        this.mChoosedUser = name;
        HashMap<String, Object> mMap = new HashMap<>();
        mMap.put(name, mUser.getMapOfUsers().get(name));
        mIsUpdated = false;
        mDatabase.child(BuildConfig.START_PATH).child(mUserId).child(BuildConfig.MAP_USER_PATH)
                .updateChildren(mMap);
    }

    @Override
    public String getBasePhone() {
        if (mUser == null) return "";
        return mUser.getmRegisterPhone() != null ? mUser.getmRegisterPhone() : "";
    }

    void stopTracking() {
        if (mChoosedUser == null) return;
        FbConnectedUser user = mUser.getMapOfUsers().get(mChoosedUser);
        if (user != null) user.setIsUsed(false);

        HashMap<String, Object> mMap = new HashMap<>();
        mMap.put(mChoosedUser, user);

        mDatabase.child(BuildConfig.START_PATH).child(mUserId).child(BuildConfig.MAP_USER_PATH)
                .updateChildren(mMap);
    }

    String getUserId() {
        return mUserId;
    }

    UserModel getModel() {
        for (UserModel user : mList) {
            if (user.getName().equals(mChoosedUser)) return user;
        }
        return null;
    }
}