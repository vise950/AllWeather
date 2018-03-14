package com.dev.nicola.allweather.db.dao

import android.arch.persistence.room.Dao

@Dao
interface DarkSkyDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(data: RootDarkSky)

//    @Query("SELECT * FROM ${RootDarkSky.TABLE}")
//    fun getData(): LiveData<RootDarkSky>
}