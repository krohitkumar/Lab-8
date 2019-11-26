package ca.davidpellegrini.scorekeepersolution;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // In our MainActivity.java we used
        //      setContentView(R.layout.activity_main);
        // to attach a layout to our activity. This time we are adding a Fragment
        getFragmentManager()
                .beginTransaction()
                // instead of new SettingsFragment, we could use a different Fragment
                // if we have one
                .replace(android.R.id.content,
                        new SettingsFragment())
                .commit();
    }
}