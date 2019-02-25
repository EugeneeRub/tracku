package com.erproject.busgo.views.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.erproject.busgo.BuildConfig;
import com.erproject.busgo.data.Repository;
import com.erproject.busgo.data.data.request.fbRegistration.FbUserRegistration;
import com.erproject.busgo.data.exceptions.UserNotFoundException;
import com.erproject.busgo.data.sharedPreferences.LocalSharedPreferences;
import com.erproject.busgo.data.sharedPreferences.LocalSharedPreferencesSource;
import com.erproject.busgo.services.authManager.AuthController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    private final Repository repository;
    @Inject
    LocalSharedPreferencesSource mSharedPreferencesSource;
    @Nullable
    private MainActivityContract.View mView;
    private DatabaseReference mDatabase;
    private FbUserRegistration mUser;
    private boolean mIsUserEnterTheUniqeCode;

    @Inject
    MainActivityPresenter(Repository repository) {
        this.repository = repository;
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void saveStringInPref(LocalSharedPreferences localSharedPreferences, String city) {
        mSharedPreferencesSource.setString(localSharedPreferences, city);
    }

    @Override
    public String getStringFromPref(LocalSharedPreferences pref) {
        return mSharedPreferencesSource.getString(pref);
    }

    @Override
    public boolean isUserEnterTheUniqueCode() {
        return mIsUserEnterTheUniqeCode;
    }

    @Override
    public void setUserEnterTheUniqueCode(boolean b) {
        this.mIsUserEnterTheUniqeCode = b;
    }

    @Override
    public boolean checkAndSaveEnteredUniqueCode(String code) {
        if (mUser == null || mUser.getmUniqueCode() == null || mUser.getmUniqueCode().isEmpty())
            return false;
        this.mIsUserEnterTheUniqeCode = mUser.getmUniqueCode().equals(code);
        return this.mIsUserEnterTheUniqeCode;
    }

    @Override
    public void takeView(MainActivityContract.View view) {
        this.mView = view;
        if (mUser == null) {
            try {
                String id = AuthController.getLoginString(mView.getmContext());
                loadUser(id);
            } catch (UserNotFoundException e) {
                mView.goToLogin();
            }
        }
    }

    private void loadUser(String userId) {
        mDatabase.child(BuildConfig.START_PATH).child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mUser = dataSnapshot.getValue(FbUserRegistration.class);
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
