package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmObject

open class ChannelYahoo: RealmObject() {
    var units: UnitsYahoo? = null
    var title: String? = null
    var link: String? = null
    var description: String? = null
    var language: String? = null
    var lastBuildDate: String? = null
    var ttl: String? = null
    var location: LocationYahoo? = null
    var wind: WindYahoo? = null
    var atmosphereYahoo: AtmosphereYahoo? = null
    var astronomyYahoo: AstronomyYahoo? = null
    var image: ImageYahoo? = null
    var item: ItemYahoo? = null
}