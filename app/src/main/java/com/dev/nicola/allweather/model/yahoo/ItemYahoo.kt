package com.dev.nicola.allweather.model.yahoo

open class ItemYahoo {
    var title: String? = null
    var lat: String? = null
    var long: String? = null
    var link: String? = null
    var pubDate: String? = null
    var condition: ConditionYahoo? = null
    var forecast: List<ForecastYahoo>? = null
}