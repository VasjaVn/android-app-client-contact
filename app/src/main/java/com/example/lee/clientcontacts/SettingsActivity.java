package com.example.lee.clientcontacts;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity {
    public static SettingsActivity sInstance;

    private String ipAddressWebService = "192.168.1.2";

    public SettingsActivity() {
        sInstance = this;
    }

    public String getIpAddressWebService() {
        return ipAddressWebService;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        Preference.OnPreferenceChangeListener changeListener = new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getApplicationContext(), (String)newValue, Toast.LENGTH_SHORT).show();
                SharedData.ipAddressWebService = (String) newValue;
                return true;
            }
        };

        EditTextPreference pref = (EditTextPreference) SettingsActivity.sInstance.findPreference("pr_address_webservice");
        pref.setOnPreferenceChangeListener(changeListener);
    }
}