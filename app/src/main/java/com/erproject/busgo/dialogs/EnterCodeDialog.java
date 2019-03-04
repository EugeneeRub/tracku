package com.erproject.busgo.dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.erproject.busgo.R;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatDialogFragment;

public class EnterCodeDialog extends DaggerAppCompatDialogFragment {
    @BindView(R.id.dialog_enter_code_layout1)
    ConstraintLayout mConstraint1;
    @BindView(R.id.dialog_enter_code_layout2)
    ConstraintLayout mConstraint2;
    @BindView(R.id.dialog_enter_code_error_text)
    TextView mTextViewError;
    @BindView(R.id.dialog_enter_code_edit)
    EditText mEditText;

    private OnCheckCallback mCallback;

    @OnClick(R.id.btn_next)
    public void onClickNext() {
        mConstraint1.setVisibility(View.GONE);
        mConstraint2.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_check)
    public void onClickCheck() {
        mTextViewError.setVisibility(View.GONE);
        if (checkValid()) {
            mCallback.checkCode(mEditText.getText().toString());
        }
    }

    private boolean checkValid() {
        if (mEditText.getText().toString().isEmpty()) {
            mTextViewError.setVisibility(View.VISIBLE);
            mTextViewError.setText(getString(R.string.string_unique_code_is_empty));
            return false;
        }
        return true;
    }

    @Inject
    public EnterCodeDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()), getTheme());
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(dialog.getWindow().getAttributes());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.5f);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_enter_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        view.setMinimumWidth(R.dimen.fake_infinite);
    }

    public void setmCallback(OnCheckCallback mCallback) {
        this.mCallback = mCallback;
    }


    public interface OnCheckCallback {
        void checkCode(String code);
    }

}
