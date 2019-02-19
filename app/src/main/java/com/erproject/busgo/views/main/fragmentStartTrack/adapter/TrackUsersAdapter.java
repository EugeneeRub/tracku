package com.erproject.busgo.views.main.fragmentStartTrack.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.ArrayList;

public class TrackUsersAdapter extends RecyclerView.Adapter<TrackUsersHolder> {
    private ArrayList<UserModel> list;
    private Context mContext;
    private OnClickItem mClickedItem;

    public TrackUsersAdapter(Context context, OnClickItem mClickedItem) {
        this.mContext = context;
        this.mClickedItem = mClickedItem;
    }

    @NonNull
    @Override
    public TrackUsersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(mContext).inflate(R.layout.item_list_track, viewGroup, false);
        return new TrackUsersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackUsersHolder trackUsersHolder, int i) {
        trackUsersHolder.bind(list.get(i), mClickedItem);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void setList(ArrayList<UserModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface OnClickItem {
        void onClick(String name);
    }
}
