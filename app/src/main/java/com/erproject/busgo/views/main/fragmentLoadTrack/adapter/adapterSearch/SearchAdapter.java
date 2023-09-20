package com.erproject.busgo.views.main.fragmentLoadTrack.adapter.adapterSearch;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erproject.busgo.R;
import com.erproject.busgo.data.data.request.fbRegistration.FbConnectedUser;
import com.erproject.busgo.data.data.simpleData.UserModel;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {
    private final Context mContext;
    private final OnSearchClicked mCallback;
    private List<UserModel> list;

    public SearchAdapter(Context context, OnSearchClicked callback) {
        this.mContext = context;
        this.mCallback = callback;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(mContext).inflate(R.layout.item_search_user, viewGroup, false);
        return new SearchHolder(view, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder searchHolder, int i) {
        searchHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public interface OnSearchClicked {
        void onClicked(FbConnectedUser user);
    }

    public void setList(List<UserModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
