package com.erproject.busgo.views.main.fragmentLoadTrack;

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

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseFragmentDagger;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.erproject.busgo.views.main.MainActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoadTrackFragment extends BaseFragmentDagger implements LoadTrackContract.View {
    @BindView(R.id.fragment_load_track_hide_all_layout)
    FloatingActionButton mImageArrow;
    @BindView(R.id.fragment_load_track_edit_users_fab)
    FloatingActionButton mFabEdit;
    @BindView(R.id.fragment_load_track_show_more_fab)
    FloatingActionButton mFabMore;
    @BindView(R.id.fragment_load_track_phone_to_fab)
    FloatingActionButton mFabPhone;
    @BindView(R.id.fragment_load_track_card_edit)
    CardView mCardEdit;
    @BindView(R.id.fragment_load_track_button_start)
    Button mButtonLoadTrack;

    @BindView(R.id.fragment_load_track_id1)
    CheckBox mBtnUser1;
    @BindView(R.id.fragment_load_track_id2)
    CheckBox mBtnUser2;
    @BindView(R.id.fragment_load_track_id3)
    CheckBox mBtnUser3;
    @BindView(R.id.fragment_load_track_id4)
    CheckBox mBtnUser4;

    private boolean mIsHidingFirst;
    private boolean mIsHidingSecond;

    private boolean mIsEnableLoading;

    @Inject
    LoadTrackPresenter mPresenter;

    @OnClick(R.id.fragment_load_track_button_start)
    public void onBtnStartClicked() {
        boolean isBusyStartTrack =
                ((MainActivity) Objects.requireNonNull(getActivity())).isBusyStart();
        if (isBusyStartTrack) {
            showDialogWithText(getString(R.string.string_warning_text),
                    getString(R.string.string_disable_transfer_warning));
        } else {
            onAllowClicked();
        }
    }

    private void onAllowClicked() {
        if (!mIsEnableLoading) {
            mButtonLoadTrack.setText(R.string.string_stop_observation);
            ((MainActivity) Objects.requireNonNull(getActivity())).setIsBusyLoad(true);
            mIsEnableLoading = true;

            if (getActivity() != null)
                ((MainActivity) getActivity()).loadUsersOnMap(mPresenter.getListActiveUsers());
        } else {
            mIsEnableLoading = false;

            mButtonLoadTrack.setText(R.string.string_allow_observation);
            ((MainActivity) Objects.requireNonNull(getActivity())).setIsBusyLoad(false);

            if (getActivity() != null) ((MainActivity) getActivity()).stopShowingUsers();

            clearCheckboxesStateButtons();
            mPresenter.stopLoading();
        }
    }

    @OnClick(R.id.fragment_load_track_hide_all_layout)
    public void onArrowClicked() {
        if (!mIsHidingFirst) {
            mIsHidingFirst = true;
            mImageArrow.setIcon(R.drawable.ic_arrow_up_green);
            hideAnimFirstViews();
        } else {
            mIsHidingFirst = false;
            mImageArrow.setIcon(R.drawable.ic_arrow_down_green);
            showAnimFirstViews();
        }
    }

    @OnClick(R.id.fragment_load_track_show_more_fab)
    public void onMoreFabClicked() {
        if (!mIsHidingSecond) {
            mIsHidingSecond = true;

            hideFabViews();
            mButtonLoadTrack.setEnabled(true);
            mImageArrow.setEnabled(true);
        } else {
            mIsHidingSecond = false;

            showFabViews();
            mButtonLoadTrack.setEnabled(false);
            mImageArrow.setEnabled(false);
        }
    }

    private void hideFabViews() {
        mFabEdit.animate().alpha(0.0f).translationX(mFabEdit.getWidth());
        mFabPhone.animate().alpha(0.0f).translationX(mFabPhone.getWidth());
        mCardEdit.animate().alpha(0.0f).translationX(mCardEdit.getWidth());
    }

    private void showFabViews() {
        mFabPhone.animate().alpha(1.0f).translationX(0);
        mFabEdit.animate().alpha(1.0f).translationX(0);
        mCardEdit.animate().alpha(1.0f).translationX(0);

        mCardEdit.setVisibility(View.VISIBLE);
    }

    private void hideAnimFirstViews() {
        mButtonLoadTrack.animate().alpha(0.0f).translationY(mButtonLoadTrack.getHeight());
        mFabMore.animate().alpha(0.0f).translationX(mFabMore.getWidth());
    }

    private void showAnimFirstViews() {
        mButtonLoadTrack.animate().alpha(1.0f).translationY(0);
        mButtonLoadTrack.setVisibility(View.VISIBLE);
        mFabMore.animate().alpha(1.0f).translationX(0);
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

        hideRadioButtons();
        showButtons(mPresenter.getListUsers());

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
        mPresenter.stopCallBack();
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
        super.startLoginActivity();
    }

    @Override
    public Context getmContext() {
        return getContext();
    }

    @Override
    public void updateState(List<UserModel> mUsers) {
        hideRadioButtons();
        showButtons(mUsers);
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
        if (model.getUser().getIsUsed())
            button.setText(String.format("%s (Active)", model.getName()));
        else button.setText(model.getName());

        button.setTag(model);

        button.setOnCheckedChangeListener((buttonView, isChecked) -> {
            model.getUser().setIsTracking(isChecked);
            mPresenter.updateDatabase();
        });
    }

    private void hideRadioButtons() {
        mBtnUser1.setVisibility(View.GONE);
        mBtnUser2.setVisibility(View.GONE);
        mBtnUser3.setVisibility(View.GONE);
        mBtnUser4.setVisibility(View.GONE);
    }

    private void clearCheckboxesStateButtons() {
        mBtnUser1.setChecked(false);
        mBtnUser2.setChecked(false);
        mBtnUser3.setChecked(false);
        mBtnUser4.setChecked(false);
    }

    @Override
    public void showUsersOnMap(List<UserModel> list) {
        if (mIsEnableLoading)
            if (getActivity() != null) ((MainActivity) getActivity()).loadUsersOnMap(list);
    }
}
