package com.example.gael_somer_anime.features.favorites.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gael_somer_anime.features.favorites.domain.entities.Favorite
import com.example.gael_somer_anime.features.favorites.domain.usecases.AddFavoriteUseCase
import com.example.gael_somer_anime.features.favorites.domain.usecases.GetFavoritesUseCase
import com.example.gael_somer_anime.features.favorites.domain.usecases.IsFavoriteUseCase
import com.example.gael_somer_anime.features.favorites.domain.usecases.RemoveFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        observeFavorites()
    }

    private fun observeFavorites() {
        getFavoritesUseCase()
            .onEach { favorites: List<Favorite> ->
                _uiState.update { it.copy(favorites = favorites) }
            }
            .launchIn(viewModelScope)
    }

    fun toggleFavorite(favorite: Favorite) {
        viewModelScope.launch {
            if (isFavoriteUseCase(favorite.id)) {
                removeFavoriteUseCase(favorite.id)
            } else {
                addFavoriteUseCase(favorite)
            }
        }
    }

    fun isFavorite(id: Int): Boolean = _uiState.value.favorites.any { it.id == id }

    fun removeFavorite(id: Int) {
        viewModelScope.launch {
            removeFavoriteUseCase(id)
        }
    }
}

data class FavoritesUiState(
    val favorites: List<Favorite> = emptyList()
)
