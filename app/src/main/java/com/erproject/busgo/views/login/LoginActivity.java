package com.erproject.busgo.views.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseActivityDagger;
import com.erproject.busgo.customViews.FixedTextInputEditText;
import com.erproject.busgo.services.authManager.AuthController;
import com.erproject.busgo.utils.TextValidWatcher;
import com.erproject.busgo.views.main.MainActivity;
import com.erproject.busgo.views.registration.RegistrationActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends BaseActivityDagger implements LoginActivityContract.View {
    //password
    @BindView(R.id.etl_password)
    TextInputLayout mLayoutPasword;
    @BindView(R.id.et_password)
    FixedTextInputEditText mEtPassword;
    //email
    @BindView(R.id.etl_mail)
    TextInputLayout mLayoutEmail;
    @BindView(R.id.et_mail)
    FixedTextInputEditText mEtMail;
    //other
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.text_error)
    TextView mTextError;

    @Inject
    LoginActivityPresenter presenter;

    public static Intent newInstance(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @OnTextChanged(R.id.et_password)
    public void changedTextPassword() {
        mLayoutPasword.setErrorEnabled(false);
        mLayoutPasword.setError(null);
    }

    @OnTextChanged(R.id.et_mail)
    public void changedTextEmail() {
        mLayoutEmail.setErrorEnabled(false);
        mLayoutEmail.setError(null);
    }

    //button login
    @OnClick(R.id.btn_resume)
    public void onResumeClicked() {
        mTextError.setVisibility(View.GONE);
        if (checkValid()) {
            mProgress.setVisibility(View.VISIBLE);
            presenter.sendLogin(mEtMail.getText().toString(), mEtPassword.getText().toString());
        }
    }

    private boolean checkValid() {
        boolean isCanLogin = true;
        int validCounter = 0;

        if (mEtMail.getText().toString().isEmpty()) {
            validCounter++;
            mLayoutEmail.setError("Email is empty");
        } else if (TextValidWatcher.isEmailValid(mEtMail.getText().toString())) {
            validCounter++;
            mLayoutEmail.setError("Email isn`t correct");
        }
        if (mEtPassword.getText().toString().isEmpty()) {
            validCounter++;
            mLayoutPasword.setError("Password is empty");
        } else if (mEtPassword.getText().toString().length() < 6) {
            validCounter++;
            mLayoutPasword.setError("Password must be more than 6 characters.");
        }

        if (validCounter > 0)
            isCanLogin = false;

        return isCanLogin;
    }

    @OnClick(R.id.text_sign_up)
    public void onSignUpClicked() {
        startActivity(RegistrationActivity.newInstance(this));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void showError(String msg) {
        mProgress.setVisibility(View.GONE);
        mTextError.setVisibility(View.VISIBLE);
        mTextError.setText(msg);
        showToastError(msg);
    }

    @Override
    public void showToastError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doneLogin(String username, String password, String token) {
        mProgress.setVisibility(View.GONE);
        if (!token.isEmpty()) {
            AuthController.addAccount(getApplicationContext(), username, password, token);
            Intent a = MainActivity.newInstance(this);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }
}
