package com.erproject.busgo.views.main.fragmentMap;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.erproject.busgo.R;
import com.erproject.busgo.base.BaseFragmentDagger;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.os.Looper.getMainLooper;

public class MapFragment extends BaseFragmentDagger
        implements MapContract.View, LocationEngineCallback<LocationEngineResult> {

    @BindView(R.id.fragment_map_map)
    MapView mMapView;

    @Inject
    MapPresenter mPresenter;
    private MapboxMap mMapboxMap;

    private PermissionsManager mPermissonManager;
    private LocationEngine mLocationEngine;
    private LocationComponent mLocationComponent;

    private LocationEngineRequest request = new LocationEngineRequest.Builder(5_000)
            .setPriority(LocationEngineRequest.PRIORITY_NO_POWER).setMaxWaitTime(30_000).build();

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

        setupMap();

        return view;
    }

    private void setupMap() {
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;

                mMapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        setupLocationCallback();
                    }
                });
            }
        });
    }

    @SuppressWarnings({"MissingPermission", "all"})
    private void setupLocationCallback() {
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {
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
        } else {
            mPermissonManager = new PermissionsManager(new PermissionsListener() {
                @Override
                public void onExplanationNeeded(List<String> permissionsToExplain) {
                    System.out.println();
                }

                @Override
                public void onPermissionResult(boolean granted) {
                    if (granted) {
                        setupLocationCallback();
                    }
                }
            });
            mPermissonManager.requestLocationPermissions(getActivity());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mPermissonManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
    public void onSaveInstanceState(@NotNull Bundle outState) {
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
        Toast.makeText(getContext(), "Point", Toast.LENGTH_SHORT).show();
        Location lastLocation = result.getLastLocation();
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
}
