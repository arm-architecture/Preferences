package com.example.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserSharedPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Preferences.PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserPreferences(userPreferences: UserPreferences) {
        sharedPreferences.edit().apply {
            putString(Preferences.KEY_NAME, userPreferences.name)
            putString(Preferences.KEY_PASSWORD, userPreferences.password)
            putBoolean(Preferences.KEY_REMEMBER, userPreferences.remember)
            apply()
        }
    }

    fun getUserPreferences(): UserPreferences {
        val name = sharedPreferences.getString(Preferences.KEY_NAME, "") ?: ""
        val password = sharedPreferences.getString(Preferences.KEY_PASSWORD, "") ?: ""
        val remember = sharedPreferences.getBoolean(Preferences.KEY_REMEMBER, false)
        return UserPreferences(name, password, remember)
    }

    fun clearPreferences() {
        sharedPreferences.edit { clear() }
    }
}