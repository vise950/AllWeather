package com.dev.nicola.allweather.application;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Nicola on 29/12/2016.
 */

public class Init extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
