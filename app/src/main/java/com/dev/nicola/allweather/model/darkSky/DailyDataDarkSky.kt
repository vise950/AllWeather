//package com.dev.nicola.allweather.model.darkSky
//
//import android.arch.persistence.room.Entity
//import android.arch.persistence.room.PrimaryKey
//
//@Entity(tableName = "darksky_daily")
//data class DailyDataDarkSky constructor(
//        @PrimaryKey(autoGenerate = true)
//        val id: Int,
//        val rootId: String,
//        val time: Long,
//        val summary: String,
//        val icon: String,
//        val sunriseTime: Long,
//        val sunsetTime: Long,
//        val precipIntensity: Double,
//        val precipProbability: Double,
//        val temperatureMin: Double,
//        val temperatureMax: Double,
//        val dewPoint: Double,
//        val humidity: Double,
//        val windSpeed: Double,
//        val windBearing: Int,
//        val visibility: Double,
//        val cloudCover: Double,
//        val pressure: Double
//)