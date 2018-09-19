package com.dev.nicola.allweather.application

import android.annotation.SuppressLint
import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class Init : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var realm: Realm? = null

        fun getRealmInstance(): Realm = realm ?: run {
            Realm.getDefaultInstance().also {
                realm = it
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build())
        //todo migration
    }
}