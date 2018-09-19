package com.dev.nicola.allweather.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.application.Init
import com.dev.nicola.allweather.ui.activity.SettingsActivity
import com.dev.nicola.allweather.util.goto
import com.dev.nicola.allweather.viewmodel.FavoritePlaceViewModel
import com.dev.nicola.allweather.viewmodel.WeatherViewModel
import com.dev.nicola.allweather.viewmodel.viewModel
import io.realm.Realm

abstract class BaseActivity(@LayoutRes private val layoutRes: Int? = null,
                            @MenuRes private val menuRes: Int? = null,
                            private val showBackArrow: Boolean = false) : AppCompatActivity() {

    val realm: Realm by lazy { Init.getRealmInstance() }

    val placeViewModel by lazy { this.viewModel { FavoritePlaceViewModel(application) } }
    val weatherViewModel by lazy { this.viewModel { WeatherViewModel(application) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let { setContentView(it) }

        supportActionBar?.setDisplayHomeAsUpEnabled(showBackArrow)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.clear()
        menuRes?.let {
            menuInflater.inflate(it, menu)
        } ?: run {
            super.onCreateOptionsMenu(menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.action_settings -> goto<SettingsActivity>()
            R.id.action_unlock_pro -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }
}