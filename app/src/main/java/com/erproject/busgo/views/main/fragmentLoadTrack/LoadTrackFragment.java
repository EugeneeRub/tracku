package com.erproject.busgo.views.main.fragmentLoadTrack;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseFragmentDagger;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.erproject.busgo.views.main.MainActivity;
import com.erproject.busgo.views.main.fragmentLoadTrack.adapter.adapterSearch.SearchAdapter;
import com.erproject.busgo.views.main.fragmentLoadTrack.clearUsers.ClearUsersDataActivity;
import com.erproject.busgo.views.main.fragmentLoadTrack.editUsers.EditUsersActivity;
import com.erproject.busgo.views.main.fragmentLoadTrack.phones.PhonesActivity;
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
    @BindView(R.id.fragment_load_track_edit_users_search)
    FloatingActionButton mFabSearchUsers;
    @BindView(R.id.fragment_load_track_show_more_fab)
    FloatingActionButton mFabMore;
    @BindView(R.id.fragment_load_track_phone_to_fab)
    FloatingActionButton mFabPhone;
    @BindView(R.id.fragment_load_track_clear_fab)
    FloatingActionButton mFabClear;
    @BindView(R.id.fragment_load_track_card_edit)
    CardView mCardEdit;
    @BindView(R.id.fragment_load_track_card_search)
    CardView mCardSearch;
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

    @BindView(R.id.fragment_load_track_list)
    RecyclerView mSearchList;

    private SearchAdapter mSearchAdapter;

    private boolean mIsHidingFirst;
    private boolean mIsHidingSecond;

    private boolean mIsEnableLoading;

    @Inject
    LoadTrackPresenter mPresenter;

    @OnClick(R.id.fragment_load_track_phone_to_fab)
    public void onFabPhonesClick() {
        startActivity(PhonesActivity.newIntent(getContext()));
    }

    @OnClick(R.id.fragment_load_track_clear_fab)
    public void onFabClearClick() {
        startActivity(ClearUsersDataActivity.newIntent(getContext()));
    }

    @OnClick(R.id.fragment_load_track_edit_users_fab)
    public void onFabEditClick() {
        startActivity(EditUsersActivity.newIntent(getContext()));
    }

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

    @OnClick(R.id.fragment_load_track_edit_users_search)
    public void onBtnSearchClicked() {
        if (mCardSearch.getVisibility() == View.VISIBLE) {
            mCardSearch.setVisibility(View.GONE);
        } else {
            mCardSearch.setVisibility(View.VISIBLE);
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

            mFabSearchUsers.setVisibility(View.VISIBLE);

            hideFabViews();
            mButtonLoadTrack.setEnabled(true);
            mImageArrow.setEnabled(true);
        } else {
            mIsHidingSecond = false;

            mCardSearch.setVisibility(View.GONE);
            mFabSearchUsers.setVisibility(View.GONE);

            showFabViews();
            mButtonLoadTrack.setEnabled(false);
            mImageArrow.setEnabled(false);
        }
    }

    private void hideFabViews() {
        mFabEdit.animate().alpha(0.0f).translationX(mFabEdit.getWidth());
        mFabPhone.animate().alpha(0.0f).translationX(mFabPhone.getWidth());
        mCardEdit.animate().alpha(0.0f).translationX(mCardEdit.getWidth());
        mFabClear.animate().alpha(0.0f).translationX(mCardEdit.getWidth());
    }

    private void showFabViews() {
        mFabPhone.animate().alpha(1.0f).translationX(0);
        mFabEdit.animate().alpha(1.0f).translationX(0);
        mCardEdit.animate().alpha(1.0f).translationX(0);
        mFabClear.animate().alpha(1.0f).translationX(0);

        mCardEdit.setVisibility(View.VISIBLE);
    }

    private void hideAnimFirstViews() {
        if (mCardSearch.getVisibility() == View.VISIBLE) {
            mCardSearch.animate().alpha(0.0f).translationX(-mCardSearch.getWidth());
        }
        mButtonLoadTrack.animate().alpha(0.0f).translationY(mButtonLoadTrack.getHeight());
        mFabMore.animate().alpha(0.0f).translationX(mFabMore.getWidth());
        mFabSearchUsers.animate().alpha(0.0f).translationX(mFabSearchUsers.getWidth());
    }

    private void showAnimFirstViews() {
        if (mCardSearch.getVisibility() == View.VISIBLE) {
            mCardSearch.animate().alpha(1.0f).translationX(0);
        }
        mButtonLoadTrack.animate().alpha(1.0f).translationY(0);
        mButtonLoadTrack.setVisibility(View.VISIBLE);
        mFabMore.animate().alpha(1.0f).translationX(0);
        mFabSearchUsers.animate().alpha(1.0f).translationX(0);
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

        prepareRecycleViewLists();

        return view;
    }

    private void prepareRecycleViewLists() {
        LinearLayoutManager manager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mSearchList.setLayoutManager(manager);
        mSearchList.setItemAnimator(new DefaultItemAnimator());
        mSearchAdapter = new SearchAdapter(getContext(), user -> {
            if (getActivity() != null) ((MainActivity) getActivity()).showUserOnMap(user);
        });

        mSearchList.setAdapter(mSearchAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCardEdit.getVisibility() == View.GONE) mCardEdit.setVisibility(View.VISIBLE);
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
            if (i == 0) showRadioButton(mBtnUser1, model);
            if (i == 1) showRadioButton(mBtnUser2, model);
            if (i == 2) showRadioButton(mBtnUser3, model);
            if (i == 3) showRadioButton(mBtnUser4, model);
        }
        mSearchAdapter.setList(list);
    }

    private void showRadioButton(final CheckBox button, final UserModel model) {
        button.setVisibility(View.VISIBLE);
        if (model.getUser().getIsUsed()) button.setText(
                String.format("%s %s", model.getUser().getUserName(),
                        getString(R.string.string_active)));
        else button.setText(model.getUser().getUserName());

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
