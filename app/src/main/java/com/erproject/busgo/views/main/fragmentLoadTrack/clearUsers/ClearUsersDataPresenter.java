package com.erproject.busgo.views.main.fragmentLoadTrack.clearUsers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.erproject.busgo.BuildConfig;
import com.erproject.busgo.data.data.request.fbRegistration.FbUserRegistration;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.erproject.busgo.data.exceptions.UserNotFoundException;
import com.erproject.busgo.services.authManager.AuthController;
import com.erproject.busgo.utils.FireBaseWorker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import javax.inject.Inject;

public class ClearUsersDataPresenter implements ClearUsersDataContract.Presenter {
    @Nullable
    private ClearUsersDataContract.View mView;
    private DatabaseReference mDatabase;
    private String mUserId;

    @Inject
    ClearUsersDataPresenter() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void takeView(ClearUsersDataContract.View view) {
        this.mView = view;
        if (mUserId == null) {
            try {
                mUserId = AuthController.getLoginString(mView.getmContext());
                loadUsers(mUserId);
            } catch (UserNotFoundException e) {
                mView.goToLogin();
            }
        } else loadUsers(mUserId);
    }

    private void loadUsers(String mUserId) {
        mDatabase.child(BuildConfig.START_PATH).child(mUserId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FbUserRegistration user = dataSnapshot.getValue(FbUserRegistration.class);
                        if (mView != null && user != null)
                            mView.setItems(FireBaseWorker.getList(user.getMapOfUsers()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    @Override
    public void dropView() {
        this.mView = null;
    }

    @Override
    public void updateUser(UserModel user) {
        HashMap<String, Object> mMap = new HashMap<>();
        mMap.put(user.getName(), user.getUser());
        mDatabase.child(BuildConfig.START_PATH).child(mUserId).child(BuildConfig.MAP_USER_PATH)
                .updateChildren(mMap);
    }
}