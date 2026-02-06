package com.example.gael_somer_anime.features.anime.domain.usecases

import com.example.gael_somer_anime.features.anime.domain.repositories.AnimeRepository

class CreateAnimeUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(titulo: String, genero: String, anio: Int, descripcion: String) = repository.createAnime(titulo, genero, anio, descripcion)
}
