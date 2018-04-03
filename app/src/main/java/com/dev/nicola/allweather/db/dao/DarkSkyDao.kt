package com.dev.nicola.allweather.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dev.nicola.allweather.model.darkSky.RootDarkSky

const val WEATHER_QUERY = """
SELECT * FROM ${RootDarkSky.TABLE}
WHERE latitude = :lat
AND longitude = :lng
LIMIT 1
"""

@Dao
interface DarkSkyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: RootDarkSky)

    @Query(WEATHER_QUERY)
    fun getData(lat: Double, lng: Double): LiveData<RootDarkSky>
}