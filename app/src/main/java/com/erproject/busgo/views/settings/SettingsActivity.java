package com.erproject.busgo.views.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioButton;

import com.erproject.busgo.R;
import com.erproject.busgo.app.App;
import com.erproject.busgo.base.BaseActivityDagger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SettingsActivity extends BaseActivityDagger {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.activity_settings_day_theme)
    RadioButton mRbDayMode;
    @BindView(R.id.activity_settings_night_theme)
    RadioButton mRbNightMode;

    @OnCheckedChanged({R.id.activity_settings_day_theme, R.id.activity_settings_night_theme})
    void onThemeSelected(RadioButton radioButton) {
        if (radioButton.isChecked()) {
            switch (radioButton.getId()) {
                case R.id.activity_settings_day_theme:
                    App.getInstance().setThemeMode(0);
                    break;
                case R.id.activity_settings_night_theme:
                    App.getInstance().setThemeMode(1);
                    break;
            }
        }
    }

    public static Intent newInstance(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        prepareToolbar();
        setSavedParametersToRadioButtons();
    }

    public void setSavedParametersToRadioButtons() {
        switch (App.getInstance().getThemeMode()) {
            case 0:
                mRbDayMode.setChecked(true);
                break;
            case 1:
                mRbNightMode.setChecked(true);
                break;
        }
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
