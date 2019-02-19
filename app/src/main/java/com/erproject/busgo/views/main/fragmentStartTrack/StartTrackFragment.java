package com.erproject.busgo.views.main.fragmentStartTrack;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseFragmentDagger;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
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

    @BindView(R.id.fragment_start_track_id1)
    CheckBox mBtnUser1;
    @BindView(R.id.fragment_start_track_id2)
    CheckBox mBtnUser2;
    @BindView(R.id.fragment_start_track_id3)
    CheckBox mBtnUser3;
    @BindView(R.id.fragment_start_track_id4)
    CheckBox mBtnUser4;

    private boolean isHidingAll;

    @Inject
    StartTrackPresenter mPresenter;

    @OnCheckedChanged({R.id.fragment_start_track_id1, R.id.fragment_start_track_id2,
            R.id.fragment_start_track_id3, R.id.fragment_start_track_id4})
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        switch (button.getId()) {
            case R.id.fragment_start_track_id1:
                if (isChecked) {
                    mBtnUser2.setEnabled(false);
                    mBtnUser3.setEnabled(false);
                    mBtnUser4.setEnabled(false);
                } else {
                    mBtnUser2.setEnabled(true);
                    mBtnUser3.setEnabled(true);
                    mBtnUser4.setEnabled(true);
                }
                break;
            case R.id.fragment_start_track_id2:
                if (isChecked) {
                    mBtnUser1.setEnabled(false);
                    mBtnUser3.setEnabled(false);
                    mBtnUser4.setEnabled(false);
                } else {
                    mBtnUser1.setEnabled(true);
                    mBtnUser3.setEnabled(true);
                    mBtnUser4.setEnabled(true);
                }
                break;
            case R.id.fragment_start_track_id3:
                if (isChecked) {
                    mBtnUser1.setEnabled(false);
                    mBtnUser2.setEnabled(false);
                    mBtnUser4.setEnabled(false);
                } else {
                    mBtnUser1.setEnabled(true);
                    mBtnUser2.setEnabled(true);
                    mBtnUser4.setEnabled(true);
                }
                break;
            case R.id.fragment_start_track_id4:
                if (isChecked) {
                    mBtnUser1.setEnabled(false);
                    mBtnUser2.setEnabled(false);
                    mBtnUser3.setEnabled(false);
                } else {
                    mBtnUser1.setEnabled(true);
                    mBtnUser2.setEnabled(true);
                    mBtnUser3.setEnabled(true);
                }
                break;
        }
    }

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

        prepareUsers();
        return view;
    }

    private void prepareUsers() {
        hideRadioButtons();
        showButtons(mPresenter.getList());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clearBeforeStopping();
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

    @Override
    public Context getmContext() {
        return getContext();
    }

    @Override
    public void updateState(List<UserModel> list) {
        hideRadioButtons();
        showButtons(list);
    }

    @SuppressWarnings({"all"})
    private void showButtons(List<UserModel> list) {
        if (list == null || list.isEmpty()) return;
        for (int i = 0; i < list.size(); i++) {
            UserModel model = list.get(i);
            if (i == 0) {
                showRadioButton(mBtnUser1, model);
            }
            if (i == 1) {
                showRadioButton(mBtnUser2, model);
            }
            if (i == 2) {
                showRadioButton(mBtnUser3, model);
            }
            if (i == 3) {
                showRadioButton(mBtnUser4, model);
            }
        }
    }

    private void showRadioButton(final CheckBox button, final UserModel model) {
        button.setVisibility(View.VISIBLE);
        if (!model.getUser().getmIsUsed()) {
            button.setEnabled(true);
            button.setText(model.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!model.getUser().getmIsUsed()) {
                        model.getUser().setmIsUsed(true);
                        button.setText(String.format("%s (in use)", model.getName()));
                    } else {
                        model.getUser().setmIsUsed(false);
                        button.setText(model.getName());
                    }
                    mPresenter.updateDatabase(model.getName());
                }
            });
        } else {
            button.setEnabled(false);
            button.setOnClickListener(null);
            button.setText(String.format("%s (in use)", model.getName()));
        }
    }

    private void hideRadioButtons() {
        mBtnUser1.setVisibility(View.GONE);
        mBtnUser2.setVisibility(View.GONE);
        mBtnUser3.setVisibility(View.GONE);
        mBtnUser4.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        mPresenter.clearBeforeStopping();
        super.onDestroyView();
    }
}
