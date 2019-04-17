package com.dev.nicola.allweather.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.base.BaseActivity
import com.dev.nicola.allweather.util.PLACE_ID
import com.dev.nicola.allweather.util.safeLet
import com.ewt.nicola.common.extension.log


class WeatherPlaceActivity : BaseActivity(true) {

    private lateinit var placeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_place)

        placeId = intent.getStringExtra(PLACE_ID)

        observeData()
    }

    private fun observeData() {
        placeViewModel.getPlace(placeId).observe(this, Observer {
            it?.first()?.let {
                supportActionBar?.title = it.name
                safeLet(it.latitude, it.longitude) { lat, lng ->
                    weatherViewModel.getWeather(lat, lng).observe(this, Observer {
                        it.toString().log("WEATHER DATA")
                        //todo fill view
                    })
                    weatherViewModel.updateWeather(lat, lng)
                }
            }
        })
    }
}