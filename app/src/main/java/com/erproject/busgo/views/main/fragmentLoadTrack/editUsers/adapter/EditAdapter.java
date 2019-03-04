package com.erproject.busgo.views.main.fragmentLoadTrack.editUsers.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.erproject.busgo.views.main.fragmentLoadTrack.editUsers.EditUsersActivity;

import java.util.List;

public class EditAdapter extends RecyclerView.Adapter<EditHolder> {
    private EditUsersActivity.OnItemEdit mOnItemEdit;
    private List<UserModel> mList;

    public EditAdapter(EditUsersActivity.OnItemEdit onItemEdit) {
        this.mOnItemEdit = onItemEdit;
    }

    @NonNull
    @Override
    public EditHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_edit_users, viewGroup, false);
        return new EditHolder(view, mOnItemEdit);
    }

    @Override
    public void onBindViewHolder(@NonNull EditHolder editHolder, int i) {
        editHolder.bind(mList.get(i));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void setList(List<UserModel> list) {
        this.mList = list;
        notifyDataSetChanged();
    }
}
