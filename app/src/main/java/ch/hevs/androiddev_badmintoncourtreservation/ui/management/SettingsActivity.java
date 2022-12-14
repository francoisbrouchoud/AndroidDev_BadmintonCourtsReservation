package ch.hevs.androiddev_badmintoncourtreservation.ui.management;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ch.hevs.androiddev_badmintoncourtreservation.R;
import ch.hevs.androiddev_badmintoncourtreservation.ui.BaseActivity;

public class SettingsActivity extends BaseActivity {

    protected SharedPreferences sharedPreferences;
    private Button aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);

        setTitle(getString(R.string.action_settings));

        //Get the default shared preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //Set a change listener on the shared preferences to change the theme on the current page
        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if (sharedPreferences.getBoolean("pref_darkMode", false)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                setTheme(R.style.Theme_AndroidDev_BadmintonCourtReservation_night);
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                setTheme(R.style.Theme_AndroidDev_BadmintonCourtReservation);
            }
        });

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.settings, new DarkModeSettingFragment()).commit();

        aboutButton = findViewById(R.id.action_about);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    public static class DarkModeSettingFragment extends PreferenceFragmentCompat{
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }
    }
}