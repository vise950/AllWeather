package com.dev.nicola.allweather.model.darkSky

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import com.dev.nicola.allweather.model.darkSky.RootDarkSky.Companion.TABLE

@Entity(tableName = TABLE, primaryKeys = ["latitude", "longitude"])
data class RootDarkSky(
        var latitude: Double,
        var longitude: Double,
//        @Embedded
//        val currently: CurrentlyDarkSky?,
//        @Embedded
//        val hourly: HourlyDarkSky?,
        @Embedded(prefix = "dark_sky_root_daily_") var daily: DailyDarkSky
) {
    companion object {
        const val TABLE = "dark_sky"
    }

    fun updateKeys() {
        daily.data.forEach {
            it.latitude = latitude
            it.longitude = longitude
        }
    }
}