package com.example.gael_somer_anime.core.di

import android.content.Context
import androidx.room.Room
import com.example.gael_somer_anime.core.database.AppDatabase
import com.example.gael_somer_anime.features.anime.data.local.dao.AnimeDao
import com.example.gael_somer_anime.features.watchlist.data.local.dao.WatchlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "anime_database"
        )
        .fallbackToDestructiveMigration() // Esto borrará las tablas si cambias la versión
        .build()
    }

    @Provides
    fun provideAnimeDao(database: AppDatabase): AnimeDao {
        return database.animeDao()
    }

    @Provides
    fun provideWatchlistDao(database: AppDatabase): WatchlistDao {
        return database.watchlistDao()
    }
}
