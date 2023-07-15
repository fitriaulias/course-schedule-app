package com.dicoding.courseschedule.ui.setting

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var prefNotification: SwitchPreference
    private lateinit var mListPreference: ListPreference


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        mListPreference = getPreferenceManager().findPreference(getString(R.string.pref_key_dark))!!
        mListPreference.setOnPreferenceChangeListener(object: Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
                when(newValue) {
                    "auto" -> updateTheme(-1)
                    "off" -> updateTheme(1)
                    "on" -> updateTheme(2)
                }
                return true
            }
        })

        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify)) as SwitchPreference
        prefNotification.setOnPreferenceChangeListener {preference, newValue ->
            val dailyReminder = DailyReminder()
            if (newValue==true) {
                dailyReminder.setDailyReminder(context as Context)
            } else if(newValue == false) {
                dailyReminder.cancelAlarm(context as Context)
            }
           true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}