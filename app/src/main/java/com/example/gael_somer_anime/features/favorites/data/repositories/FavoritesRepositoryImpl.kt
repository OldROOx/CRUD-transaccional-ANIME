package com.example.gael_somer_anime.features.favorites.data.repositories

import com.example.gael_somer_anime.features.favorites.data.local.FavoritesLocalDataSource
import com.example.gael_somer_anime.features.favorites.data.mappers.toDomain
import com.example.gael_somer_anime.features.favorites.data.mappers.toEntity
import com.example.gael_somer_anime.features.favorites.domain.entities.Favorite
import com.example.gael_somer_anime.features.favorites.domain.repositories.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val localDataSource: FavoritesLocalDataSource
) : FavoritesRepository {

    override fun getFavorites(): Flow<List<Favorite>> {
        return localDataSource.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addFavorite(favorite: Favorite) {
        localDataSource.save(favorite.toEntity())
    }

    override suspend fun removeFavorite(id: Int) {
        localDataSource.delete(id)
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return localDataSource.isFavorite(id)
    }
}
