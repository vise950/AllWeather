package com.dev.nicola.allweather.model.darkSky

import android.arch.persistence.room.ColumnInfo

data class DailyDarkSky(
        @ColumnInfo(name = "darksky_root_daily_summary")
        val summary: String?,
        @ColumnInfo(name = "darksky_root_daily_icon")
        val icon: String?
//        @Embedded
//        val data: List<DailyDataDarkSky>?
)