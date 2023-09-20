package com.erproject.busgo.views.main.fragmentLoadTrack.editUsers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

public class EditUsersPresenter implements EditUsersContract.Presenter {
    @Nullable
    private EditUsersContract.View mView;
    private DatabaseReference mDatabase;
    private String mUserId;

    @Inject
    EditUsersPresenter() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void takeView(EditUsersContract.View view) {
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
                .addValueEventListener(new ValueEventListener() {
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
    public void updateUser(UserModel model) {
        HashMap<String, Object> mMap = new HashMap<>();
        mMap.put(model.getName(), model.getUser());
        mDatabase.child(BuildConfig.START_PATH).child(mUserId).child(BuildConfig.MAP_USER_PATH)
                .updateChildren(mMap);
    }
}
