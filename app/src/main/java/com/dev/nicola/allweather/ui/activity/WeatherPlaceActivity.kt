package com.dev.nicola.allweather.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import co.eggon.eggoid.Nil
import co.eggon.eggoid.extension.error
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.application.Injector
import com.dev.nicola.allweather.repository.FavoritePlaceRepository
import com.dev.nicola.allweather.repository.WeatherRepository
import com.dev.nicola.allweather.viewmodel.FavoritePlaceViewModel
import com.dev.nicola.allweather.viewmodel.WeatherViewModel
import com.dev.nicola.allweather.viewmodel.viewModel
import javax.inject.Inject


class WeatherPlaceActivity : BaseActivity() {

    private lateinit var placeId: String

    private lateinit var placeViewModel: FavoritePlaceViewModel
    private lateinit var weatherViewModel: WeatherViewModel

    @Inject
    lateinit var placeRepo: FavoritePlaceRepository

    @Inject
    lateinit var weatherRepo: WeatherRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_place)

        Injector.get().inject(this)

        placeId = intent.getStringExtra(HomeActivity.PLACE_ID)

        initUI()

        placeViewModel = this.viewModel { FavoritePlaceViewModel(placeRepo) }
        weatherViewModel = this.viewModel { WeatherViewModel(weatherRepo) }
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