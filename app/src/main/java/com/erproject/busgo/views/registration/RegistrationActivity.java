package com.erproject.busgo.views.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseActivityDagger;
import com.erproject.busgo.customViews.FixedTextInputEditText;
import com.erproject.busgo.utils.TextValidWatcher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegistrationActivity extends BaseActivityDagger
        implements RegistrationActivityContract.View {

    @Inject
    RegistrationActivityPresenter presenter;

    //username
    @BindView(R.id.etl_username)
    TextInputLayout mLayoutUsername;
    @BindView(R.id.et_username)
    FixedTextInputEditText mEtUsername;
    //email
    @BindView(R.id.etl_email)
    TextInputLayout mLayoutEmail;
    @BindView(R.id.et_email)
    FixedTextInputEditText mEtEmail;
    //phone
    @BindView(R.id.etl_phone)
    TextInputLayout mLayoutPhone;
    @BindView(R.id.et_phone)
    FixedTextInputEditText mEtPhone;
    //email
    @BindView(R.id.etl_password)
    TextInputLayout mLayoutPassword;
    @BindView(R.id.et_password)
    FixedTextInputEditText mEtPassword;

    //other
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.text_error)
    TextView mTextError;

    public static Intent newInstance(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    @OnTextChanged(R.id.et_username)
    public void changedTextUsername() {
        mLayoutUsername.setErrorEnabled(false);
        mLayoutUsername.setError(null);
    }

    @OnTextChanged(R.id.et_email)
    public void changedTextEmail() {
        mLayoutEmail.setErrorEnabled(false);
        mLayoutEmail.setError(null);
    }

    @OnTextChanged(R.id.et_phone)
    public void changedTextPhone() {
        mLayoutPhone.setErrorEnabled(false);
        mLayoutPhone.setError(null);
    }

    @OnTextChanged(R.id.et_password)
    public void changedTextPassword() {
        mLayoutPassword.setErrorEnabled(false);
        mLayoutPassword.setError(null);
    }

    @OnClick(R.id.text_sign_up)
    public void onLoginClicked() {
        finish();
    }

    @OnClick(R.id.btn_resume)
    public void onRegisterClicked() {
        mTextError.setVisibility(View.GONE);
        if (checkValid()) {
            mProgress.setVisibility(View.VISIBLE);
            presenter.sendRegistration(mEtUsername.getText().toString(),
                    mEtEmail.getText().toString(),
                    mEtPhone.getText().toString(),
                    mEtPassword.getText().toString());
        }
    }

    private boolean checkValid() {
        boolean isCanLogin = true;
        int validCounter = 0;

        if (mEtUsername.getText().toString().isEmpty()) {
            validCounter++;
            mLayoutUsername.setError("Username is empty");
        }

        if (mEtPhone.getText().toString().isEmpty()) {
            validCounter++;
            mLayoutPhone.setError("Phone is empty");
        }

        if (mEtEmail.getText().toString().isEmpty()) {
            validCounter++;
            mLayoutEmail.setError("Email is empty");
        } else if (TextValidWatcher.isEmailValid(mEtEmail.getText().toString())) {
            validCounter++;
            mLayoutEmail.setError("Email isn`t correct");
        }

        if (mEtPassword.getText().toString().isEmpty()) {
            validCounter++;
            mLayoutPassword.setError("Password is empty");
        } else if (mEtPassword.getText().toString().length() < 6) {
            validCounter++;
            mLayoutPassword.setError("Password must be more than 6 characters.");
        }

        if (validCounter > 0)
            isCanLogin = false;

        return isCanLogin;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
    }

    @Override
    public void showError(String msg) {
        mProgress.setVisibility(View.GONE);
        mTextError.setVisibility(View.VISIBLE);
        mTextError.setText(msg);
    }

    @Override
    public void doneRegistration(String username, String password, String token) {
        mProgress.setVisibility(View.GONE);
//        if (!token.isEmpty()) {
//            AuthController.addAccount(getApplicationContext(), username, password, token);
//            startActivity(MainActivity.newInstance(this));
//        }
    }

    @Override
    public void setEmailError(String error) {
        mLayoutEmail.setError("Email already exists");
    }
}
