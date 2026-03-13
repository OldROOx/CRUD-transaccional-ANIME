package com.example.gael_somer_anime.features.favorites.data.repositories

import com.example.gael_somer_anime.features.favorites.data.local.FavoritesLocalDataSource
import com.example.gael_somer_anime.features.favorites.data.mappers.toDomain
import com.example.gael_somer_anime.features.favorites.data.mappers.toDto
import com.example.gael_somer_anime.features.favorites.domain.entities.Favorite
import com.example.gael_somer_anime.features.favorites.domain.repositories.FavoritesRepository

class FavoritesRepositoryImpl(
    private val localDataSource: FavoritesLocalDataSource
) : FavoritesRepository {

    override fun getFavorites(): List<Favorite> =
        localDataSource.getAll().map { it.toDomain() }

    override fun addFavorite(favorite: Favorite) {
        val current = localDataSource.getAll().toMutableList()
        if (current.none { it.id == favorite.id }) {
            current.add(favorite.toDto())
            localDataSource.save(current)
        }
    }

    override fun removeFavorite(id: Int) {
        val updated = localDataSource.getAll().filter { it.id != id }
        localDataSource.save(updated)
    }

    override fun isFavorite(id: Int): Boolean =
        localDataSource.getAll().any { it.id == id }
}