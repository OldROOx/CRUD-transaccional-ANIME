package com.example.gael_somer_anime.features.anime.domain.usecases

import com.example.gael_somer_anime.features.anime.domain.repositories.AnimeRepository
import javax.inject.Inject

class UpdateAnimeUseCase @Inject constructor(private val repository: AnimeRepository) {
    suspend operator fun invoke(id: Int, titulo: String, genero: String, anio: Int, descripcion: String, tags: String = "") =
        repository.updateAnime(id, titulo, genero, anio, descripcion, tags)
}