package com.dev.nicola.allweather.preferences

import android.os.Bundle
import android.preference.PreferenceFragment
import android.view.View
import com.dev.nicola.allweather.R

/**
 * Created by Nicola on 25/02/2017.
 */
class UnitPreferenceFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_unit)
            setHasOptionsMenu(true)
        }

        override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        }
}