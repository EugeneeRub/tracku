package com.erproject.busgo.views.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseActivityDagger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivityDagger {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Intent newInstance(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        prepareToolbar();
    }

    private void prepareToolbar() {
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_white);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
