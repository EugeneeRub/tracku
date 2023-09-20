package com.erproject.busgo.views.registration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseActivityDagger;
import com.erproject.busgo.customViews.FixedTextInputEditText;
import com.erproject.busgo.data.data.responses.SignUpResponseError;
import com.erproject.busgo.services.authManager.AuthController;
import com.erproject.busgo.utils.TextValidWatcher;
import com.erproject.busgo.views.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegistrationActivity extends BaseActivityDagger
        implements RegistrationActivityContract.View {

    @Inject
    RegistrationActivityPresenter mPresenter;

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
    //unique code
    @BindView(R.id.etl_unique_code)
    TextInputLayout mLayoutUniqueCode;
    @BindView(R.id.et_unique_code)
    FixedTextInputEditText mEtUniqueCode;
    //password
    @BindView(R.id.etl_password)
    TextInputLayout mLayoutPassword;
    @BindView(R.id.et_password)
    FixedTextInputEditText mEtPassword;

    //other
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.text_error)
    TextView mTextError;

    AlertDialog.Builder builder;

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

    @OnTextChanged(R.id.et_unique_code)
    public void changedTextUniqeCode() {
        mLayoutUniqueCode.setErrorEnabled(false);
        mLayoutUniqueCode.setError(null);
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
    @SuppressWarnings("all")
    public void onRegisterClicked() {
        mTextError.setVisibility(View.GONE);
        if (checkValid()) {
            mProgress.setVisibility(View.VISIBLE);
            mPresenter.sendRegistration(mEtUsername.getText().toString(),
                    mEtEmail.getText().toString(), mEtPhone.getText().toString(),
                    mEtPassword.getText().toString(), mEtUniqueCode.getText().toString());
        }
    }

    @SuppressWarnings("all")
    private boolean checkValid() {
        boolean isCanLogin = true;
        int validCounter = 0;

        if (mEtUsername.getText().toString().isEmpty()) {
            validCounter++;
            mLayoutUsername.setError(getString(R.string.string_username_is_empty));
        }

        if (mEtPhone.getText().toString().isEmpty()) {
            validCounter++;
            mLayoutPhone.setError(getString(R.string.string_phone_is_empty));
        }

        if (mEtEmail.getText().toString().isEmpty()) {
            validCounter++;
            mLayoutEmail.setError(getString(R.string.string_email_is_empty));
        } else if (TextValidWatcher.isEmailValid(mEtEmail.getText().toString())) {
            validCounter++;
            mLayoutEmail.setError(getString(R.string.string_email_is_not_correct));
        }

        if (mEtUniqueCode.getText().toString().isEmpty()) {
            validCounter++;
            mLayoutUniqueCode.setError(getString(R.string.string_unique_code_is_empty));
        } else {
            if (mEtUniqueCode.getText().toString().equals(mEtPassword.getText().toString())) {
                validCounter++;
                mLayoutUniqueCode.setError(getString(R.string.string_unique_code_can_not_match));
            }
        }

        if (mEtPassword.getText().toString().isEmpty()) {
            validCounter++;
            mLayoutPassword.setError(getString(R.string.string_password_is_empty));
        } else if (mEtPassword.getText().toString().length() < 6) {
            validCounter++;
            mLayoutPassword.setError(getString(R.string.string_password_must_be_six));
        }

        if (validCounter > 0) isCanLogin = false;

        return isCanLogin;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        prepareUniqueAlertNotification();
    }

    @Override
    public void showError(String msg) {
        mProgress.setVisibility(View.GONE);
        mTextError.setVisibility(View.VISIBLE);
        mTextError.setText(msg);
    }

    @Override
    public void goToLogin() {
        //gag
    }

    @Override
    public void showToastError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doneRegistration(String username, String password, String token) {
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
    public void setEmailError(SignUpResponseError error) {
        mProgress.setVisibility(View.GONE);
        if (error == null) return;
        if (error.getmEmailError() != null) mLayoutEmail.setError(error.getmEmailError());
        if (error.getmPhoneError() != null) mLayoutPhone.setError(error.getmPhoneError());
        if (error.getmUserNameError() != null) mLayoutUsername.setError(error.getmUserNameError());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void prepareUniqueAlertNotification() {
        builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        mEtUniqueCode.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (mEtUniqueCode.getRight() -
                        mEtUniqueCode.getCompoundDrawables()[2].getBounds().width())) {
                    builder.setTitle(R.string.string_unique_code_with_question);

                    builder.setMessage(R.string.string_unique_code_warning);
                    builder.setPositiveButton(R.string.string_ok, null);
                    builder.show();
                    return true;
                }
            }
            return false;
        });
    }
}
