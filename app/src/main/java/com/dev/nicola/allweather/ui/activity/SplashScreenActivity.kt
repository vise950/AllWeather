package com.dev.nicola.allweather.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.nicola.allweather.util.delay
import com.dev.nicola.allweather.util.gotoWithFinish

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delay(1000) { gotoWithFinish<HomeActivity>() }
    }
}