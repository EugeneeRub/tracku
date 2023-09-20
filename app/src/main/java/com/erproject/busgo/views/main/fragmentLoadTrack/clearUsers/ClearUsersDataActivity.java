package com.erproject.busgo.views.main.fragmentLoadTrack.clearUsers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseActivityDagger;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.erproject.busgo.views.main.fragmentLoadTrack.clearUsers.adapter.ClearUsersAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClearUsersDataActivity extends BaseActivityDagger
        implements ClearUsersDataContract.View {
    @Inject
    ClearUsersDataPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_clear_data_list)
    RecyclerView mRecycleView;

    public static Intent newIntent(Context context) {
        return new Intent(context, ClearUsersDataActivity.class);
    }

    private ClearUsersAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_data);
        ButterKnife.bind(this);

        prepareToolbar();
        prepareRecycleView();
    }

    private void prepareRecycleView() {
        LinearLayoutManager manager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ClearUsersAdapter(user -> showAlertDialog(getString(R.string.string_warning),
                (getString(R.string.string_you_want_to_clear_bla_bla) + user.getUser().getUserName() +
                        getString(R.string.string_you_want_to_clear_bla_bla_second_part)),
                new OnOkCancelClicked() {
                    @Override
                    public void onOkClicked() {
                        user.getUser().setIsUsed(false);
                        mPresenter.updateUser(user);
                    }

                    @Override
                    public void onCancelClicked() {
                        // gag
                    }
                }, true));
        mRecycleView.setAdapter(mAdapter);
    }

    private void prepareToolbar() {
        setSupportActionBar(mToolbar);
        setTitle(R.string.string_clear_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_white);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
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

    @Override
    public void setItems(List<UserModel> list) {
        mAdapter.setList(list);
    }

    @Override
    public Context getmContext() {
        return this;
    }
}
