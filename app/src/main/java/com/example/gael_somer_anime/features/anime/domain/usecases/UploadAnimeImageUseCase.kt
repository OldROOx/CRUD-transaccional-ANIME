package com.example.gael_somer_anime.features.anime.domain.usecases

import com.example.gael_somer_anime.features.anime.domain.repositories.AnimeRepository
import java.io.File
import javax.inject.Inject

class UploadAnimeImageUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(animeId: Int, file: File) =
        repository.uploadImage(animeId, file)
}
