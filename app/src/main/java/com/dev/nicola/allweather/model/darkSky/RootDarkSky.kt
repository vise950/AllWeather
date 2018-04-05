package com.dev.nicola.allweather.model.darkSky

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import com.dev.nicola.allweather.model.darkSky.RootDarkSky.Companion.TABLE

@Entity(tableName = TABLE, primaryKeys = ["latitude", "longitude"])
data class RootDarkSky(
        var latitude: Double,
        var longitude: Double,
        var timezone: String,
        @Embedded(prefix = TABLE_CURRENTLY) var currently: CurrentlyDarkSky,
        @Embedded(prefix = TABLE_DAILY) var daily: DailyDarkSky
//        @Embedded val hourly: HourlyDarkSky?,
) {
    companion object {
        const val TABLE = "dark_sky"
        const val TABLE_CURRENTLY = "dark_sky_currently"
        const val TABLE_DAILY = "dark_sky_daily"
    }

    fun updateKeys() {
        daily.data.forEach {
            it.latitude = latitude
            it.longitude = longitude
        }
    }
}