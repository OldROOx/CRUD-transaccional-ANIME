package com.example.gael_somer_anime.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gael_somer_anime.features.anime.data.local.dao.AnimeDao
import com.example.gael_somer_anime.features.anime.data.local.entities.AnimeEntity
import com.example.gael_somer_anime.features.favorites.data.local.dao.FavoriteDao
import com.example.gael_somer_anime.features.favorites.data.local.entities.FavoriteEntity
import com.example.gael_somer_anime.features.watchlist.data.local.dao.WatchlistDao
import com.example.gael_somer_anime.features.watchlist.data.local.entities.WatchlistEntity

@Database(entities = [AnimeEntity::class, WatchlistEntity::class, FavoriteEntity::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun watchlistDao(): WatchlistDao
    abstract fun favoriteDao(): FavoriteDao
}
