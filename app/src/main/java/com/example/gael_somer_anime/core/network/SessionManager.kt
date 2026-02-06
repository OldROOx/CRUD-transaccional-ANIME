package com.example.gael_somer_anime.core.network

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SessionManager {
    private const val PREFS_NAME = "anime_prefs"
    private const val TOKEN_KEY = "user_token"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(context: Context, token: String?) {
        getPrefs(context).edit {
            putString(TOKEN_KEY, token)
        }
    }

    fun fetchToken(context: Context): String? {
        return getPrefs(context).getString(TOKEN_KEY, null)
    }
}