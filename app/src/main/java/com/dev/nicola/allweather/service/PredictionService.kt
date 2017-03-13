package com.dev.nicola.allweather.service

import com.dev.nicola.allweather.BuildConfig
import com.dev.nicola.allweather.model.Prediction
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

/**
 * Created by Nicola on 18/02/2017.
 */
interface PredictionService {

    companion object {
        private const val URL_TYPE = "maps/api/place/autocomplete"
        private const val RETURN = "/json"
        private const val API_KEY = BuildConfig.GOOGLE_API_AUTOCOMPLETE_KEY
    }

    @GET(URL_TYPE + RETURN)
    fun getPrediction(@Query("input") input: String,
                      @Query("types") types: String = "(cities)",
                      @Query("language") lang: String = Locale.getDefault().language,
                      @Query("key") key: String = API_KEY
    ): Call<Prediction>
}