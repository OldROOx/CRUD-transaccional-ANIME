package com.example.gael_somer_anime.core.network

import android.content.SharedPreferences
import androidx.core.content.edit

object SessionManager {
    private const val TOKEN_KEY = "user_token"
    private const val USER_ID_KEY = "user_id"
    private const val USER_KEY = "saved_username"
    private const val PASS_KEY = "saved_password"
    private const val FCM_TOKEN_KEY = "fcm_token"

    fun saveToken(prefs: SharedPreferences, token: String?) {
        prefs.edit { putString(TOKEN_KEY, token) }
    }

    fun fetchToken(prefs: SharedPreferences): String? = prefs.getString(TOKEN_KEY, null)

    fun saveUserId(prefs: SharedPreferences, userId: Int) {
        prefs.edit { putInt(USER_ID_KEY, userId) }
    }

    fun fetchUserId(prefs: SharedPreferences): Int = prefs.getInt(USER_ID_KEY, -1)

    fun saveCredentials(prefs: SharedPreferences, user: String?, pass: String?) {
        prefs.edit {
            putString(USER_KEY, user)
            putString(PASS_KEY, pass)
        }
    }

    fun getSavedUser(prefs: SharedPreferences): String? = prefs.getString(USER_KEY, null)
    fun getSavedPass(prefs: SharedPreferences): String? = prefs.getString(PASS_KEY, null)

    fun clearToken(prefs: SharedPreferences) {
        prefs.edit {
            remove(TOKEN_KEY)
            remove(USER_ID_KEY)
            remove(FCM_TOKEN_KEY)
        }
    }

    fun saveFcmToken(prefs: SharedPreferences, token: String?) {
        prefs.edit { putString(FCM_TOKEN_KEY, token) }
    }

    fun fetchFcmToken(prefs: SharedPreferences): String? = prefs.getString(FCM_TOKEN_KEY, null)

}
