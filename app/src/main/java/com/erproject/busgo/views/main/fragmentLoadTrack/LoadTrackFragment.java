package com.erproject.busgo.views.main.fragmentLoadTrack;

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

public class LoadTrackFragment extends BaseFragmentDagger implements LoadTrackContract.View {

    @Inject
    LoadTrackPresenter mPresenter;

    @Inject
    public LoadTrackFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_load_track, container, false);
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
