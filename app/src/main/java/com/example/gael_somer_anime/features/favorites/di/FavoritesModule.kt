package com.example.gael_somer_anime.features.favorites.di

import android.content.Context
import com.example.gael_somer_anime.features.favorites.data.local.FavoritesLocalDataSource
import com.example.gael_somer_anime.features.favorites.data.repositories.FavoritesRepositoryImpl
import com.example.gael_somer_anime.features.favorites.domain.repositories.FavoritesRepository
import com.example.gael_somer_anime.features.favorites.domain.usecases.AddFavoriteUseCase
import com.example.gael_somer_anime.features.favorites.domain.usecases.GetFavoritesUseCase
import com.example.gael_somer_anime.features.favorites.domain.usecases.IsFavoriteUseCase
import com.example.gael_somer_anime.features.favorites.domain.usecases.RemoveFavoriteUseCase
import com.example.gael_somer_anime.features.favorites.presentation.viewmodels.FavoritesViewModelFactory

class FavoritesModule(context: Context) {

    private val localDataSource = FavoritesLocalDataSource(context)

    private val favoritesRepository: FavoritesRepository =
        FavoritesRepositoryImpl(localDataSource)

    fun provideGetFavoritesUseCase() = GetFavoritesUseCase(favoritesRepository)
    fun provideAddFavoriteUseCase() = AddFavoriteUseCase(favoritesRepository)
    fun provideRemoveFavoriteUseCase() = RemoveFavoriteUseCase(favoritesRepository)
    fun provideIsFavoriteUseCase() = IsFavoriteUseCase(favoritesRepository)

    fun provideFavoritesViewModelFactory(): FavoritesViewModelFactory {
        return FavoritesViewModelFactory(
            getFavoritesUseCase = provideGetFavoritesUseCase(),
            addFavoriteUseCase = provideAddFavoriteUseCase(),
            removeFavoriteUseCase = provideRemoveFavoriteUseCase(),
            isFavoriteUseCase = provideIsFavoriteUseCase()
        )
    }
}