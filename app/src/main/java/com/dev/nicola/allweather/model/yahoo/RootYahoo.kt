package com.dev.nicola.allweather.model.yahoo

import io.realm.annotations.PrimaryKey

open class RootYahoo {
    @PrimaryKey
    var id: String? = null
    var query: QueryYahoo? = null
}