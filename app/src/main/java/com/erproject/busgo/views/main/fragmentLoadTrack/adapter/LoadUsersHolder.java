package com.erproject.busgo.views.main.fragmentLoadTrack.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.simpleData.UserModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoadUsersHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_list_monitor_chb)
    CheckBox mCheckbox;
    @BindView(R.id.item_list_monitor_text_title)
    TextView mTextTitle;
    private UserModel model;

    @OnClick(R.id.item_list_monitor_img_icon)
    public void onImageClick() {

    }

    LoadUsersHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckbox.isChecked()) mCheckbox.setChecked(false);
                else mCheckbox.setChecked(true);

                model.setMonitorUsed(mCheckbox.isChecked());
            }
        });
    }

    public void bind(UserModel userModel) {
        this.model = userModel;
        mTextTitle.setText(userModel.getName());
    }
}
