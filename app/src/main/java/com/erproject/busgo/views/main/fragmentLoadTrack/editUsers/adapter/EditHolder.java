package com.erproject.busgo.views.main.fragmentLoadTrack.editUsers.adapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.erproject.busgo.views.main.fragmentLoadTrack.editUsers.EditUsersActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditHolder extends RecyclerView.ViewHolder {
    private EditUsersActivity.OnItemEdit mOnItemEdit;
    private final String DISABLE = "Disable";
    private final String ENABLE = "Enable";
    private UserModel mModel;

    @BindView(R.id.item_edit_users_enable_img)
    ImageView mImageEnable;
    @BindView(R.id.item_edit_users_input_name)
    EditText mEditTextName;
    @BindView(R.id.item_edit_users_input_phone)
    EditText mEditTextPhone;

    @OnClick(R.id.item_edit_users_enable_img)
    void onEnableImageClicked() {
        if (mImageEnable.getTag().equals(DISABLE)) {
            mImageEnable.setTag(ENABLE);
            mImageEnable.setBackground(null);
            mImageEnable.setBackground(
                    ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_done_green));
            mEditTextName.setEnabled(true);
            mEditTextPhone.setEnabled(true);
        } else if (mImageEnable.getTag().equals(ENABLE)) {
            mImageEnable.setTag(DISABLE);
            mImageEnable.setBackground(null);
            mImageEnable.setBackground(
                    ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_edit_green));
            mEditTextName.setEnabled(false);
            mEditTextPhone.setEnabled(false);
            if (mOnItemEdit != null && mModel != null) {
                if (!mEditTextName.getText().toString().isEmpty()) {
                    mModel.getUser().setUserName(mEditTextName.getText().toString());
                    mModel.getUser().setPhone(mEditTextPhone.getText().toString());
                    mOnItemEdit.onItemEdit(mModel);
                } else {
                    Toast.makeText(itemView.getContext(),
                            itemView.getContext().getString(R.string.string_username_error),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    EditHolder(@NonNull View itemView, EditUsersActivity.OnItemEdit onItemEdit) {
        super(itemView);
        this.mOnItemEdit = onItemEdit;
        ButterKnife.bind(this, itemView);
    }

    public void bind(UserModel model) {
        this.mModel = model;
        mImageEnable.setTag(DISABLE);
        mImageEnable.setBackground(
                ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_edit_green));
        if (mModel != null && mModel.getUser() != null) {
            mEditTextName.setText(mModel.getUser().getUserName());
            if (!mModel.getUser().getPhone().isEmpty())
                mEditTextPhone.setText(mModel.getUser().getPhone());
        }
    }
}
