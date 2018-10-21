package com.erproject.busgo.base;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Objects;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragmentDagger extends DaggerFragment {

    protected void showFragmentOrRestore(@IdRes int containerId, Fragment fragment,
                                           String tagForRestoredFragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Fragment sameFragment = fragmentManager.findFragmentByTag(tagForRestoredFragment);

        if (sameFragment == null) {
            hideAllFragments(fragmentManager, fragmentTransaction);
            fragmentTransaction.add(containerId, fragment, tagForRestoredFragment);
        } else {
            hideAllFragments(fragmentManager, fragmentTransaction);
            fragmentTransaction.show(sameFragment);
        }

        fragmentTransaction.commit();
    }

    protected void showFragmentOrRestoreInActivity(@IdRes int containerId, Fragment fragment,
                                                   String tagForRestoredFragment) {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Fragment sameFragment = fragmentManager.findFragmentByTag(tagForRestoredFragment);

        if (sameFragment == null) {
            hideAllFragments(fragmentManager, fragmentTransaction);
            fragmentTransaction.add(containerId, fragment, tagForRestoredFragment);
        } else {
            hideAllFragments(fragmentManager, fragmentTransaction);
            fragmentTransaction.show(sameFragment);
        }

        fragmentTransaction.commit();
    }

    private void hideAllFragments(FragmentManager fragmentManager,
                                  FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : fragmentManager.getFragments()) {
            fragmentTransaction.hide(fragment);
        }
    }
}
