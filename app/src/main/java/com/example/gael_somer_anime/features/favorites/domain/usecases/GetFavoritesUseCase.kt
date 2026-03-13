package com.example.gael_somer_anime.features.favorites.domain.usecases

import com.example.gael_somer_anime.features.favorites.domain.entities.Favorite
import com.example.gael_somer_anime.features.favorites.domain.repositories.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: FavoritesRepository) {
    operator fun invoke(): Flow<List<Favorite>> = repository.getFavorites()
}
