package com.erproject.busgo.views.main.fragmentMap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseFragmentDagger;
import com.erproject.busgo.data.data.request.fbRegistration.FbConnectedUser;
import com.erproject.busgo.data.data.simpleData.UserModel;
import com.erproject.busgo.utils.RequestPermissionsUtils;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.os.Looper.getMainLooper;
import static com.erproject.busgo.base.StaticFields.MAX_TIME_UPDATE;
import static com.erproject.busgo.base.StaticFields.MIDDLE_TIME_UPDATE;

public class MapFragment extends BaseFragmentDagger
        implements MapContract.View, LocationEngineCallback<LocationEngineResult>,
        MapboxMap.OnMapClickListener {
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
        mMapboxMap.addOnMapClickListener(this);

        mMapboxMap.getUiSettings().setCompassEnabled(false);

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
            builder.setMessage(getString(R.string.string_gps_is_disabled)).setCancelable(false)
                    .setPositiveButton(getString(R.string.string_yes),
                            (dialog, which) -> startActivity(new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .setNegativeButton(getString(R.string.string_no),
                            (dialog, which) -> dialog.cancel());
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
        mPresenter.saveLastLocation(result.getLastLocation());
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
        mMapView.onDestroy();
        mSymbolManager.onDestroy();
    }

    @Override
    public synchronized void showUsers(List<UserModel> activeUsers) {
        if (activeUsers == null || mMapboxMap.getStyle() == null) return;
        LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
        List<LatLng> latLngs = new ArrayList<>();
        for (UserModel user : activeUsers) {
            // Add the marker image to map
            mMapboxMap.getStyle().addImage(user.getName() + "_img", BitmapFactory
                    .decodeResource(getResources(), R.drawable.mapbox_marker_icon_default));

            GeoJsonSource geoJsonSource = new GeoJsonSource(user.getName(), Feature.fromGeometry(
                    Point.fromLngLat(user.getUser().getLongitude(), user.getUser().getLatitude())));

            if (user.getUser().getLatitude() != 0 && user.getUser().getLongitude() != 0)
                latLngs.add(
                        new LatLng(user.getUser().getLatitude(), user.getUser().getLongitude()));

            mMapboxMap.getStyle().addSource(geoJsonSource);

            SymbolLayer symbolLayer = new SymbolLayer(user.getName(), user.getName());
            symbolLayer.withProperties(PropertyFactory.iconImage(user.getName() + "_img"));
            mMapboxMap.getStyle().addLayer(symbolLayer);

            mMapboxMap.getStyle().addImage(user.getName() + "_img", BitmapFactory
                    .decodeResource(getResources(), R.drawable.mapbox_marker_icon_default));
        }
        if (latLngs.isEmpty()) return;
        if (latLngs.size() > 1) {
            latLngBounds.includes(latLngs);
            mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(), 128));

        } else {
            mMapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                    new CameraPosition.Builder().target(latLngs.get(0)).zoom(16).build()));
        }
    }

    public void loadUsersOnMap(List<UserModel> activeUsers) {
        mPresenter.checkAndShow(activeUsers);
    }

    @Override
    public void stopShowingUsers() {
        if (mPresenter.getmSavedActiveUsers() == null || mMapboxMap.getStyle() == null) return;
        for (UserModel user : mPresenter.getmSavedActiveUsers()) {
            if (mMapboxMap.getStyle().getLayer(user.getName()) != null &&
                    mMapboxMap.getStyle().getSource(user.getName()) != null) {
                mMapboxMap.getStyle().removeLayer(user.getName());
                mMapboxMap.getStyle().removeSource(user.getName());
            }
        }
    }

    @Override
    public void showUserOnMap(FbConnectedUser user) {
        if (user == null || mMapboxMap == null ||
                user.getLatitude() == 0 && user.getLongitude() == 0) return;
        LatLng latLng = new LatLng(user.getLatitude(), user.getLongitude());
        mMapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(new CameraPosition.Builder().target(latLng).zoom(15).build()));
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        PointF screenPoint = mMapboxMap.getProjection().toScreenLocation(point);
        if (mPresenter.getmSavedActiveUsers() == null) return false;
        for (UserModel model : mPresenter.getmSavedActiveUsers()) {
            List<Feature> features = mMapboxMap.queryRenderedFeatures(screenPoint, model.getName());
            if (!features.isEmpty()) {
                Snackbar.make(mMapView, model.getUser().getUserName(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
        return true;
    }

    public void showMyLastLocation() {
        Location location = mPresenter.getmLastLocation();
        if (mMapboxMap == null || location == null) return;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(new CameraPosition.Builder().target(latLng).zoom(15).build()));
    }
}
