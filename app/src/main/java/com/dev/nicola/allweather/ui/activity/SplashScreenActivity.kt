package com.dev.nicola.allweather.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.nicola.allweather.util.gotoWithFinish
import com.ewt.nicola.common.extension.delay

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delay { gotoWithFinish<HomeActivity>() }
    }
}