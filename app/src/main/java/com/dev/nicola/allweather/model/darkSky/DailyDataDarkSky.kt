package com.dev.nicola.allweather.model.darkSky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import com.dev.nicola.allweather.db.DBConstant.LATITUDE
import com.dev.nicola.allweather.db.DBConstant.LONGITUDE
import com.dev.nicola.allweather.db.DBConstant.TABLE_DAILY_DATA_DS
import com.dev.nicola.allweather.db.DBConstant.TIME

@Entity(tableName = TABLE_DAILY_DATA_DS,
        primaryKeys = [LATITUDE, LONGITUDE, TIME],
        foreignKeys = [(ForeignKey(entity = RootDarkSky::class, parentColumns = [LATITUDE, LONGITUDE], childColumns = [LATITUDE, LONGITUDE]))],
        indices = [Index(LATITUDE), Index(LONGITUDE)])
data class DailyDataDarkSky(
        var latitude: Double,
        var longitude: Double,
        var time: Long,
        var summary: String,
        var icon: String,
        var sunriseTime: Long,
        var sunsetTime: Long,
        var precipIntensity: Double,
        var precipProbability: Double,
        var temperatureMin: Double,
        var temperatureMax: Double,
        var dewPoint: Double,
        var humidity: Double,
        var windSpeed: Double,
        var windBearing: Int,
        var visibility: Double,
        var cloudCover: Double,
        var pressure: Double)