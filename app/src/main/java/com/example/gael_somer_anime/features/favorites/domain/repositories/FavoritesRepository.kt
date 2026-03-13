package com.example.gael_somer_anime.features.favorites.domain.repositories

import com.example.gael_somer_anime.features.favorites.domain.entities.Favorite

interface FavoritesRepository {
    fun getFavorites(): List<Favorite>
    fun addFavorite(favorite: Favorite)
    fun removeFavorite(id: Int)
    fun isFavorite(id: Int): Boolean
}