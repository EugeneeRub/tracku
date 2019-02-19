package com.erproject.busgo.views.main.fragmentLoadTrack;

import android.support.annotation.NonNull;

import com.erproject.busgo.data.data.request.fbRegistration.FbUserRegistration;
import com.erproject.busgo.data.exceptions.UserNotFoundException;
import com.erproject.busgo.services.authManager.AuthController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class LoadTrackPresenter implements LoadTrackContract.Presenter {

    @Nullable
    private LoadTrackContract.View mView;
    private DatabaseReference mDatabase;
    private FbUserRegistration mUser;

    @Inject
    LoadTrackPresenter() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void takeView(LoadTrackContract.View view) {
        this.mView = view;
        if (mUser == null) {
            try {
                String id = AuthController.getLoginString(mView.getmContext());
                loadAndTrackUser(id);
            } catch (UserNotFoundException e) {
                mView.goToLogin();
            }
        }
    }

    private void loadAndTrackUser(String userId) {
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser = dataSnapshot.getValue(FbUserRegistration.class);
                if (mView != null && mUser != null) {
                    mView.updateState(mUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (mView != null) {
                    mView.showError(databaseError.getMessage());
                }
            }
        });
    }

    @Override
    public void dropView() {
        this.mView = null;
        this.mDatabase.onDisconnect();
    }
}
