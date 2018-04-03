package com.dev.nicola.allweather.utils

import android.app.Activity
import android.support.design.widget.Snackbar
import android.view.View
import com.dev.nicola.allweather.R
import kotlinx.android.synthetic.main.content_main.*

object SnackBarHelper {

    fun alreadyPro(activity: Activity, view: View = activity.coordinator_layout) {
        val snackBar = Snackbar.make(view, activity.resources.getString(R.string.already_pro_version), Snackbar.LENGTH_LONG)
        showSnackBar(activity, snackBar)
    }

    fun yahooProvider(activity: Activity, view: View = activity.coordinator_layout) {
        val snackBar = Snackbar.make(view, activity.resources.getString(R.string.error_yahoo), Snackbar.LENGTH_LONG)
        showSnackBar(activity, snackBar)
    }

    fun serverError(activity: Activity, onClick: (() -> Unit)? = null, view: View = activity.coordinator_layout) {
        val snackBar = Snackbar.make(view, activity.resources.getString(R.string.error_server_problem), Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(activity.resources.getString(R.string.action_retry), {
            onClick?.invoke()
            snackBar.dismiss()
        })
        showSnackBar(activity, snackBar)
    }

    fun dataRefresh(activity: Activity, view: View = activity.coordinator_layout) {
        val snackBar = Snackbar.make(view, activity.resources.getString(R.string.error_data_refresh), Snackbar.LENGTH_LONG)
        showSnackBar(activity, snackBar)
    }

    fun noInternetOrLocationActive(activity: Activity, onClick: (() -> Unit)? = null, view: View = activity.coordinator_layout) {
        val snackBar = Snackbar.make(view, activity.resources.getString(R.string.error_no_internet_or_location), Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(activity.resources.getString(R.string.action_retry), {
            onClick?.invoke()
            snackBar.dismiss()
        })
        showSnackBar(activity, snackBar)
    }

//    fun offline(activity: Activity, view: View = activity.coordinator_layout) {
//        val s = String.format(activity.resources.getString(R.string.offline_mode),
//                Utils.TimeHelper.getOfflineTime(PreferencesHelper.getPreferences(activity, PreferencesHelper.KEY_LAST_UPDATE, 0L) as Long))
//        val snackBar = Snackbar.make(view, s, Snackbar.LENGTH_INDEFINITE)
//        showSnackBar(activity, snackBar)
//    }

    private fun showSnackBar(activity: Activity, snackBar: Snackbar) {
        activity.runOnUiThread {
            snackBar.show()
        }
    }

}
