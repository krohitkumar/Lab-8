package ca.davidpellegrini.scorekeepersolution;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;



public class SettingsFragment extends PreferenceFragment {


    private SharedPreferences prefs;
    private boolean rememberPercent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instead of using a layout, we can use the preferences.xml file as a layout
        // which did a lot more work for us
        addPreferencesFromResource(R.xml.preferences);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

}
