package android.curso.com.br.rasperdroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by hsiqueira on 28/08/2015.
 */
public class ConfiguracoesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_EDIT_TEXT_PREFERENCE = "web_service_text_preference";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.layout.configuracoes);

    }


    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
        updatePreference(KEY_EDIT_TEXT_PREFERENCE);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePreference(key);
    }

    private void updatePreference(String key) {
        if (key.equals(KEY_EDIT_TEXT_PREFERENCE)) {
            Preference preference = findPreference(key);
            if (preference instanceof EditTextPreference) {
                EditTextPreference editTextPreference = (EditTextPreference) preference;
                String text = editTextPreference.getText();
                if (text != null && editTextPreference.getText().trim().length() > 0) {
                    editTextPreference.setSummary("Web Service URL:  " + editTextPreference.getText());
                } else {
                    editTextPreference.setSummary(R.string.summary_web_service_preference);
                }
            }
        }
    }


}