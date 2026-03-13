package com.example.gael_somer_anime.features.favorites.domain.usecases

import com.example.gael_somer_anime.features.favorites.domain.repositories.FavoritesRepository

class IsFavoriteUseCase(private val repository: FavoritesRepository) {
    operator fun invoke(id: Int) = repository.isFavorite(id)
}