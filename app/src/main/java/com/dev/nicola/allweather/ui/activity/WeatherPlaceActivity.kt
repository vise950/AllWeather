package com.dev.nicola.allweather.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import co.eggon.eggoid.Nil
import co.eggon.eggoid.extension.error
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.viewmodel.FavoritePlaceViewModel
import com.dev.nicola.allweather.viewmodel.WeatherViewModel
import com.dev.nicola.allweather.viewmodel.viewModel


class WeatherPlaceActivity : AppCompatActivity() {

    private lateinit var placeId: String

    private val placeViewModel by lazy { this.viewModel { FavoritePlaceViewModel(application) } }
    private val weatherViewModel by lazy { this.viewModel { WeatherViewModel(application) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_place)

        placeId = intent.getStringExtra(HomeActivity.PLACE_ID)

        initUI()
        observeData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun initUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeData() {
        placeViewModel.getPlace(placeId).observe(this, Observer {
            it?.first()?.let {
                supportActionBar?.title = it.name
                Nil(it.latitude, it.longitude) let { (lat, lng) ->
                    weatherViewModel.getWeather(lat, lng).observe(this, Observer {
                        it.toString().error("WEATHER DATA")
                    })
                    weatherViewModel.updateWeather(lat, lng)
                }
            }
        })

    }
}