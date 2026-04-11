package com.example.gael_somer_anime.core.network

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SessionManager {
    private const val PREFS_NAME = "anime_prefs"
    private const val TOKEN_KEY = "user_token"
    private const val USER_ID_KEY = "user_id"
    private const val USER_KEY = "saved_username"
    private const val PASS_KEY = "saved_password"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(context: Context, token: String?) {
        getPrefs(context).edit { putString(TOKEN_KEY, token) }
    }

    fun fetchToken(context: Context): String? = getPrefs(context).getString(TOKEN_KEY, null)

    fun saveUserId(context: Context, userId: Int) {
        getPrefs(context).edit { putInt(USER_ID_KEY, userId) }
    }

    fun fetchUserId(context: Context): Int = getPrefs(context).getInt(USER_ID_KEY, -1)

    fun saveCredentials(context: Context, user: String?, pass: String?) {
        getPrefs(context).edit {
            putString(USER_KEY, user)
            putString(PASS_KEY, pass)
        }
    }

    fun getSavedUser(context: Context): String? = getPrefs(context).getString(USER_KEY, null)
    fun getSavedPass(context: Context): String? = getPrefs(context).getString(PASS_KEY, null)

    fun clearToken(context: Context) {
        getPrefs(context).edit {
            remove(TOKEN_KEY)
            remove(USER_ID_KEY)
        }
    }

    fun clearAll(context: Context) {
        getPrefs(context).edit { clear() }
    }
}
