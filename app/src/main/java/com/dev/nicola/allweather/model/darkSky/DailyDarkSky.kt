package com.dev.nicola.allweather.model.darkSky

import android.arch.persistence.room.Ignore

data class DailyDarkSky(
        var summary: String = "",
        var icon: String = "",
        @Ignore
        var data: List<DailyDataDarkSky> = listOf()
)