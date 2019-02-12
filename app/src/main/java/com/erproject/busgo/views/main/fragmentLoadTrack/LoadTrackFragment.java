package com.erproject.busgo.views.main.fragmentLoadTrack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
    @BindView(R.id.fragment_load_track_edit_fab)
    FloatingActionButton mFabEdit;
    @BindView(R.id.fragment_load_track_card_edit)
    CardView mCardEdit;
    @BindView(R.id.fragment_load_track_button_start)
    Button mButtonLoadTrack;
    @BindView(R.id.fragment_load_track_card_list)
    RecyclerView mUsersList;

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

    @OnClick(R.id.fragment_load_track_edit_fab)
    public void onEditClicked() {
        if (mCardEdit.getVisibility() == View.GONE) {
            mCardEdit.setVisibility(View.VISIBLE);

            mButtonLoadTrack.setEnabled(false);
            mFabEdit.setEnabled(false);
            mImageArrow.setEnabled(false);
        }
    }

    @OnClick(R.id.fragment_load_track_card_text)
    public void onTextCloseClicked() {
        mCardEdit.setVisibility(View.GONE);
        mButtonLoadTrack.setEnabled(true);
        mFabEdit.setEnabled(true);
        mImageArrow.setEnabled(true);
    }

    private void hideByAnimAllViews() {
        mButtonLoadTrack.animate().alpha(0.0f).translationY(mButtonLoadTrack.getHeight());
        mFabEdit.animate().alpha(0.0f).translationX(mFabEdit.getWidth());
    }

    private void showByAnimAllViews() {
        mButtonLoadTrack.animate().alpha(1.0f).translationY(0);
        mButtonLoadTrack.setVisibility(View.VISIBLE);
        mFabEdit.animate().alpha(1.0f).translationX(0);
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
    public void onStop() {
        super.onStop();
        mCardEdit.setVisibility(View.GONE);

        mButtonLoadTrack.setEnabled(true);
        mFabEdit.setEnabled(true);
        mImageArrow.setEnabled(true);
    }

    @Override
    public void showError(String msg) {
    }

    @Override
    public void goToLogin() {
        startLoginActivity();
    }
}
