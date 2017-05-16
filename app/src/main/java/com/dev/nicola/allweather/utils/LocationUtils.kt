package com.dev.nicola.allweather.utils

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import java.io.IOException
import java.util.*

/**
 * Created by Nicola on 18/08/2016.
 */
object LocationUtils {

    fun getLocationName(context: Context, latitude: Double, longitude: Double): String {
        //todo http://maps.googleapis.com/maps/api/geocode/json?latlng=lat,lng&sensor=true
        var cityName = "Not Found"
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.size > 0) {
                cityName = addresses[0].locality
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return cityName
    }

    fun getCoordinateByName(context: Context, cityName: String): Location {
        var location: Location? = null
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocationName(cityName, 1)
            location = Location(LocationManager.PASSIVE_PROVIDER)
            if (addresses.size > 0) {
                location.latitude = addresses[0].latitude
                location.longitude = addresses[0].longitude
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return location!!
    }
}
