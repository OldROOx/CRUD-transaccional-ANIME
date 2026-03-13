package com.example.gael_somer_anime.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gael_somer_anime.features.anime.data.local.dao.AnimeDao
import com.example.gael_somer_anime.features.anime.data.local.entities.AnimeEntity
import com.example.gael_somer_anime.features.anime.data.local.entities.FavoriteEntity

@Database(entities = [AnimeEntity::class, FavoriteEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}