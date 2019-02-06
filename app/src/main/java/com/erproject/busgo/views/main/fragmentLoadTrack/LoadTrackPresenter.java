package com.erproject.busgo.views.main.fragmentLoadTrack;

import javax.inject.Inject;

public class LoadTrackPresenter implements LoadTrackContract.Presenter {

    private LoadTrackContract.View mView;

    @Inject
    LoadTrackPresenter() {

    }

    @Override
    public void takeView(LoadTrackContract.View view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        this.mView = null;
    }
}
