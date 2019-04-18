package com.dev.nicola.allweather.util

import androidx.appcompat.app.AppCompatActivity
import com.ewt.nicola.common.extension.log
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocomplete

class PlaceAutocompleteUtil(private val activity: AppCompatActivity) {

    private val placeAutocompleteIntent by lazy {
        PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                .setFilter(placeAutocompleteFilter)
                .build(activity)
    }

    private val placeAutocompleteFilter by lazy {
        AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build()
    }

    fun gotoSearchPlace() {
        try {
            activity.startActivityForResult(placeAutocompleteIntent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace().log("error")
//            Snackbar.make(root_view, "Errore", Snackbar.LENGTH_LONG).show()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace().log("error")
//            Snackbar.make(root_view, "Errore", Snackbar.LENGTH_LONG).show()
        }
    }
}