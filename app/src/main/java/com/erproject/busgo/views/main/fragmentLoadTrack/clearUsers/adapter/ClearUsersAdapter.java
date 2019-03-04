package com.erproject.busgo.views.main.fragmentLoadTrack.clearUsers.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.List;

public class ClearUsersAdapter extends RecyclerView.Adapter<ClearUsersHolder> {
    private List<UserModel> mList;
    private OnItemClicked mOnClick;

    public ClearUsersAdapter(OnItemClicked onClickListener) {
        this.mOnClick = onClickListener;
    }

    @NonNull
    @Override
    public ClearUsersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_clear_user_data, viewGroup, false);
        return new ClearUsersHolder(view, mOnClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ClearUsersHolder clearUsersHolder, int i) {
        clearUsersHolder.bind(mList.get(i));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void setList(List<UserModel> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public interface OnItemClicked {
        void onClick(UserModel user);
    }
}
