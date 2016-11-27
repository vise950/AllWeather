package com.dev.nicola.allweather.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Nicola on 05/10/2016.
 */

public class PermissionUtils {


    public static boolean isPermissionGranted(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static void askPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
    }
}
