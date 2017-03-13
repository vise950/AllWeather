package com.dev.nicola.allweather.application

import android.app.Application

import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by Nicola on 29/12/2016.
 */

class Init : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)
    }
}
