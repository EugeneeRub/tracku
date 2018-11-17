package com.erproject.busgo.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseActivityDagger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivityDagger
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpNavigationView();
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.open_drawer,
                R.string.close_drawer) {

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
            case R.id.menu_city:
                Toast.makeText(this, "City", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_schedule_n_routes:
                Toast.makeText(this, "Schedule", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_input_code:
                Toast.makeText(this, "Input Code", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_exit:
                finish();
                return true;
            default:
                return true;
        }
    }
}
