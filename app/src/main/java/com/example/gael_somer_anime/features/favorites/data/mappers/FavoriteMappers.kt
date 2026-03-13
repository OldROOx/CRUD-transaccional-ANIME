package com.example.gael_somer_anime.features.favorites.data.mappers

import com.example.gael_somer_anime.features.favorites.data.local.entities.FavoriteEntity
import com.example.gael_somer_anime.features.favorites.domain.entities.Favorite

fun FavoriteEntity.toDomain(): Favorite = Favorite(id, titulo, genero, anio, descripcion)

fun Favorite.toEntity(): FavoriteEntity = FavoriteEntity(id, titulo, genero, anio, descripcion)
