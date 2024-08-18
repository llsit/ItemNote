package com.example.core.data.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface PreferenceManager {
    fun saveCurrentUserId(userId: String)
    fun getUserId(): String?
    fun clearUserId()
}

class PreferenceManagerImpl @Inject constructor(@ApplicationContext context: Context) :
    PreferenceManager {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(KEY_USER_PREF, Context.MODE_PRIVATE)

    override fun saveCurrentUserId(userId: String) {
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
    }

    override fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    override fun clearUserId() {
        sharedPreferences.edit().remove(KEY_USER_ID).apply()
    }

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_PREF = "UserPrefs"
    }
}