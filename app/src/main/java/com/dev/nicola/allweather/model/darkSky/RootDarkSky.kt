package com.dev.nicola.allweather.model.darkSky

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class RootDarkSky(
        @PrimaryKey
        @ColumnInfo(name = "darksky_id")
        val id: String,
        @ColumnInfo(name = "darksky_latitude")
        val latitude: Double?,
        @ColumnInfo(name = "darksky_longitude")
        val longitude: Double?,
        @ColumnInfo(name = "darksky_timezone")
        val timezone: String?,
        @ColumnInfo(name = "darksky_offset")
        val offset: Int?,
        @Embedded
        val currently: CurrentlyDarkSky?,
        @Embedded
        val hourly: HourlyDarkSky?,
        @Embedded
        val daily: DailyDarkSky?)