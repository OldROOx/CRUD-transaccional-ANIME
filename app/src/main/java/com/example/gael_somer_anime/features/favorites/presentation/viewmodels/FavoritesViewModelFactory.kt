package com.example.gael_somer_anime.features.favorites.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gael_somer_anime.features.favorites.domain.usecases.AddFavoriteUseCase
import com.example.gael_somer_anime.features.favorites.domain.usecases.GetFavoritesUseCase
import com.example.gael_somer_anime.features.favorites.domain.usecases.IsFavoriteUseCase
import com.example.gael_somer_anime.features.favorites.domain.usecases.RemoveFavoriteUseCase

class FavoritesViewModelFactory(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(
                getFavoritesUseCase,
                addFavoriteUseCase,
                removeFavoriteUseCase,
                isFavoriteUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}