package com.erproject.busgo.base;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.erproject.busgo.R;
import com.erproject.busgo.app.App;
import com.erproject.busgo.views.login.LoginActivity;
import com.erproject.busgo.views.main.fragmentMap.MapFragment;

import dagger.android.support.DaggerAppCompatActivity;


public abstract class BaseActivityDagger extends DaggerAppCompatActivity {
    public void showFragment(Fragment fragment, @IdRes int container, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(container);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (currentFragment == null) fragmentTransaction.add(container, fragment, tag);
        else fragmentTransaction.replace(container, fragment, tag);

        hideFragment(fragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    protected boolean isEmptyFragmentContainer(@IdRes int containerId) {
        return getSupportFragmentManager().findFragmentById(containerId) == null;
    }

    public void showFragmentOrRestore(@IdRes int containerId, Fragment fragment,
                                      String tagForRestoredFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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

    public void showFragmentOrRestore2(@IdRes int containerId, Fragment fragment,
                                       String tagForRestoredFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment sameFragment = fragmentManager.findFragmentByTag(tagForRestoredFragment);

        if (sameFragment == null) {
            hideAllFragments2(fragmentManager, fragmentTransaction);
            fragmentTransaction.add(containerId, fragment, tagForRestoredFragment);
        } else {
            hideAllFragments2(fragmentManager, fragmentTransaction);
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

    private void hideAllFragments2(FragmentManager fragmentManager,
                                   FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (!(fragment instanceof MapFragment)) fragmentTransaction.hide(fragment);
        }
    }

    public void hideFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Fragment fragment2 : fragmentManager.getFragments()) {
            getSupportFragmentManager().beginTransaction().hide(fragment2).commit();
        }
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    public void startLoginActivity() {
        startActivity(LoginActivity.newInstance(this));
        finish();
    }

    protected void setTheme() {
        switch (App.getInstance().getThemeMode()) {
            case 1:
                setTheme(R.style.ThemeDark);
                break;
        }
    }
}
