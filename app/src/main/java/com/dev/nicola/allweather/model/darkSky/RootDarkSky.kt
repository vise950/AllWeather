package com.dev.nicola.allweather.model.darkSky

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import com.dev.nicola.allweather.db.DBConstant.LATITUDE
import com.dev.nicola.allweather.db.DBConstant.LONGITUDE
import com.dev.nicola.allweather.db.DBConstant.TABLE_CURRENTLY_DS
import com.dev.nicola.allweather.db.DBConstant.TABLE_DAILY_DS
import com.dev.nicola.allweather.db.DBConstant.TABLE_HOURLY_DS
import com.dev.nicola.allweather.db.DBConstant.TABLE_ROOT_DS

@Entity(tableName = TABLE_ROOT_DS, primaryKeys = [LATITUDE, LONGITUDE])
data class RootDarkSky(
        var latitude: Double,
        var longitude: Double,
        var timezone: String,
        @Embedded(prefix = TABLE_CURRENTLY_DS) var currently: CurrentlyDarkSky,
        @Embedded(prefix = TABLE_DAILY_DS) var daily: DailyDarkSky,
        @Embedded(prefix = TABLE_HOURLY_DS) var hourly: HourlyDarkSky
) {
    fun updateKeys() {
        daily.data.forEach {
            it.latitude = latitude
            it.longitude = longitude
        }
        hourly.data.forEach {
            it.latitude = latitude
            it.longitude = longitude
        }
    }
}