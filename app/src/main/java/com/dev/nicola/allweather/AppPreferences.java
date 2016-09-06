package com.dev.nicola.allweather;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

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

        getFragmentManager().beginTransaction().replace(android.R.id.content, new myPreferenceFragment()).commit();
    }


    public static class myPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            final PreferenceManager preferenceManager = getPreferenceManager();


            Preference theme = findPreference(getResources().getString(R.string.key_pref_theme));
            theme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    if (!newValue.equals(preferenceManager.getSharedPreferences().getString(getResources().getString(R.string.key_pref_theme), getResources().getString(R.string.default_pref_theme)))) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(R.string.dialog_preference_theme);
                        builder.setCancelable(false);
                        builder.setPositiveButton(R.string.action_OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().recreate();
                            }
                        });
                        builder.setNegativeButton(R.string.action_cancel, null);
                        builder.create().show();
                    }

                    return true;
                }
            });
        }
    }

}
