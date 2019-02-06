package com.erproject.busgo.views.main.fragmentMap;

import javax.inject.Inject;

public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mView;

    @Inject
    MapPresenter() {

    }

    @Override
    public void takeView(MapContract.View view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        this.mView = null;
    }
}
