package com.dev.nicola.allweather.utils;

import android.Manifest;
import android.app.Activity;
import android.os.Build;

/**
 * Created by Nicola on 05/10/2016.
 */

public class PermissionUtils {

    public static void askPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_ASK_PERMISSIONS:
//                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                    // Permission DENIED
//                    showSnackbar(1);
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
}
