//package com.dev.nicola.allweather.model.darkSky
//
//import android.arch.persistence.room.*
//
//@Entity(tableName = "dark_sky")
//data class RootDarkSky constructor(
//        @PrimaryKey
//        val id: String,
//        val latitude: Double,
//        val longitude: Double,
//        val timezone: String,
//        val offset: Int,
////        @Embedded
////        val currently: CurrentlyDarkSky?,
////        @Embedded
////        val hourly: HourlyDarkSky?,
//        @Embedded(prefix = "dark_sky_root_daily_")
//        val daily: DailyDarkSky
//)