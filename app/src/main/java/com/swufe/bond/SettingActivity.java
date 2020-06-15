package com.swufe.bond;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;


import androidx.appcompat.app.AppCompatActivity;

import com.swufe.bond.R;

public class SettingActivity extends AppCompatActivity {
    private ListPreference list_affairinfo_fontsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_main);

            Preference setText1 = findPreference(getString(R.string.set_text1_key));

            bindPreferenceSummaryToValue(setText1);

            //设置选项2可显示
            //bindPreferenceSummaryToValue(findPreference(getString(R.string.set_text2_key)));

            bindPreferenceSummaryToValue(findPreference(getString(R.string.set_list_key)));
        }
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            preference.setSummary(stringValue);
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}

