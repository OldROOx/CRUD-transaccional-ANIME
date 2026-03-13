package com.example.gael_somer_anime.features.favorites.domain.usecases

import com.example.gael_somer_anime.features.favorites.domain.repositories.FavoritesRepository

class GetFavoritesUseCase(private val repository: FavoritesRepository) {
    operator fun invoke() = repository.getFavorites()
}