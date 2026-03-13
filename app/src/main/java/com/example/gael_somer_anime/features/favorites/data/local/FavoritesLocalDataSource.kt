package com.example.gael_somer_anime.features.favorites.data.local

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoritesLocalDataSource(context: Context) {

    private val prefs = context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getAll(): List<FavoriteDto> {
        val json = prefs.getString(KEY_FAVORITES, null) ?: return emptyList()
        val type = object : TypeToken<List<FavoriteDto>>() {}.type
        return gson.fromJson(json, type)
    }

    fun save(favorites: List<FavoriteDto>) {
        prefs.edit { putString(KEY_FAVORITES, gson.toJson(favorites)) }
    }

    companion object {
        private const val KEY_FAVORITES = "favorites_list"
    }
}