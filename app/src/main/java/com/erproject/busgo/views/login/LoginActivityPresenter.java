package com.erproject.busgo.views.login;

import com.erproject.busgo.data.Repository;
import com.erproject.busgo.data.data.request.UserRegistrationRequest;
import com.erproject.busgo.data.data.responses.BaseResponse;
import com.erproject.busgo.utils.ErrorConverter;

import javax.inject.Inject;

import rx.Subscriber;

public class LoginActivityPresenter implements LoginActivityContract.Presenter {
    private final Repository mRepository;
    private LoginActivityContract.View mView;

    @Inject
    LoginActivityPresenter(Repository mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public void takeView(LoginActivityContract.View view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        this.mView = null;
    }

    @Override
    public void sendLogin(final String email, final String password) {
        UserRegistrationRequest user = new UserRegistrationRequest(email, password);

        mView.doneLogin(email, password, "token_example");

//        mRepository.signIn(user).subscribe(new Subscriber<BaseResponse>() {
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mView.showError(ErrorConverter.getMsgFromCode(e));
//            }
//
//            @Override
//            public void onNext(BaseResponse baseResponse) {
//                mView.doneLogin(email, password, "token_example");
//            }
//        });
    }
}
