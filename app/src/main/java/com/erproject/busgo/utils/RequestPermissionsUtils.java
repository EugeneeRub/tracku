package com.erproject.busgo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import static android.support.v4.app.ActivityCompat.requestPermissions;

public class RequestPermissionsUtils {
    public static boolean checkPermissionForReadExternalStorage(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @SuppressWarnings("unused")
    public static boolean requestPermissionForReadExternalStorageActivity(Activity activity, int requestCode) {
        try {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkPermissionForAccessFineLocation(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @SuppressWarnings("unused")
    public static boolean requestPermissionForAccessFineLocationActivity(Activity activity, int requestCode) {
        try {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("unused")
    public static boolean requestPermissionForAccessFineLocationFragment(Fragment fragment, int requestCode) {
        try {
            fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
