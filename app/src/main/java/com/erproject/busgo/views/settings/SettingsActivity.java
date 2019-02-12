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
    public static final int RESULT_CODE = 456;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_settings_day_theme)
    RadioButton mRbDayMode;
    @BindView(R.id.activity_settings_night_theme)
    RadioButton mRbNightMode;
    private boolean isFirst;

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @OnCheckedChanged({R.id.activity_settings_day_theme, R.id.activity_settings_night_theme})
    void onThemeSelected(RadioButton radioButton) {
        if (radioButton.isChecked() && isFirst) {
            switch (radioButton.getId()) {
                case R.id.activity_settings_day_theme:
                    App.getInstance().setThemeMode(0);
                    recreate();
                    break;
                case R.id.activity_settings_night_theme:
                    App.getInstance().setThemeMode(1);
                    recreate();
                    break;
            }
        }
        isFirst = true;
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
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // startActivity(MainActivity.newInstance(this));
    }
}