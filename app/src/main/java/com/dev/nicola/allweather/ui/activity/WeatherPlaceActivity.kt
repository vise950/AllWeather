package com.dev.nicola.allweather.ui.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.MenuItem
import co.eggon.eggoid.Nil
import co.eggon.eggoid.extension.error
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.application.Init
import com.dev.nicola.allweather.model.FavoritePlace
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

    private var place: FavoritePlace? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_place)

        (application as Init).appComponent.inject(this)

        placeId = intent.getStringExtra(HomeActivity.PLACE_ID)

        initUI()

        placeViewModel = this.viewModel { FavoritePlaceViewModel(placeRepo, placeId) }
        weatherViewModel = this.viewModel { WeatherViewModel(weatherRepo, disposables) }
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
        placeViewModel.place.observe(this, Observer {
            place = it
            supportActionBar?.title = it?.name
            Nil(it?.latitude, it?.longitude) let { (lat, lng) ->
                weatherViewModel.updateWeather(Pair(lat, lng))
            }
        })

        weatherViewModel.weatherData.observe(this, Observer {
            it.toString().error("WEATHER DATA")
        })
    }
}