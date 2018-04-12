package com.dev.nicola.allweather.model.darkSky

import android.arch.persistence.room.Ignore

data class HourlyDarkSky(
        var summary: String = "",
        var icon: String = "",
        @Ignore
        var data: List<HourlyDataDarkSky> = listOf()
)