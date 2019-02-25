package com.erproject.busgo.views.main.fragmentLoadTrack.phones.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.List;

public class PhonesAdapter extends RecyclerView.Adapter<PhonesHolder> {
    private List<UserModel> mList;

    public PhonesAdapter() {
    }

    @NonNull
    @Override
    public PhonesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_phones, viewGroup, false);
        return new PhonesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhonesHolder phonesHolder, int i) {
        phonesHolder.bind(mList.get(i));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void setList(List<UserModel> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
}
