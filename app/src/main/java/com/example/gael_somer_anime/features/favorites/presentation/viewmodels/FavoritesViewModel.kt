package com.example.gael_somer_anime.features.favorites.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.gael_somer_anime.features.favorites.domain.entities.Favorite
import com.example.gael_somer_anime.features.favorites.domain.usecases.AddFavoriteUseCase
import com.example.gael_somer_anime.features.favorites.domain.usecases.GetFavoritesUseCase
import com.example.gael_somer_anime.features.favorites.domain.usecases.IsFavoriteUseCase
import com.example.gael_somer_anime.features.favorites.domain.usecases.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        val favorites = getFavoritesUseCase()
        _uiState.update { it.copy(favorites = favorites) }
    }

    fun toggleFavorite(favorite: Favorite) {
        if (isFavoriteUseCase(favorite.id)) {
            removeFavoriteUseCase(favorite.id)
        } else {
            addFavoriteUseCase(favorite)
        }
        loadFavorites()
    }

    fun isFavorite(id: Int): Boolean = isFavoriteUseCase(id)

    fun removeFavorite(id: Int) {
        removeFavoriteUseCase(id)
        loadFavorites()
    }
}

data class FavoritesUiState(
    val favorites: List<Favorite> = emptyList()
)