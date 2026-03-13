package com.example.gael_somer_anime.features.favorites.domain.usecases

import com.example.gael_somer_anime.features.favorites.domain.repositories.FavoritesRepository

class RemoveFavoriteUseCase(private val repository: FavoritesRepository) {
    operator fun invoke(id: Int) = repository.removeFavorite(id)
}