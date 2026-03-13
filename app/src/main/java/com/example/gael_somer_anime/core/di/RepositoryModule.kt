package com.example.gael_somer_anime.core.di

import com.example.gael_somer_anime.features.anime.data.repositories.AnimeRepositoryImpl
import com.example.gael_somer_anime.features.anime.domain.repositories.AnimeRepository
import com.example.gael_somer_anime.features.auth.data.repositories.AuthRepositoryImpl
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository
import com.example.gael_somer_anime.features.watchlist.data.repositories.WatchlistRepositoryImpl
import com.example.gael_somer_anime.features.watchlist.domain.repositories.WatchlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAnimeRepository(
        animeRepositoryImpl: AnimeRepositoryImpl
    ): AnimeRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindWatchlistRepository(
        watchlistRepositoryImpl: WatchlistRepositoryImpl
    ): WatchlistRepository
}
