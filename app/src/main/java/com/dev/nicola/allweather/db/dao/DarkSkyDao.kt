package com.dev.nicola.allweather.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.dev.nicola.allweather.model.darkSky.RootDarkSky

@Dao
interface DarkSkyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: RootDarkSky)

    @Query("SELECT * FROM ${RootDarkSky.TABLE}")
    fun getData(): LiveData<RootDarkSky>
}