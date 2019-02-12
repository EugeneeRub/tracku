package com.erproject.busgo.views.registration;

import com.erproject.busgo.data.Repository;
import com.erproject.busgo.data.data.request.UserRegistrationRequest;
import com.erproject.busgo.data.data.request.fbRegistration.FbConnectedUser;
import com.erproject.busgo.data.data.request.fbRegistration.FbUserRegistration;
import com.erproject.busgo.data.data.responses.BaseResponse;
import com.erproject.busgo.utils.ErrorConverter;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.HttpException;
import rx.Subscriber;

public class RegistrationActivityPresenter implements RegistrationActivityContract.Presenter {
    private final Repository mRepository;
    private RegistrationActivityContract.View mView;
    private DatabaseReference mDatabase;

    @Inject
    RegistrationActivityPresenter(Repository repository) {
        this.mRepository = repository;
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void takeView(RegistrationActivityContract.View view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        this.mView = null;
    }

    @Override
    public void sendRegistration(final String username, final String email, final String phone,
                                 final String password, final String unique) {
        final UserRegistrationRequest user =
                new UserRegistrationRequest(username, email, password, unique, phone);
        mRepository.signUp(user).subscribe(new Subscriber<BaseResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (((HttpException) e).code() == 409) {
                    mView.setEmailError(ErrorConverter.getErrorAuth(e).getmEmailError());
                } else {
                    mView.showError(ErrorConverter.getMsgFromCode(e));
                }
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                registerUserInFirebase(user, baseResponse.getmToken());
            }
        });
    }

    private void registerUserInFirebase(final UserRegistrationRequest user, final String token) {
        FbUserRegistration fbUser = new FbUserRegistration();
        //base data
        String[] emailSplitted = user.getmEmail().split("@");
        fbUser.setmEmailSuffix(emailSplitted[1]);
        fbUser.setmRegisterPhone(user.getmPhoneNumber());
        Map<String, Object> map = new HashMap<>();
        map.put("User 1", new FbConnectedUser());
        fbUser.setMapOfUsers(map);
        fbUser.setmUniqueCode(user.getmUniqueCode());

        //add first user
        mDatabase.child("users").child(emailSplitted[0]).setValue(fbUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mView.doneRegistration(user.getmEmail(), user.getmPassword(), token);
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                mView.showError(ErrorConverter.getMsgFromCode(new Throwable()));
            }
        });
    }
}
