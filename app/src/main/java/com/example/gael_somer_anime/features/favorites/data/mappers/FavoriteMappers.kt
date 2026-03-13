package com.example.gael_somer_anime.features.favorites.data.mappers

import com.example.gael_somer_anime.features.favorites.data.local.FavoriteDto
import com.example.gael_somer_anime.features.favorites.domain.entities.Favorite

fun FavoriteDto.toDomain(): Favorite = Favorite(id, titulo, genero, anio, descripcion)

fun Favorite.toDto(): FavoriteDto = FavoriteDto(id, titulo, genero, anio, descripcion)