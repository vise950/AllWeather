package com.dev.nicola.allweather.ui.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import co.eggon.eggoid.Nil
import co.eggon.eggoid.extension.error
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.base.BaseActivity


class WeatherPlaceActivity : BaseActivity(R.layout.activity_weather_place, showBackArrow = true) {

    private lateinit var placeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        placeId = intent.getStringExtra(HomeActivity.PLACE_ID)

        observeData()
    }

    private fun observeData() {
        placeViewModel.getPlace(placeId).observe(this, Observer {
            it?.first()?.let {
                supportActionBar?.title = it.name
                Nil(it.latitude, it.longitude) let { (lat, lng) ->
                    weatherViewModel.getWeather(lat, lng).observe(this, Observer {
                        it.toString().error("WEATHER DATA")
                        //todo fill view
                    })
                    weatherViewModel.updateWeather(lat, lng)
                }
            }
        })
    }
}