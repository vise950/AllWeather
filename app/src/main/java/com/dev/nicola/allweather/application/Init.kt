package com.dev.nicola.allweather.application

import android.annotation.SuppressLint
import android.app.Application
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import io.realm.Realm
import io.realm.RealmConfiguration
import net.danlew.android.joda.JodaTimeAndroid

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
        Realm.init(this)
        initRealm()
        JodaTimeAndroid.init(this)
    }

    private fun initRealm() {
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build())
    }
}

@GlideModule
class AppGlideModule : AppGlideModule()