package com.dev.nicola.allweather.utils

import android.app.Activity
import android.support.design.widget.Snackbar
import android.view.View
import com.dev.nicola.allweather.R
import kotlinx.android.synthetic.main.content_main.*

object SnackBarHelper {

    fun alreadyPro(activity: Activity, view: View? = activity.container_layout) {
        val snackBar = Snackbar.make(view!!, activity.resources.getString(R.string.already_pro_version), Snackbar.LENGTH_LONG)
        showSnackBar(activity, snackBar)
    }

    fun yahooProvider(activity: Activity, view: View? = activity.container_layout) {
        val snackBar = Snackbar.make(view!!, activity.resources.getString(R.string.error_yahoo), Snackbar.LENGTH_LONG)
        showSnackBar(activity, snackBar)
    }

    fun serverError(activity: Activity, onClick: (() -> Unit)? = null, view: View? = activity.container_layout) {
        val snackBar = Snackbar.make(view!!, activity.resources.getString(R.string.error_server_problem), Snackbar.LENGTH_LONG)
        snackBar.setAction(activity.resources.getString(R.string.action_retry), { onClick?.invoke() })
        showSnackBar(activity, snackBar)
    }

    fun dataRefresh(activity: Activity, view: View? = activity.container_layout) {
        val snackBar = Snackbar.make(view!!, activity.resources.getString(R.string.error_data_refresh), Snackbar.LENGTH_LONG)
        showSnackBar(activity, snackBar)
    }

    private fun showSnackBar(activity: Activity, snackBar: Snackbar) {
        activity.runOnUiThread {
            snackBar.show()
        }
    }

}
