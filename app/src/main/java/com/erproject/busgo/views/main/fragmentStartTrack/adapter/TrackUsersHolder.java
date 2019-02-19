package com.erproject.busgo.views.main.fragmentStartTrack.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.simpleData.UserModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackUsersHolder extends RecyclerView.ViewHolder {
    private UserModel mUserModel;

    @BindView(R.id.item_list_track_text_title)
    TextView mTextTitle;
    @BindView(R.id.item_list_track_layout)
    ConstraintLayout mLayoutItem;

    TrackUsersHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(UserModel userModel, final TrackUsersAdapter.OnClickItem callback) {
        mUserModel = userModel;
        mLayoutItem.setBackgroundResource(android.R.color.white);
        mTextTitle.setText("");

        if (!userModel.getUser().getmIsUsed()) {
            mTextTitle.setText(userModel.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mUserModel.getUser().getmIsUsed()) {
                        mUserModel.getUser().setmIsUsed(true);
                        mLayoutItem.setBackgroundResource(R.color.colorItemUsed);
                        mTextTitle.setText(String.format("%s (in use)", mUserModel.getName()));
                    } else {
                        mUserModel.getUser().setmIsUsed(false);
                        mLayoutItem.setBackgroundResource(android.R.color.white);
                        mTextTitle.setText(mUserModel.getName());
                    }
                    callback.onClick(mUserModel.getName());
                }
            });
        } else {
            mTextTitle.setText(String.format("%s (in use)", mUserModel.getName()));
        }
    }

}
