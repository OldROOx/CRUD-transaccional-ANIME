package com.example.gael_somer_anime.features.favorites.domain.repositories

import com.example.gael_somer_anime.features.favorites.domain.entities.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavorites(): Flow<List<Favorite>>
    suspend fun addFavorite(favorite: Favorite)
    suspend fun removeFavorite(id: Int)
    suspend fun isFavorite(id: Int): Boolean
}
