//package com.dev.nicola.allweather.model.darkSky
//
//import android.arch.persistence.room.ColumnInfo
//import android.arch.persistence.room.Embedded
//import android.arch.persistence.room.PrimaryKey
//import android.arch.persistence.room.Relation
//
//data class DailyDarkSky constructor(
//        @PrimaryKey(autoGenerate = true)
//        val id: Int,
//        val summary: String,
//        val icon: String
////        @Relation(parentColumn = "dark_sky_root_daily_id", entityColumn = "rootId", entity = DailyDataDarkSky::class)
////        val data: List<DailyDataDarkSky>
//)