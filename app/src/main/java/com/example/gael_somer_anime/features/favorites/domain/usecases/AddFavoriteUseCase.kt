package com.example.gael_somer_anime.features.favorites.domain.usecases

import com.example.gael_somer_anime.features.favorites.domain.entities.Favorite
import com.example.gael_somer_anime.features.favorites.domain.repositories.FavoritesRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(private val repository: FavoritesRepository) {
    operator fun invoke(favorite: Favorite) = repository.addFavorite(favorite)
}