package com.erproject.busgo.views.main.fragmentLoadTrack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseFragmentDagger;
import com.getbase.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoadTrackFragment extends BaseFragmentDagger implements LoadTrackContract.View {
    @BindView(R.id.fragment_load_track_hide_all_layout)
    FloatingActionButton mImageArrow;
    @BindView(R.id.fragment_load_track_button_start)
    Button mButtonLoadTrack;
    private boolean isHidingAll;

    @Inject
    LoadTrackPresenter mPresenter;

    @OnClick(R.id.fragment_load_track_hide_all_layout)
    public void onArrowClicked() {
        if (!isHidingAll) {
            isHidingAll = true;
            mImageArrow.setIcon(R.drawable.ic_arrow_up_green);
            hideByAnimAllViews();
        } else {
            isHidingAll = false;
            mImageArrow.setIcon(R.drawable.ic_arrow_down_green);
            showByAnimAllViews();
        }
    }

    private void hideByAnimAllViews() {
        mButtonLoadTrack.animate().alpha(0.0f).translationY(mButtonLoadTrack.getHeight());
    }

    private void showByAnimAllViews() {
        mButtonLoadTrack.animate().alpha(1.0f).translationY(0);
        mButtonLoadTrack.setVisibility(View.VISIBLE);
    }

    @Inject
    public LoadTrackFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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

    @Override
    public void goToLogin() {
        startLoginActivity();
    }
}
