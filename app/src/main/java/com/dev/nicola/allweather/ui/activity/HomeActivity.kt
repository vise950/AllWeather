package com.dev.nicola.allweather.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev.nicola.allweather.R
import com.dev.nicola.allweather.application.Init
import com.dev.nicola.allweather.repository.FavoritePlaceRepository
import com.dev.nicola.allweather.utils.log
import com.dev.nicola.allweather.viewmodel.FavoritePlaceViewModel
import com.dev.nicola.allweather.viewmodel.viewModel
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {

    companion object {
        const val PLACE_AUTOCOMPLETE_REQUEST_CODE = 563
    }

    private val placeAutocompleteIntent by lazy {
        PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                .setFilter(placeAutocompleteFilter)
                .build(this)
    }

    private val placeAutocompleteFilter by lazy {
        AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build()
    }

    private lateinit var placeViewModel: FavoritePlaceViewModel

    @Inject
    lateinit var placeRepo: FavoritePlaceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        (application as Init).appComponent.inject(this)

        placeViewModel = this.viewModel { FavoritePlaceViewModel(placeRepo) }

        add_place_fab.setOnClickListener {
            searchPlace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val place = PlaceAutocomplete.getPlace(this, data)
                    "Place: " + place.name.log()
                }
                PlaceAutocomplete.RESULT_ERROR -> {
                    val status = PlaceAutocomplete.getStatus(this, data)
                    status.statusMessage.log("error")
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
        }
    }

    private fun searchPlace() {
        try {
            startActivityForResult(placeAutocompleteIntent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace().log("error")
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace().log("error")
        }
    }
}