package com.dev.nicola.allweather.utils

import android.content.Context
import com.amazonaws.mobileconnectors.amazonmobileanalytics.MobileAnalyticsManager
import com.dev.nicola.allweather.BuildConfig

class AnalyticsHelper(val context: Context) {
    var analytics: MobileAnalyticsManager? = null

    fun create() {
        analytics = MobileAnalyticsManager.getOrCreateInstance(context, BuildConfig.AMAZON_ANALYTICS_ID, BuildConfig.AMAZON_COGNITO_ID)
    }

    fun pause() {
        analytics?.sessionClient?.pauseSession()
        analytics?.eventClient?.submitEvents()
    }

    fun resume() {
        analytics?.sessionClient?.resumeSession()
    }
}