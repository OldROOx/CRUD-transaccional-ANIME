package com.example.gael_somer_anime.features.anime.data.mappers

import com.example.gael_somer_anime.features.anime.data.remote.models.AnimeResponseDto
import com.example.gael_somer_anime.features.anime.domain.entities.Anime

fun AnimeResponseDto.toDomain(): Anime {
    return Anime(
        id = id,
        titulo = titulo,
        genero = genero,
        anio = anio,
        descripcion = descripcion
    )
}
