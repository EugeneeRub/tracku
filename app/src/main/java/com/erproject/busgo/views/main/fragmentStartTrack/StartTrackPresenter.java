package com.erproject.busgo.views.main.fragmentStartTrack;

import javax.inject.Inject;

public class StartTrackPresenter implements StartTrackContract.Presenter {

    private StartTrackContract.View mView;

    @Inject
    StartTrackPresenter() {

    }

    @Override
    public void takeView(StartTrackContract.View view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        this.mView = null;
    }
}
