package com.erproject.busgo.views.main.fragmentLoadTrack.clearUsers.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.simpleData.UserModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClearUsersHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_phones_text)
    TextView mTitle;

    private ClearUsersAdapter.OnItemClicked mOnClick;

    ClearUsersHolder(@NonNull View itemView, ClearUsersAdapter.OnItemClicked mOnClick) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mOnClick = mOnClick;
    }

    public void bind(UserModel user) {
        if (user != null && user.getUser() != null && user.getUser().getPhone() != null) {
            if (user.getUser().getIsUsed()) mTitle.setText(
                    String.format("%s %s", user.getUser().getUserName(),
                            itemView.getContext().getString(R.string.string_active)));
            else mTitle.setText(user.getUser().getUserName());
            itemView.setOnClickListener(v -> mOnClick.onClick(user));
        }
    }
}
