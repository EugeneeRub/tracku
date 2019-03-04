package com.erproject.busgo.views.main.fragmentLoadTrack.phones.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.simpleData.UserModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhonesHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_phones_text)
    TextView mTitle;
    @BindView(R.id.item_phones_phone)
    TextView mPhone;

    PhonesHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(UserModel user) {
        if (user != null && user.getUser() != null && user.getUser().getPhone() != null) {
            mTitle.setText(user.getUser().getUserName());
            mPhone.setText(user.getUser().getPhone());

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + user.getUser().getPhone()));
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
