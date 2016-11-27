package com.dev.nicola.allweather.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.dev.nicola.allweather.R;

/**
 * Created by Nicola on 21/10/2016.
 */

public class SnackbarUtils {


    /**
     * @param activity is activity where show view
     * @param view     is main view (Coordination layout)
     * @param code     is code for many task
     *                 1: ask location permission
     *                 2: ask enable gps
     *                 3: ask turn on internet connection (wifi or cellular data)
     *                 4: ask if you retry because server don't response
     *                 5: alert that yahoo don't support hourly forecast
     *                 6: alert that is already pro
     */
    public static void showSnackbar(final Activity activity, final View view, final int code) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Snackbar mSnackbar = null;

                switch (code) {

                    case 1:
                        mSnackbar = Snackbar.make(view, R.string.snackbar_1, Snackbar.LENGTH_INDEFINITE);
                        mSnackbar.setAction(R.string.action_OK, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PermissionUtils.askPermission(activity);
                            }
                        });
                        mSnackbar.setActionTextColor(Color.YELLOW);
                        break;


                    case 2:
                        mSnackbar = Snackbar.make(view, R.string.snackbar_2, Snackbar.LENGTH_INDEFINITE);
                        mSnackbar.setAction(R.string.action_OK, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        });
                        mSnackbar.setActionTextColor(Color.YELLOW);
                        break;


                    case 3:
                        mSnackbar = Snackbar.make(view, R.string.snackbar_3, Snackbar.LENGTH_INDEFINITE);
                        mSnackbar.setAction(R.string.action_OK, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new AlertDialog.Builder(activity)
                                        .setMessage(R.string.diaolog_connection_disable)
                                        .setCancelable(false)
                                        .setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
                                            }
                                        })
                                        .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                activity.finish();
                                            }
                                        })
                                        .show();
                            }
                        });
                        mSnackbar.setActionTextColor(Color.YELLOW);
                        break;


                    case 4:
                        mSnackbar = Snackbar.make(view, R.string.snackbar_4, Snackbar.LENGTH_INDEFINITE);
                        mSnackbar.setAction(R.string.action_retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                        new task().execute(nullString);
//                        if (!mProgressDialog.isShowing())
//                            mProgressDialog.show();
                            }
                        });
                        mSnackbar.setActionTextColor(Color.YELLOW);
                        break;

                    case 5:
                        mSnackbar = Snackbar.make(view, R.string.dialog_preference_yahoo, Snackbar.LENGTH_LONG);
                        break;

                    case 6:
                        mSnackbar = Snackbar.make(view, "Già pro", Snackbar.LENGTH_LONG);
                        break;
                }

                mSnackbar.show();
            }
        });
    }
}
