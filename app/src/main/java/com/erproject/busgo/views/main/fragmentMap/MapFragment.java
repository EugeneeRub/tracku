package com.erproject.busgo.views.main.fragmentMap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseFragmentDagger;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.erproject.busgo.utils.RequestPermissionsUtils;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.os.Looper.getMainLooper;
import static com.erproject.busgo.base.StaticFields.MAX_TIME_UPDATE;
import static com.erproject.busgo.base.StaticFields.MIDDLE_TIME_UPDATE;

public class MapFragment extends BaseFragmentDagger
        implements MapContract.View, LocationEngineCallback<LocationEngineResult> {

    private static final int REQUEST_CODE_ACCESS_FINE_PERMISSION = 354;

    @BindView(R.id.fragment_map_map)
    MapView mMapView;

    @Inject
    MapPresenter mPresenter;
    private MapboxMap mMapboxMap;

    private LocationEngine mLocationEngine;
    private LocationComponent mLocationComponent;
    private SymbolManager mSymbolManager;

    private LocationEngineRequest request = new LocationEngineRequest.Builder(MIDDLE_TIME_UPDATE)
            .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
            .setMaxWaitTime(MAX_TIME_UPDATE).build();

    @Inject
    public MapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        mMapView.onCreate(savedInstanceState);

        if (!RequestPermissionsUtils.checkPermissionForAccessFineLocation(getActivity())) {
            RequestPermissionsUtils.requestPermissionForAccessFineLocationFragment(this,
                    REQUEST_CODE_ACCESS_FINE_PERMISSION);
        } else {
            checkGPSEnabled(Objects.requireNonNull(getContext()));
            setupMap();
        }

        return view;
    }

    private void setupMap() {
        mMapView.getMapAsync(mapboxMap -> {
            mMapboxMap = mapboxMap;
            mMapboxMap.setStyle(Style.MAPBOX_STREETS, style -> {
                mSymbolManager = new SymbolManager(mMapView, mMapboxMap, style);
                setupLocationCallback();
            });
        });
    }

    @SuppressWarnings({"MissingPermission", "all"})
    private void setupLocationCallback() {
        if (mLocationComponent == null) {
            mLocationComponent = mMapboxMap.getLocationComponent();
            mLocationComponent.activateLocationComponent(getContext(),
                    Objects.requireNonNull(mMapboxMap.getStyle()));
            mLocationComponent.setLocationComponentEnabled(true);
            mLocationComponent.setCameraMode(CameraMode.TRACKING_GPS);
            mLocationComponent.setRenderMode(RenderMode.COMPASS);
        } else mLocationComponent.onStart();

        mMapboxMap.setMinZoomPreference(14);
        mMapboxMap.setMaxZoomPreference(25);

        if (mLocationEngine == null)
            mLocationEngine = LocationEngineProvider.getBestLocationEngine(getContext());

        mLocationEngine.requestLocationUpdates(request, this, getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ACCESS_FINE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkGPSEnabled(Objects.requireNonNull(getContext()));
                    setupMap();
                } else {
                    RequestPermissionsUtils.requestPermissionForAccessFineLocationFragment(this,
                            REQUEST_CODE_ACCESS_FINE_PERMISSION);
                }
            }
        }
    }

    private void checkGPSEnabled(@NonNull Context context) {
        final LocationManager manager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager == null) return;

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false).setPositiveButton("Yes", (dialog, which) -> startActivity(
                    new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .setNegativeButton("No", (dialog, which) -> dialog.cancel());
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void showError(String msg) {
    }

    @Override
    public void goToLogin() {
        startLoginActivity();
    }

    //region LOCATION CALLBACKS
    @Override
    public void onSuccess(LocationEngineResult result) {
    }

    @Override
    public void onFailure(@NonNull Exception exception) {
    }

    //endregion LOCATION CALLBACKS

    public void enableLocationCallback() {
        setupLocationCallback();
    }

    public void disableLocationCallback() {
        mMapboxMap.setMinZoomPreference(0);
        mMapboxMap.setMaxZoomPreference(25);

        mLocationComponent.onStop();
        mLocationEngine.removeLocationUpdates(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSymbolManager.onDestroy();
    }

    @Override
    public synchronized void showUsers(List<UserModel> activeUsers) {
        if (activeUsers == null || mMapboxMap.getStyle() == null) return;
        for (UserModel user : activeUsers) {
            // Add the marker image to map
            mMapboxMap.getStyle().addImage("marker-icon-id", BitmapFactory
                    .decodeResource(getResources(), R.drawable.mapbox_marker_icon_default));

            GeoJsonSource geoJsonSource = new GeoJsonSource(user.getName(), Feature.fromGeometry(
                    Point.fromLngLat(activeUsers.get(0).getUser().getLongitude(),
                            activeUsers.get(0).getUser().getLatitude())));

            mMapboxMap.getStyle().addSource(geoJsonSource);

            SymbolLayer symbolLayer = new SymbolLayer(user.getName(), user.getName());
            symbolLayer.withProperties(PropertyFactory.iconImage("marker-icon-id"));
            mMapboxMap.getStyle().addLayer(symbolLayer);

            mMapboxMap.getStyle().addImage("marker-icon-id", BitmapFactory
                    .decodeResource(getResources(), R.drawable.mapbox_marker_icon_default));
        }
    }

    public void loadUsersOnMap(List<UserModel> activeUsers) {
        mPresenter.checkAndShow(activeUsers);
    }

    @Override
    public void stopShowingUsers() {
        if (mPresenter.getSavedActiveUsers() == null || mMapboxMap.getStyle() == null) return;
        for (UserModel user : mPresenter.getSavedActiveUsers()) {
            if (mMapboxMap.getStyle().getLayer(user.getName()) != null &&
                    mMapboxMap.getStyle().getSource(user.getName()) != null) {
                mMapboxMap.getStyle().removeLayer(user.getName());
                mMapboxMap.getStyle().removeSource(user.getName());
            }
        }
    }
}
