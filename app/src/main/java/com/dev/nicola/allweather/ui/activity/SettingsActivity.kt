package com.dev.nicola.allweather.ui.activity

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.dev.nicola.allweather.base.BaseActivity

class SettingsActivity : BaseActivity(showBackArrow = true) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Utils.changeTheme(this)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, AppPreferenceFragment()).commit()
    }

    class AppPreferenceFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(com.dev.nicola.allweather.R.xml.preferences,rootKey)
        }

//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            addPreferencesFromResource(com.dev.nicola.allweather.R.xml.preferences)

//            val isProVersion = PreferencesHelper.isProVersion(activity) as Boolean
//
//            val provider = findPreference(PreferencesHelper.KEY_PREF_WEATHER_PROVIDER) as ListPreference
//            provider.setOnPreferenceChangeListener { preference, value ->
//                if (value == WeatherProvider.YAHOO) {
//                    SnackBarHelper.yahooProvider(activity, view)
//                }
//                true
//            }
//            provider.isEnabled = isProVersion
//
//            val theme = findPreference(PreferencesHelper.KEY_PREF_THEME)
//            theme.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, value ->
//                // entra se clicco su un item del dialog (anche se è lo stesso) quindi controllo se il value nuovo e diverso da quello vecchio
//                if (PreferencesHelper.isPreferenceChange(activity, PreferencesHelper.KEY_PREF_THEME, PreferencesHelper.KEY_PREF_THEME, value.toString()) == true) {
//                    activity.recreate()
//                }
//                true
//            }
//        }
    }

}
