package com.example.gael_somer_anime.features.anime.data.mappers

import com.example.gael_somer_anime.features.anime.data.local.entities.AnimeEntity
import com.example.gael_somer_anime.features.anime.data.remote.models.AnimeResponseDto
import com.example.gael_somer_anime.features.anime.domain.entities.Anime

fun AnimeEntity.toDomain(): Anime {
    return Anime(
        id = id,
        titulo = titulo,
        genero = genero,
        anio = anio,
        descripcion = descripcion,
        imageUrl = imageUrl,
        userId = userId,
        tags = tags,
        likes = likes
    )
}

fun Anime.toEntity(): AnimeEntity {
    return AnimeEntity(
        id = id,
        titulo = titulo,
        genero = genero,
        anio = anio,
        descripcion = descripcion,
        imageUrl = imageUrl,
        userId = userId,
        tags = tags,
        likes = likes
    )
}

fun AnimeResponseDto.toEntity(): AnimeEntity {
    return AnimeEntity(
        id = id,
        titulo = titulo,
        genero = genero,
        anio = anio,
        descripcion = descripcion,
        imageUrl = imageUrl,
        userId = userId,
        tags = tags ?: "",
        likes = likes
    )
}
