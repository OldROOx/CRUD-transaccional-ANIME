package com.example.gael_somer_anime.features.anime.domain.usecases

import com.example.gael_somer_anime.features.anime.domain.entities.Anime
import com.example.gael_somer_anime.features.anime.domain.repositories.AnimeRepository
import javax.inject.Inject

class GetAnimeByIdUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(id: Int): Anime? = repository.getAnimeById(id)
}
