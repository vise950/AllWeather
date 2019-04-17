package com.dev.nicola.allweather.util

import android.annotation.SuppressLint
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import com.dev.nicola.allweather.model.FavoritePlace
import com.dev.nicola.allweather.ui.activity.HomeActivity
import com.ewt.nicola.common.extension.log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationUtil(private val activity: AppCompatActivity) {

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(activity)
    }

    private fun checkPermission(block: () -> Unit = {}) {
        if (LOCATION_PERMISSION.isPermissionGranted(activity))
            block.invoke()
        else
            LOCATION_PERMISSION.requestPermission(activity, LOCATION_PERMISSION_CODE)
    }


    @SuppressLint("MissingPermission")
    fun getLastKnowPosition() {
        checkPermission {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        FavoritePlace().apply {
                            id = uuid()
                            name = location?.getName(activity)
                            latitude = location?.latitude
                            longitude = location?.longitude
                        }.also {
                            (activity as HomeActivity).placeViewModel.addPlace(it)
                            "add place".log()
                        }
                    }
        }
    }
}