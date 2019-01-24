package com.erproject.busgo.views.registration;

import com.erproject.busgo.data.Repository;
import com.erproject.busgo.data.data.request.UserRegistrationRequest;
import com.erproject.busgo.data.data.responses.BaseResponse;
import com.erproject.busgo.utils.ErrorConverter;

import javax.inject.Inject;

import retrofit2.HttpException;
import rx.Subscriber;

public class RegistrationActivityPresenter implements RegistrationActivityContract.Presenter {
    private final Repository mRepository;
    private RegistrationActivityContract.View mView;

    @Inject
    RegistrationActivityPresenter(Repository repository) {
        this.mRepository = repository;
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
    public void sendRegistration(final String username, final String email, final String phone, final String password) {
        UserRegistrationRequest user = new UserRegistrationRequest(username, email, password, phone);
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
                mView.doneRegistration(email, password, baseResponse.getmToken());
            }
        });
    }
}
