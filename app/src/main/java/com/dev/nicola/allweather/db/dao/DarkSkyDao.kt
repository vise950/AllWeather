package com.dev.nicola.allweather.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dev.nicola.allweather.model.darkSky.DailyDataDarkSky
import com.dev.nicola.allweather.model.darkSky.RootDarkSky

const val WEATHER_QUERY = """
SELECT * FROM ${RootDarkSky.TABLE}
WHERE latitude = :lat
AND longitude = :lng
LIMIT 1
"""
const val DAILY_DATA_QUERY = """
SELECT * FROM ${DailyDataDarkSky.TABLE}
WHERE latitude = :lat
AND longitude = :lng
"""

@Dao
interface DarkSkyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: RootDarkSky)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDailyData(vararg data: DailyDataDarkSky)

    @Query(WEATHER_QUERY)
    fun getData(lat: Double, lng: Double): LiveData<RootDarkSky>

    @Query(DAILY_DATA_QUERY)
    fun getDailyData(lat: Double, lng: Double): LiveData<List<DailyDataDarkSky>>
}