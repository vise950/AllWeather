package com.dev.nicola.allweather.model.darkSky

import android.arch.persistence.room.ColumnInfo

data class DailyDataDarkSky(
        @ColumnInfo(name = "darksky_daily_time")
        val time: Long?,
        @ColumnInfo(name = "darksky_daily_temperature")
        val summary: String?,
        @ColumnInfo(name = "darksky_daily_icon")
        val icon: String?,
        @ColumnInfo(name = "darksky_daily_sunrise")
        val sunriseTime: Long?,
        @ColumnInfo(name = "darksky_daily_sunset")
        val sunsetTime: Long?,
        @ColumnInfo(name = "darksky_daily_precip_intensity")
        val precipIntensity: Double?,
        @ColumnInfo(name = "darksky_daily_precip_probability")
        val precipProbability: Double?,
        @ColumnInfo(name = "darksky_daily_temperature_min")
        val temperatureMin: Double?,
        @ColumnInfo(name = "darksky_daily_temperature_max")
        val temperatureMax: Double?,
        @ColumnInfo(name = "darksky_daily_dew_point")
        val dewPoint: Double?,
        @ColumnInfo(name = "darksky_daily_humidity")
        val humidity: Double?,
        @ColumnInfo(name = "darksky_daily_wind_speed")
        val windSpeed: Double?,
        @ColumnInfo(name = "darksky_daily_wind_bearing")
        val windBearing: Int?,
        @ColumnInfo(name = "darksky_daily_visibility")
        val visibility: Double?,
        @ColumnInfo(name = "darksky_daily_cloud_cover")
        val cloudCover: Double?,
        @ColumnInfo(name = "darksky_daily_pressure")
        val pressure: Double?)