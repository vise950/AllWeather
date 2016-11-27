package com.dev.nicola.allweather;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.dev.nicola.allweather.utils.PreferencesUtils;
import com.dev.nicola.allweather.utils.SnackbarUtils;
import com.dev.nicola.allweather.utils.Utils;

/**
 * Created by Nicola on 07/04/2016.
 */
public class AppPreferences extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.setTheme(getApplicationContext());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new myPreferenceFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public static class myPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            boolean isProVersion = PreferencesUtils.getPreferences(getActivity(), getResources().getString(R.string.key_pro_version), false);

            final PreferenceManager preferenceManager = getPreferenceManager();

            /*
             * se proVersion==true mostro tutti i provider altrimenti solo uno
             */
            ListPreference listProvider = (ListPreference) findPreference(getResources().getString(R.string.key_pref_provider));

//            listProvider.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                @Override
//                public boolean onPreferenceClick(Preference preference) {
//                    Log.d("Preferences","click list provider");
//                    preference.setEnabled(true);
//
//                    return true;
//                }
//            });

            if (isProVersion) {
                listProvider.setEntries(getResources().getStringArray(R.array.provider_unit_pro));
                listProvider.setEntryValues(getResources().getStringArray(R.array.provider_value_pro));
                listProvider.setDefaultValue(getResources().getString(R.string.default_pref_provider));
            } else {
                listProvider.setEntries(getResources().getStringArray(R.array.provider_unit_free));
                listProvider.setEntryValues(getResources().getStringArray(R.array.provider_value_free));
                listProvider.setDefaultValue(getResources().getString(R.string.default_pref_provider));
//                listProvider.setEnabled(true);
            }

            
            /*
             * quando cambio tema riavvio le impostazioni in modo da applicare il tema
             */
            Preference theme = findPreference(getResources().getString(R.string.key_pref_theme));
            theme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object value) {
                    if (!value.equals(preferenceManager.getSharedPreferences().getString(getResources().getString(R.string.key_pref_theme), getResources().getString(R.string.default_pref_theme))))
                        getActivity().recreate();
                    return true;
                }
            });


            /*
             * se provider==yahoo mostro un avviso perchè yahoo non supporta le previsioni orarie
             */
            Preference provider = findPreference(getResources().getString(R.string.key_pref_provider));
            provider.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object value) {
                    if (value.toString().equals("yahoo")) {
                        SnackbarUtils.showSnackbar(getActivity(), getView(), 5);
                    }
                    return true;
                }
            });

        }
    }

}
