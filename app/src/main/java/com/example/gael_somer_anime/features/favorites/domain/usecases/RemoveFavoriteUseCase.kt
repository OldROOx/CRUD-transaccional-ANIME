package com.example.gael_somer_anime.features.favorites.domain.usecases

import com.example.gael_somer_anime.features.favorites.domain.repositories.FavoritesRepository
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(private val repository: FavoritesRepository) {
    operator fun invoke(id: Int) = repository.removeFavorite(id)
}