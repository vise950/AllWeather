package com.dev.nicola.allweather.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.View
import com.dev.nicola.allweather.R

/**
 * Created by Nicola on 21/10/2016.
 */

class SnackbarUtils {

    companion object {

        /**
         * @param activity is activity where show view
         * *
         * @param view     is main view (Coordination layout)
         * *
         * @param code     is code for many task
         * *                 1: ask location permission
         * *                 2: ask enable gps
         * *                 3: ask turn on internet connection (wifi or cellular data)
         * *                 4: ask if you retry because server don't response
         * *                 5: alert that yahoo don't support hourly forecast
         * *                 6: alert that is already pro
         */
        @JvmStatic fun showSnackbar(activity: Activity, view: View, code: Int) {
            activity.runOnUiThread {
                var mSnackbar: Snackbar? = null

                when (code) {

                    1 -> {
                        mSnackbar = Snackbar.make(view, R.string.snackbar_1, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.action_OK) { PermissionUtils.askPermission(activity) }
                                .setActionTextColor(Color.YELLOW)
                    }


                    2 -> {
                        mSnackbar = Snackbar.make(view, R.string.snackbar_2, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.action_OK) { activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                                .setActionTextColor(Color.YELLOW)
                    }


                    3 -> {
                        mSnackbar = Snackbar.make(view, R.string.snackbar_3, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.action_OK) {
                                    AlertDialog.Builder(activity)
                                            .setMessage(R.string.diaolog_connection_disable)
                                            .setCancelable(false)
                                            .setPositiveButton(R.string.action_settings) { dialogInterface, i -> activity.startActivity(Intent(Settings.ACTION_SETTINGS)) }
                                            .setNegativeButton(R.string.action_cancel) { dialogInterface, i -> activity.finish() }
                                            .show()
                                }
                                .setActionTextColor(Color.YELLOW)
                    }


                    4 -> {
                        mSnackbar = Snackbar.make(view, R.string.snackbar_4, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.action_retry) {
                                    //                                                        new task().execute(nullString);
//                                                        if (!mProgressDialog.isShowing())
//                                                            mProgressDialog.show();
                                }
                                .setActionTextColor(Color.YELLOW)
                    }

                    5 -> mSnackbar = Snackbar.make(view, R.string.dialog_preference_yahoo, Snackbar.LENGTH_LONG)

                    6 -> mSnackbar = Snackbar.make(view, "Già pro", Snackbar.LENGTH_LONG)
                }

                mSnackbar!!.show()
            }
        }
    }
}
