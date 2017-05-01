package com.dev.nicola.allweather.application

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.squareup.leakcanary.LeakCanary
import io.realm.Realm
import io.realm.RealmConfiguration

class Init : Application() {

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

        Realm.init(this)
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}
