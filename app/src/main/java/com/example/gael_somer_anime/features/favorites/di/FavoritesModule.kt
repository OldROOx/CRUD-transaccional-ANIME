package com.example.gael_somer_anime.features.favorites.di

import com.example.gael_somer_anime.features.favorites.data.repositories.FavoritesRepositoryImpl
import com.example.gael_somer_anime.features.favorites.domain.repositories.FavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoritesModule {

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(
        favoritesRepositoryImpl: FavoritesRepositoryImpl
    ): FavoritesRepository
}