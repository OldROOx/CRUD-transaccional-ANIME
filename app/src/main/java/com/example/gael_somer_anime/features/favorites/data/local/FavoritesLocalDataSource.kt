package com.example.gael_somer_anime.features.favorites.data.local

import com.example.gael_somer_anime.features.favorites.data.local.dao.FavoriteDao
import com.example.gael_somer_anime.features.favorites.data.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesLocalDataSource @Inject constructor(
    private val favoriteDao: FavoriteDao
) {
    fun getAll(): Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()

    suspend fun save(favorite: FavoriteEntity) = favoriteDao.insertFavorite(favorite)

    suspend fun delete(id: Int) = favoriteDao.deleteFavoriteById(id)

    suspend fun isFavorite(id: Int): Boolean = favoriteDao.isFavorite(id)
}
