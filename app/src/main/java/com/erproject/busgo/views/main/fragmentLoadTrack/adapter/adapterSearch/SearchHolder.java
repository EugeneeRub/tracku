package com.erproject.busgo.views.main.fragmentLoadTrack.adapter.adapterSearch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.erproject.busgo.views.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchHolder extends RecyclerView.ViewHolder {
    private final SearchAdapter.OnSearchClicked mCallBack;
    @BindView(R.id.item_search_user)
    TextView mTextFind;

    SearchHolder(@NonNull View itemView, SearchAdapter.OnSearchClicked callBack) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mCallBack = callBack;
    }

    public void bind(UserModel user) {
        if (user.getUser().getIsUsed()) mTextFind.setText(
                String.format("%s %s", user.getUser().getUserName(),
                        itemView.getContext().getString(R.string.string_active)));
        else mTextFind.setText(user.getUser().getUserName());

        itemView.setOnClickListener(v -> {
            if (user.getUser().getIsUsed()) {
                mCallBack.onClicked(user.getUser());
            } else {
                Toast.makeText(itemView.getContext(),
                        itemView.getContext().getString(R.string.string_can_not_find_user),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
