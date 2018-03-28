package com.dev.nicola.allweather.model.apixu

import io.realm.annotations.PrimaryKey

open class RootApixu {
    @PrimaryKey
    var id: String? = null
    var location: LocationApixu? = null
    var current: CurrentApixu? = null
    var forecast: ForecastApixu? = null
}