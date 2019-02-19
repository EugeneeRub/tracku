package com.erproject.busgo.views.main.fragmentLoadTrack.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.ArrayList;

public class LoadUsersAdapter extends RecyclerView.Adapter<LoadUsersHolder> {
    ArrayList<UserModel> list;

    private Context mContext;

    public LoadUsersAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public LoadUsersHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(mContext).inflate(R.layout.item_list_monitor, viewGroup, false);
        return new LoadUsersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadUsersHolder loadUsersHolder, int i) {
        loadUsersHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void setList(ArrayList<UserModel> list) {
        this.list = list;
    }
}
