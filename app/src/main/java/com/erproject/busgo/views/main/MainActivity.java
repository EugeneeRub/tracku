package com.erproject.busgo.views.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseActivityDagger;
import com.erproject.busgo.data.data.request.fbRegistration.FbConnectedUser;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.erproject.busgo.dialogs.EnterCodeDialog;
import com.erproject.busgo.services.authManager.AuthController;
import com.erproject.busgo.utils.GPSChecker;
import com.erproject.busgo.views.login.LoginActivity;
import com.erproject.busgo.views.main.fragmentLoadTrack.LoadTrackFragment;
import com.erproject.busgo.views.main.fragmentMap.MapFragment;
import com.erproject.busgo.views.main.fragmentStartTrack.StartTrackFragment;
import com.erproject.busgo.views.settings.SettingsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivityDagger
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityContract.View {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    MainActivityPresenter presenter;

    //region FRAGMENTS
    @Inject
    StartTrackFragment mStartTrackFragment;
    @Inject
    LoadTrackFragment mLoadTrackFragment;
    @Inject
    MapFragment mMapFragment;
    //endregion FRAGMENTS

    private boolean mIsBusyLoad;
    private boolean mIsBusyStart;

    @Inject
    EnterCodeDialog mEnterCodeDialog;

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setToolbarTitle(getString(R.string.string_map));
        checkGPS();
        setUpNavigationView();
    }

    private void checkGPS() {
        if (GPSChecker.isLocationDisabled(this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.app_name));
            builder.setMessage(getString(R.string.string_gps_warning));
            builder.setPositiveButton(R.string.string_ok, null);
            builder.show();
        }
    }

    //region NAVIGATION SETUP
    private void setUpNavigationView() {
        showFragmentOrRestore(R.id.container, mMapFragment, "MAP_FRAGMENT");

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.string_open_drawer,
                        R.string.string_close_drawer) {

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }
                };

        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_map:
                setToolbarTitle(getString(R.string.string_map));
                if (getSupportFragmentManager().findFragmentByTag("MAP_FRAGMENT") != null) {
                    mMapFragment.enableLocationCallback();
                }
                showFragmentOrRestore(R.id.container, mMapFragment, "MAP_FRAGMENT");
                drawer.closeDrawers();
                return true;
            case R.id.menu_start_track:
                if (getSupportFragmentManager().findFragmentByTag("MAP_FRAGMENT") != null) {
                    mMapFragment.enableLocationCallback();
                }

                setToolbarTitle(getString(R.string.string_start_track));
                showFragmentOrRestore2(R.id.container2, mStartTrackFragment,
                        "START_TRACK_FRAGMENT");
                drawer.closeDrawers();
                return true;
            case R.id.menu_check_track:
                showCheckFragment();
                return true;
            case R.id.menu_log_out:
                AuthController.removeAccount(this);
                startActivity(LoginActivity.newInstance(this));
                finish();
                return true;
            case R.id.menu_settings:
                drawer.closeDrawers();
                startActivity(SettingsActivity.newInstance(this));
                return true;
            default:
                return true;
        }
    }

    private void showCheckFragment() {
        if (presenter.isUserEnterTheUniqueCode()) {
            setToolbarTitle(getString(R.string.string_monitor_users));
            mMapFragment.disableLocationCallback();
            showFragmentOrRestore2(R.id.container2, mLoadTrackFragment, "LOAD_TRACK_FRAGMENT");
            drawer.closeDrawers();
        } else {
            mEnterCodeDialog.setmCallback(code -> {
                if (presenter.checkAndSaveEnteredUniqueCode(code)) {
                    mEnterCodeDialog.dismiss();
                    showCheckFragment();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.string_codes_do_not_match),
                            Toast.LENGTH_SHORT).show();
                }

            });
            mEnterCodeDialog.show(getSupportFragmentManager(), "ENTER_CODE_DIALOG");
        }
    }
    //endregion NAVIGATION SETUP

    private void setToolbarTitle(@NonNull String title) {
        if (getSupportActionBar() == null) setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.dropView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToLogin() {
        startLoginActivity();
    }

    @Override
    public Context getmContext() {
        return this;
    }

    public boolean isBusyLoad() {
        return mIsBusyLoad;
    }

    public void setIsBusyLoad(boolean mIsBusyLoad) {
        this.mIsBusyLoad = mIsBusyLoad;
    }

    public boolean isBusyStart() {
        return mIsBusyStart;
    }

    public void setIsBusyStart(boolean mIsBusyStart) {
        this.mIsBusyStart = mIsBusyStart;
    }

    public void loadUsersOnMap(List<UserModel> activeUsers) {
        mMapFragment.loadUsersOnMap(activeUsers);
    }

    public void stopShowingUsers() {
        mMapFragment.stopShowingUsers();
    }

    public void showUserOnMap(FbConnectedUser user) {
        mMapFragment.showUserOnMap(user);
    }

    public void showLastMyLoaction() {
        mMapFragment.showMyLastLocation();
    }
}