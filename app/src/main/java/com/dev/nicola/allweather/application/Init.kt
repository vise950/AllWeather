package com.dev.nicola.allweather.application

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import io.realm.Realm
import io.realm.RealmConfiguration


class Init : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}
