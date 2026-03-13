package com.example.gael_somer_anime.features.watchlist.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gael_somer_anime.features.anime.domain.entities.Anime
import com.example.gael_somer_anime.features.anime.domain.usecases.GetAnimesUseCase
import com.example.gael_somer_anime.features.watchlist.domain.entities.Watchlist
import com.example.gael_somer_anime.features.watchlist.domain.entities.WatchlistStatus
import com.example.gael_somer_anime.features.watchlist.domain.usecases.AddToWatchlistUseCase
import com.example.gael_somer_anime.features.watchlist.domain.usecases.GetWatchlistUseCase
import com.example.gael_somer_anime.features.watchlist.domain.usecases.RemoveFromWatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getWatchlistUseCase: GetWatchlistUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val removeFromWatchlistUseCase: RemoveFromWatchlistUseCase,
    private val getAnimesUseCase: GetAnimesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(WatchlistState())
    val state: State<WatchlistState> = _state

    init {
        loadWatchlist()
    }

    fun loadWatchlist() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            try {
                // Cargar animes para tener los títulos
                val animes = getAnimesUseCase().first()
                
                getWatchlistUseCase().onSuccess { watchlist ->
                    _state.value = _state.value.copy(
                        items = watchlist,
                        animes = animes,
                        isLoading = false
                    )
                }.onFailure { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error loading data"
                )
            }
        }
    }

    fun addToWatchlist(animeId: Int, status: WatchlistStatus) {
        viewModelScope.launch {
            addToWatchlistUseCase(animeId, status).onSuccess {
                loadWatchlist()
            }.onFailure { e ->
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun removeFromWatchlist(animeId: Int) {
        viewModelScope.launch {
            removeFromWatchlistUseCase(animeId).onSuccess {
                loadWatchlist()
            }.onFailure { e ->
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}

data class WatchlistState(
    val items: List<Watchlist> = emptyList(),
    val animes: List<Anime> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
