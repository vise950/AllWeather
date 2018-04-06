package com.dev.nicola.allweather.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dev.nicola.allweather.db.DBConstant.LATITUDE
import com.dev.nicola.allweather.db.DBConstant.LONGITUDE
import com.dev.nicola.allweather.db.DBConstant.TABLE_DAILY_DATA_DS
import com.dev.nicola.allweather.db.DBConstant.TABLE_ROOT_DS
import com.dev.nicola.allweather.model.darkSky.DailyDataDarkSky
import com.dev.nicola.allweather.model.darkSky.RootDarkSky

const val DATA_QUERY = """
SELECT * FROM $TABLE_ROOT_DS
WHERE $LATITUDE = :lat
AND $LONGITUDE = :lng
LIMIT 1
"""
const val DAILY_DATA_QUERY = """
SELECT * FROM $TABLE_DAILY_DATA_DS
WHERE $LATITUDE = :lat
AND $LONGITUDE = :lng
"""

@Dao
interface DarkSkyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: RootDarkSky)

    //todo viene salvato solo l'ultimo elelmente a causa del conflitto della primary key
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertDailyData(vararg data: DailyDataDarkSky)

    @Query(DATA_QUERY)
    fun getData(lat: Double, lng: Double): LiveData<RootDarkSky>

    @Query(DAILY_DATA_QUERY)
    fun getDailyData(lat: Double, lng: Double): LiveData<List<DailyDataDarkSky>>
}