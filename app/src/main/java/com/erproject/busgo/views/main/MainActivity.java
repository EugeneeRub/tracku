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
import com.erproject.busgo.services.authManager.AuthController;
import com.erproject.busgo.utils.GPSChecker;
import com.erproject.busgo.views.login.LoginActivity;
import com.erproject.busgo.views.main.fragmentLoadTrack.LoadTrackFragment;
import com.erproject.busgo.views.main.fragmentMap.MapFragment;
import com.erproject.busgo.views.main.fragmentStartTrack.StartTrackFragment;
import com.erproject.busgo.views.settings.SettingsActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivityDagger implements NavigationView.OnNavigationItemSelectedListener, MainActivityContract.View {

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
    //region FRAGMENTS

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.string_open_drawer, R.string.string_close_drawer) {

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
                showFragmentOrRestore(R.id.container, mMapFragment, "MAP_FRAGMENT");
                drawer.closeDrawers();
                return true;
            case R.id.menu_start_track:
                showFragmentOrRestore2(R.id.container2, mStartTrackFragment, "START_TRACK_FRAGMENT");
                drawer.closeDrawers();
                return true;
            case R.id.menu_check_track:
                showFragmentOrRestore2(R.id.container2, mLoadTrackFragment, "LOAD_TRACK_FRAGMENT");
                drawer.closeDrawers();
                return true;
            case R.id.menu_log_out:
                AuthController.removeAccount(this);
                startActivity(LoginActivity.newInstance(this));
                finish();
                return true;
            case R.id.menu_settings:
                startActivity(SettingsActivity.newInstance(this));
                drawer.closeDrawers();
                return true;
            default:
                return true;
        }
    }
    //endregion NAVIGATION SETUP

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
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}