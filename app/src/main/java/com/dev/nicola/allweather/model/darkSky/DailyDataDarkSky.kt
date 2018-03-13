package com.dev.nicola.allweather.model.darkSky

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index

@Entity(tableName = "darksky_daily_data",
        primaryKeys = ["latitude", "longitude"],
        foreignKeys = [(ForeignKey(entity = RootDarkSky::class,
                parentColumns = ["latitude", "longitude"],
                childColumns = ["latitude", "longitude"]))],
        indices = [(Index("latitude")), Index("longitude")])
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
        var pressure: Double
)