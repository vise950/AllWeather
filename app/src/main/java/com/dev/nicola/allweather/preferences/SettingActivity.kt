package com.dev.nicola.allweather.preferences

import android.os.Bundle
import android.preference.PreferenceActivity
import android.view.MenuItem
import com.dev.nicola.allweather.R

/**
 * Created by Nicola on 07/04/2016.
 */
class SettingActivity : AppCompatPreferenceActivity() {


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Utils.setTheme(applicationContext)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        loadHeadersFromResource(R.xml.pref_headers, target)
    }

    override fun isValidFragment(fragmentName: String): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}


//            val isProVersion = PreferencesUtils.getPreferences(activity, resources.getString(R.string.key_pro_version), false)
//
//            val preferenceManager = preferenceManager
//
//            /*
//             * se proVersion==true mostro tutti i provider altrimenti solo uno
//             */
//            val listProvider = findPreference(resources.getString(R.string.key_pref_provider)) as ListPreference
//
//            //            listProvider.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            //                @Override
//            //                public boolean onPreferenceClick(Preference preference) {
//            //                    Log.d("Preferences","click list provider");
//            //                    preference.setEnabled(true);
//            //
//            //                    return true;
//            //                }
//            //            });
//
//            if (isProVersion) {
//                listProvider.entries = resources.getStringArray(R.array.provider_unit_pro)
//                listProvider.entryValues = resources.getStringArray(R.array.provider_value_pro)
//                listProvider.setDefaultValue(resources.getString(R.string.default_pref_provider))
//            } else {
//                listProvider.entries = resources.getStringArray(R.array.provider_unit_free)
//                listProvider.entryValues = resources.getStringArray(R.array.provider_value_free)
//                listProvider.setDefaultValue(resources.getString(R.string.default_pref_provider))
//                //                listProvider.setEnabled(true);
//            }
//
//
//            /*
//             * quando cambio tema riavvio le impostazioni in modo da applicare il tema
//             */
//            val theme = findPreference(resources.getString(R.string.key_pref_theme))
//            theme.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, value ->
//                if (value != preferenceManager.sharedPreferences.getString(resources.getString(R.string.key_pref_theme), resources.getString(R.string.default_pref_theme)))
//                    activity.recreate()
//                true
//            }
//
//
//            /*
//             * se provider==yahoo mostro un avviso perchè yahoo non supporta le previsioni orarie
//             */
//            val provider = findPreference(resources.getString(R.string.key_pref_provider))
//            provider.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, value ->
//                if (value.toString() == "yahoo") {
//                    SnackbarUtils.showSnackbar(activity, view!!, 5)
//                }
//                true
//            }
//    }


//    class UnitPreferenceFragment : PreferenceFragment() {
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            addPreferencesFromResource(R.xml.pref_unit)
//            setHasOptionsMenu(true)
//        }
//
//        override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
//            super.onViewCreated(view, savedInstanceState)
//        }
//
//        //todo non viene invocato
//        override fun onOptionsItemSelected(item: MenuItem): Boolean {
//            when(item.itemId){
//                android.R.id.home-> startActivity(Intent(activity, SettingActivity::class.java))
//            }
//            return super.onOptionsItemSelected(item)
//        }
//    }

//}

