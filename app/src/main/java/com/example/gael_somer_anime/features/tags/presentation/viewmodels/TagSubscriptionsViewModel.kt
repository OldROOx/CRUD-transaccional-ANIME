package com.example.gael_somer_anime.features.tags.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gael_somer_anime.core.hardware.VibrationManager
import com.example.gael_somer_anime.core.network.SessionManager
import com.example.gael_somer_anime.features.tags.domain.usecases.GetMyTagsUseCase
import com.example.gael_somer_anime.features.tags.domain.usecases.SubscribeToTagUseCase
import com.example.gael_somer_anime.features.tags.domain.usecases.UnsubscribeFromTagUseCase
import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagSubscriptionsViewModel @Inject constructor(
    private val getMyTagsUseCase: GetMyTagsUseCase,
    private val subscribeToTagUseCase: SubscribeToTagUseCase,
    private val unsubscribeFromTagUseCase: UnsubscribeFromTagUseCase,
    private val vibrationManager: VibrationManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(TagSubscriptionsUiState())
    val uiState: StateFlow<TagSubscriptionsUiState> = _uiState.asStateFlow()

    init {
        loadTags()
    }

    fun loadTags() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val tags = getMyTagsUseCase()
                _uiState.update { it.copy(tags = tags, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun unsubscribe(tag: String) {
        viewModelScope.launch {
            if (unsubscribeFromTagUseCase(tag)) {
                vibrationManager.vibrateSuccess()
                loadTags()
            } else {
                vibrationManager.vibrateError()
            }
        }
    }

    fun subscribe(tag: String) {
        if (tag.isBlank()) return
        
        viewModelScope.launch {
            val fcmToken = SessionManager.fetchFcmToken(context)
            if (subscribeToTagUseCase(tag.trim().lowercase(), fcmToken)) {
                vibrationManager.vibrateSuccess()
                loadTags()
            } else {
                vibrationManager.vibrateError()
            }
        }
    }
}

data class TagSubscriptionsUiState(
    val tags: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
