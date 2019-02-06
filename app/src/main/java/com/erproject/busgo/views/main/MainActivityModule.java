package com.erproject.busgo.views.main;

import com.erproject.busgo.di.scoped.FragmentScoped;
import com.erproject.busgo.views.main.fragmentLoadTrack.LoadTrackFragment;
import com.erproject.busgo.views.main.fragmentMap.MapFragment;
import com.erproject.busgo.views.main.fragmentStartTrack.StartTrackFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract MapFragment bindMapFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract StartTrackFragment bindStartTrackFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoadTrackFragment bindLoadTrackFragment();
}
