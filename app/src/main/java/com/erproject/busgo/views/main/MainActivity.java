package com.erproject.busgo.views.main;

import android.content.Context;
import android.content.DialogInterface;
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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseActivityDagger;
import com.erproject.busgo.data.data.responses.CityFinderResponse;
import com.erproject.busgo.data.sharedPreferences.LocalSharedPreferences;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

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
    private MapboxMap mMapboxMap;
    private SupportMapFragment mMapFragment;

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapbox.getInstance(this, getString(R.string.access_token_map));
        ButterKnife.bind(this);

        String cityName = presenter.getStringFromPref(LocalSharedPreferences.CITY);

        if (cityName == null) {
            presenter.loadCurrentCity();
        } else {
            TextView tvCityName = navigationView.getHeaderView(0).findViewById(R.id.name_city);
            tvCityName.setText(cityName);
        }

        setUpNavigationView();

        setupMap();
    }

    private void setupMap() {
        LatLng location = new LatLng(50.458137, 30.559787);
        MapboxMapOptions options = new MapboxMapOptions();
        options.camera(new CameraPosition.Builder()
                .target(location)
                .zoom(11)
                .build());

        mMapFragment = SupportMapFragment.newInstance(options);
        showFragmentOrRestore(R.id.container, mMapFragment, "com.mapbox.map");
        mMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                mMapboxMap.setStyle(Style.MAPBOX_STREETS, null);
            }
        });
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
//            case R.id.menu_city:
//                Toast.makeText(this, "City", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.menu_schedule_n_routes:
//                Toast.makeText(this, "Schedule", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.menu_input_code:
//                if (getSupportFragmentManager().findFragmentByTag(ENTER_CODE_DIALOG) == null) {
//                    enterCodeDialog.show(getSupportFragmentManager(), ENTER_CODE_DIALOG);
//                }
//                drawer.closeDrawers();
//                return true;
            case R.id.menu_exit:
                finish();
                return true;
            default:
                return true;
        }
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
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCurrentCityDialog(final CityFinderResponse response) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        if (response.getStatus()) {
            final String msg1 = "We detect your current location in city: ";
            final String msg2 = "If it isn`t your city, you have to change it in menu, because Bus&Go work with your location and city";
            final SpannableStringBuilder sb = new SpannableStringBuilder(msg1
                    + response.getCity() + "\n" + msg2);
            final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
            sb.setSpan(bss, msg1.length(), (msg1.length() + response.getCity().length()), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            builder.setMessage(sb);
        } else {
            builder.setMessage("Sorry, but we can not detect your city location");
        }
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (response.getStatus()) {
                    presenter.saveStringInPref(LocalSharedPreferences.CITY, response.getCity());
                    TextView tvCityName = navigationView.getHeaderView(0).findViewById(R.id.name_city);
                    tvCityName.setText(response.getCity());
                }
            }
        });
        builder.show();
    }
}
