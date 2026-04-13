package com.example.gael_somer_anime.features.anime.domain.usecases

import com.example.gael_somer_anime.features.anime.domain.repositories.AnimeRepository
import javax.inject.Inject

class LikeAnimeUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(id: Int) = repository.likeAnime(id)
}
