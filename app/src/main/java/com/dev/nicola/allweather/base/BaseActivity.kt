package com.dev.nicola.allweather.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dev.nicola.allweather.application.Init
import com.dev.nicola.allweather.viewmodel.FavoritePlaceViewModel
import com.dev.nicola.allweather.viewmodel.WeatherViewModel
import com.dev.nicola.allweather.viewmodel.viewModel
import io.realm.Realm

abstract class BaseActivity(private val showBackArrow: Boolean = false) : AppCompatActivity() {

    val realm: Realm by lazy { Init.getRealmInstance() }

    val placeViewModel by lazy { this.viewModel { FavoritePlaceViewModel(application) } }
    val weatherViewModel by lazy { this.viewModel { WeatherViewModel(application) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackArrow)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}