package com.erproject.busgo.views.main.fragmentStartTrack;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseFragmentDagger;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.erproject.busgo.views.main.MainActivity;
import com.erproject.busgo.views.serviceTracking.StartTrackService;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.erproject.busgo.views.serviceTracking.StartTrackService.MODEL_EXTRAS;
import static com.erproject.busgo.views.serviceTracking.StartTrackService.USER_ID_EXTRAS;

public class StartTrackFragment extends BaseFragmentDagger implements StartTrackContract.View {
    private static final int PERMISSIONS_REQUEST_CALL_PHONE = 587;

    @BindView(R.id.fragment_start_track_hide_all_layout)
    FloatingActionButton mImageArrow;
    @BindView(R.id.fragment_start_track_phone_fab)
    FloatingActionButton mPhoneFab;
    @BindView(R.id.fragment_start_track_show_me)
    FloatingActionButton mShowMeFab;
    @BindView(R.id.fragment_start_track_button_start)
    Button mButtonStartTrack;
    @BindView(R.id.fragment_start_track_card_layout_choose)
    CardView mCardChoose;
    @BindView(R.id.fragment_start_track_card_layout_title)
    CardView mCardTitle;
    @BindView(R.id.fragment_start_track_card_title)
    TextView mTextTitle;

    @BindView(R.id.fragment_start_track_id1)
    CheckBox mBtnUser1;
    @BindView(R.id.fragment_start_track_id2)
    CheckBox mBtnUser2;
    @BindView(R.id.fragment_start_track_id3)
    CheckBox mBtnUser3;
    @BindView(R.id.fragment_start_track_id4)
    CheckBox mBtnUser4;

    private boolean mIsHidingAll;
    private boolean mIsStartWork;

    @Inject
    StartTrackPresenter mPresenter;

    @OnClick(R.id.fragment_start_track_hide_all_layout)
    public void onArrowClicked() {
        if (!mIsHidingAll) {
            mIsHidingAll = true;
            mImageArrow.setIcon(R.drawable.ic_arrow_up_green);
            hideByAnimAllViews();
        } else {
            mIsHidingAll = false;
            mImageArrow.setIcon(R.drawable.ic_arrow_down_green);
            showByAnimAllViews();
        }
    }

    @OnClick(R.id.fragment_start_track_phone_fab)
    public void onChooseClicked() {
        if (getActivity() == null) return;
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat
                    .requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                            PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            // Permission has already been granted
            startCallingToBasePhone();
        }
    }

    private void startCallingToBasePhone() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mPresenter.getBasePhone()));
        startActivity(callIntent);
    }

    @OnClick(R.id.fragment_start_track_button_start)
    public void onStartClicked() {
        boolean isBusyLoad = ((MainActivity) Objects.requireNonNull(getActivity())).isBusyLoad();
        if (isBusyLoad) {
            showDialogWithText(getString(R.string.string_warning_text),
                    getString(R.string.string_disable_loading_warning));
        } else {
            if (!mIsStartWork) {
                showCard();
            } else {
                stopTracking();
            }
            mIsStartWork = !mIsStartWork;
        }
    }

    @OnClick(R.id.fragment_start_track_card_text)
    public void onCloseClicked() {
        ((MainActivity) Objects.requireNonNull(getActivity())).setIsBusyStart(false);
        mIsStartWork = !mIsStartWork;
        hideCard();
    }

    @OnClick(R.id.fragment_start_track_show_me)
    public void onShowMeClicked() {
        if (getActivity() != null) ((MainActivity) getActivity()).showLastMyLoaction();
    }

    private void hideCard() {
        mCardChoose.setVisibility(View.GONE);
        mButtonStartTrack.setEnabled(true);
        mImageArrow.setEnabled(true);
    }

    private void showCard() {
        ((MainActivity) Objects.requireNonNull(getActivity())).setIsBusyStart(true);
        mCardChoose.setVisibility(View.VISIBLE);
        mButtonStartTrack.setEnabled(false);
        mImageArrow.setEnabled(false);
    }

    private void hideByAnimAllViews() {
        mButtonStartTrack.animate().alpha(0.0f).translationY(mButtonStartTrack.getHeight());
        mShowMeFab.animate().alpha(0.0f).translationX(-mShowMeFab.getWidth());
        if (mPhoneFab.getVisibility() == View.VISIBLE) {
            mPhoneFab.animate().alpha(0.0f).translationX(mPhoneFab.getWidth());
        }
        if (mCardTitle.getVisibility() == View.VISIBLE) {
            mCardTitle.animate().alpha(0.0f).translationY(-mCardTitle.getHeight());
        }
    }

    private void showByAnimAllViews() {
        mButtonStartTrack.animate().alpha(1.0f).translationY(0);
        mShowMeFab.animate().alpha(1.0f).translationX(0);
        mButtonStartTrack.setVisibility(View.VISIBLE);
        if (mPhoneFab.getVisibility() == View.VISIBLE) {
            mPhoneFab.animate().alpha(1.0f).translationX(0);
        }
        if (mCardTitle.getVisibility() == View.VISIBLE) {
            mCardTitle.animate().alpha(1.0f).translationY(0);
        }
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
        mPresenter.stopTracking();
        mPresenter.dropView();
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
        if (!model.getUser().getIsUsed()) {
            button.setEnabled(true);
            button.setText(model.getUser().getUserName());
            button.setOnClickListener(v -> {
                if (!model.getUser().getIsUsed()) {
                    model.getUser().setIsUsed(true);
                    button.setText(String.format("%s %s", model.getUser().getUserName(),
                            getString(R.string.string_in_use)));
                } else {
                    model.getUser().setIsUsed(false);
                    button.setText(model.getUser().getUserName());
                }
                button.setChecked(false);
                startTracking(model.getName(), model.getUser().getUserName());
            });
        } else {
            button.setEnabled(false);
            button.setOnClickListener(null);
            button.setText(String.format("%s %s", model.getUser().getUserName(),
                    getString(R.string.string_in_use)));
        }
    }

    private void hideRadioButtons() {
        mBtnUser1.setVisibility(View.GONE);
        mBtnUser2.setVisibility(View.GONE);
        mBtnUser3.setVisibility(View.GONE);
        mBtnUser4.setVisibility(View.GONE);
    }

    @Override
    public void stopTracking() {
        ((MainActivity) Objects.requireNonNull(getActivity())).setIsBusyStart(false);
        mCardTitle.setVisibility(View.GONE);
        mPhoneFab.setVisibility(View.GONE);

        mButtonStartTrack.setText(R.string.string_allow_transfer_data);
        mTextTitle.setText("");

        Intent stopServiceIntent = new Intent(getActivity(), StartTrackService.class);
        if (getActivity() != null) getActivity().stopService(stopServiceIntent);

        mPresenter.stopTracking();
    }

    private void startTracking(String userID, String userName) {
        mCardTitle.setVisibility(View.VISIBLE);
        mPhoneFab.setVisibility(View.VISIBLE);

        mButtonStartTrack.setText(R.string.string_stop_transfer_data);
        mTextTitle.setText(String.format(" %s: \"%s\" ", getString(R.string.string_user), userName));
        hideCard();

        mPresenter.updateDatabase(userID);

        // load service
        Intent startServiceIntent = new Intent(getActivity(), StartTrackService.class);
        startServiceIntent.putExtra(MODEL_EXTRAS, mPresenter.getModel());
        startServiceIntent.putExtra(USER_ID_EXTRAS, mPresenter.getUserId());
        if (getActivity() != null) getActivity().startService(startServiceIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCallingToBasePhone();
                }
            }
            break;
        }
    }
}
