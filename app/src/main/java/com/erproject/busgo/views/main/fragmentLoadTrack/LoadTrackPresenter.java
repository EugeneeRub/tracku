package com.erproject.busgo.views.main.fragmentLoadTrack;

import android.support.annotation.NonNull;

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

public class LoadTrackPresenter implements LoadTrackContract.Presenter {
    @Nullable
    private LoadTrackContract.View mView;
    private DatabaseReference mDatabase;
    private FbUserRegistration mUser;
    private String mUserId;
    private List<UserModel> mList;
    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            mUser = dataSnapshot.getValue(FbUserRegistration.class);
            if (mUser != null) update(mUser.getMapOfUsers());
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            if (mView != null) mView.showError(databaseError.getMessage());
        }
    };

    @Inject
    LoadTrackPresenter() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.mList = new ArrayList<>();
    }

    @Override
    public void takeView(LoadTrackContract.View view) {
        this.mView = view;
        if (mUser == null && mUserId == null) {
            try {
                mUserId = AuthController.getLoginString(mView.getmContext());
                loadAndTrackUser(mUserId);
            } catch (UserNotFoundException e) {
                mView.goToLogin();
            }
        } else loadAndTrackUser(mUserId);
    }

    private void loadAndTrackUser(String userId) {
        mDatabase.child("users").child(userId).addValueEventListener(valueEventListener);
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

        if (mView != null && this.mUser != null) {
            mView.showUsersOnMap(mList);
            mView.updateState(mList);
        }
    }

    List<UserModel> getList() {
        return mList;
    }

    @Override
    public void dropView() {
        this.mView = null;
        this.mDatabase.onDisconnect();
    }

    @Override
    public void updateDatabase() {
        HashMap<String, Object> mMap = new HashMap<>();
        for (UserModel user : mList) {
            mMap.put(user.getName(), user.getUser());
        }
        mDatabase.child("users").child(mUserId).child("mapOfUsers").updateChildren(mMap);
    }

    @Override
    public void stopCallBack() {
        mDatabase.removeEventListener(valueEventListener);
    }
}
