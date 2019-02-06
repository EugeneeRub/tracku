package com.erproject.busgo.views.main.fragmentStartTrack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseFragmentDagger;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class StartTrackFragment extends BaseFragmentDagger implements StartTrackContract.View {

    @Inject
    StartTrackPresenter mPresenter;

    @Inject
    public StartTrackFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_track, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void showError(String msg) {

    }
}
