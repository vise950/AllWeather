package com.dev.nicola.allweather.util

import android.Manifest
import com.dev.nicola.allweather.BuildConfig

private const val DARK_SKY_API_KEY = BuildConfig.DARKSKY_API_KEY
private const val APIXU_API_KEY = BuildConfig.APIXU_API_KEY

const val DARK_SKY_BASE_URL = "https://api.darksky.net/forecast/$DARK_SKY_API_KEY/"
const val APIXU_BASE_URL = "https://api.apixu.com/"
const val YAHOO_BASE_URL = "https://query.yahooapis.com/"

const val ALL_WEATHER_PRO_SKU = "com.nicola.dev.allweather_pro"

const val PLACE_AUTOCOMPLETE_REQUEST_CODE = 563
const val PLACE_ID = "placeId"

const val LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
const val LOCATION_PERMISSION_CODE = 348
