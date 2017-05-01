package com.dev.nicola.allweather.retrofit

import com.dev.nicola.allweather.BuildConfig
import com.dev.nicola.allweather.model.GoolgeResponse.Coordinates
import com.dev.nicola.allweather.model.GoolgeResponse.LocationName
import com.dev.nicola.allweather.model.GoolgeResponse.Prediction
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

/**
 * Created by Nicola on 18/02/2017.
 */
interface MapsGoogleApiService {

    companion object {
        private const val URL_AUTOCOMPLETE = "maps/api/place/autocomplete/json"
        private const val API_KEY = BuildConfig.GOOGLE_API_AUTOCOMPLETE_KEY
        private const val URL_GEOCODE = "maps/api/geocode/json"
    }

    @GET(URL_AUTOCOMPLETE)
    fun getPrediction(@Query("input") input: String,
                      @Query("types") types: String = "(cities)",
                      @Query("language") lang: String = Locale.getDefault().language,
                      @Query("key") key: String = API_KEY
    ): Observable<Prediction>

    @GET(URL_GEOCODE)
    fun getLocationName(@Query("latlng") latlng: String): Observable<LocationName>

    @GET(URL_GEOCODE)
    fun getCoordinates(@Query("address") address: String): Observable<Coordinates>
}