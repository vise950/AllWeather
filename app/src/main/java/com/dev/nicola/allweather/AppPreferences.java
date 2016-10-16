package com.dev.nicola.allweather;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.dev.nicola.allweather.utils.PreferencesUtils;

/**
 * Created by Nicola on 07/04/2016.
 */
public class AppPreferences extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(getResources().getString(R.string.key_pref_theme), getResources().getString(R.string.default_pref_theme)).equals("light"))
            setTheme(R.style.AppTheme);
        else
            setTheme(R.style.AppThemeDark);

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
            ListPreference lp = (ListPreference) findPreference(getResources().getString(R.string.key_pref_provider));
            if (isProVersion) {
                lp.setEntries(getResources().getStringArray(R.array.provider_unit_pro));
                lp.setEntryValues(getResources().getStringArray(R.array.provider_value_pro));
                lp.setDefaultValue(getResources().getString(R.string.default_pref_provider));
            } else {
                lp.setEntries(getResources().getStringArray(R.array.provider_unit_free));
                lp.setEntryValues(getResources().getStringArray(R.array.provider_value_free));
                lp.setDefaultValue(getResources().getString(R.string.default_pref_provider));
            }

            
            /*
             * quando cambio tema riavvio le impostazioni in modo da applicare il tema
             */
            final Preference theme = findPreference(getResources().getString(R.string.key_pref_theme));
            theme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object value) {
                    if (!value.equals(preferenceManager.getSharedPreferences().getString(getResources().getString(R.string.key_pref_theme), getResources().getString(R.string.default_pref_theme))))
                        getActivity().recreate();
                    return true;
                }
            });


            /*
             * se provider==yahoo mostro un dialog 
             */
            Preference provider = findPreference(getResources().getString(R.string.key_pref_provider));
            provider.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object value) {
                    if (value.toString().equals("yahoo")) {
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setMessage(getResources().getString(R.string.dialog_preference_yahoo));
                        dialog.setPositiveButton(getResources().getString(R.string.action_OK), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        dialog.show();
                    }
                    return true;
                }
            });

        }
    }

}
