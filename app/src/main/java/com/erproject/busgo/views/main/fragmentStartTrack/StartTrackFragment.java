package com.erproject.busgo.views.main.fragmentStartTrack;

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

public class StartTrackFragment extends BaseFragmentDagger implements StartTrackContract.View {
    @BindView(R.id.fragment_start_track_hide_all_layout)
    FloatingActionButton mImageArrow;
    @BindView(R.id.fragment_start_track_choose_fab)
    FloatingActionButton mChooseFab;
    @BindView(R.id.fragment_start_track_button_start)
    Button mButtonStartTrack;
    @BindView(R.id.fragment_start_track_card_choose)
    CardView mCardChoose;
    @BindView(R.id.fragment_start_track_card_list)
    RecyclerView mUsersList;

    private boolean isHidingAll;

    @Inject
    StartTrackPresenter mPresenter;

    @OnClick(R.id.fragment_start_track_hide_all_layout)
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

    @OnClick(R.id.fragment_start_track_choose_fab)
    public void onChooseClicked() {
        if (mCardChoose.getVisibility() == View.GONE) {
            mCardChoose.setVisibility(View.VISIBLE);

            mButtonStartTrack.setEnabled(false);
            mChooseFab.setEnabled(false);
            mImageArrow.setEnabled(false);
        }
    }

    @OnClick(R.id.fragment_start_track_card_text)
    public void onTextCloseClicked() {
        mCardChoose.setVisibility(View.GONE);
        mButtonStartTrack.setEnabled(true);
        mChooseFab.setEnabled(true);
        mImageArrow.setEnabled(true);
    }

    private void hideByAnimAllViews() {
        mButtonStartTrack.animate().alpha(0.0f).translationY(mButtonStartTrack.getHeight());
        mChooseFab.animate().alpha(0.0f).translationX(mChooseFab.getWidth());
    }

    private void showByAnimAllViews() {
        mButtonStartTrack.animate().alpha(1.0f).translationY(0);
        mButtonStartTrack.setVisibility(View.VISIBLE);
        mChooseFab.animate().alpha(1.0f).translationX(0);
    }

    @Inject
    public StartTrackFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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
    public void onStop() {
        super.onStop();
        mCardChoose.setVisibility(View.GONE);

        mButtonStartTrack.setEnabled(true);
        mChooseFab.setEnabled(true);
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
