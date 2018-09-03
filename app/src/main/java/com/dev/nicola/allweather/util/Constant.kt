package com.dev.nicola.allweather.util

import com.dev.nicola.allweather.BuildConfig


private const val DARK_SKY_API_KEY = BuildConfig.DARKSKY_API_KEY
private const val APIXU_API_KEY = BuildConfig.APIXU_API_KEY

const val DARK_SKY_BASE_URL = "https://api.darksky.net/forecast/$DARK_SKY_API_KEY/"
const val APIXU_BASE_URL = "https://api.apixu.com/"
const val YAHOO_BASE_URL = "https://query.yahooapis.com/"