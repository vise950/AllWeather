package com.dev.nicola.allweather.model.yahoo

import io.realm.RealmObject

open class ResultsYahoo: RealmObject() {
    var channel: ChannelYahoo? = null
}