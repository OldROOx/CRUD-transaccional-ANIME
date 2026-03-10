package com.example.gael_somer_anime.features.anime.domain.usecases

import com.example.gael_somer_anime.features.anime.domain.repositories.AnimeRepository
import javax.inject.Inject

class GetAnimesUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke() = repository.getAnimes()
}