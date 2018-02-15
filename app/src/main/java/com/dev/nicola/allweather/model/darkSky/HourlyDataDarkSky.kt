package com.dev.nicola.allweather.model.darkSky

import android.arch.persistence.room.ColumnInfo

data class HourlyDataDarkSky(
        @ColumnInfo(name = "darksky_hourly_time")
        val time: Long?,
        @ColumnInfo(name = "darksky_hourly_summary")
        val summary: String?,
        @ColumnInfo(name = "darksky_hourly_icon")
        val icon: String?,
        @ColumnInfo(name = "darksky_hourly_precip_intensity")
        val precipIntensity: Double?,
        @ColumnInfo(name = "darksky_hourly_temperature")
        val temperature: Double?,
        @ColumnInfo(name = "darksky_hourly_dew_point")
        val dewPoint: Double?,
        @ColumnInfo(name = "darksky_hourly_humidity")
        val humidity: Double?,
        @ColumnInfo(name = "darksky_hourly_wind_speed")
        val windSpeed: Double?,
        @ColumnInfo(name = "darksky_hourly_wind_bearing")
        val windBearing: Int?,
        @ColumnInfo(name = "darksky_hourly_visibility")
        val visibility: Double?,
        @ColumnInfo(name = "darksky_hourly_cloud_cover")
        val cloudCover: Double?,
        @ColumnInfo(name = "darksky_hourly_pressure")
        val pressure: Double?)