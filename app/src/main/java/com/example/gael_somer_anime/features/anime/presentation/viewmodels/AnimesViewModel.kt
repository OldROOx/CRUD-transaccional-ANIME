package com.example.gael_somer_anime.features.anime.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gael_somer_anime.core.hardware.ShakeDetector
import com.example.gael_somer_anime.core.hardware.VibrationManager
import com.example.gael_somer_anime.features.anime.domain.entities.Anime
import com.example.gael_somer_anime.features.anime.domain.usecases.CreateAnimeUseCase
import com.example.gael_somer_anime.features.anime.domain.usecases.DeleteAnimeUseCase
import com.example.gael_somer_anime.features.anime.domain.usecases.GetAnimesUseCase
import com.example.gael_somer_anime.features.anime.domain.usecases.UpdateAnimeUseCase
import com.example.gael_somer_anime.features.anime.domain.usecases.UploadAnimeImageUseCase
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AnimesViewModel @Inject constructor(
    private val getAnimesUseCase: GetAnimesUseCase,
    private val createAnimeUseCase: CreateAnimeUseCase,
    private val updateAnimeUseCase: UpdateAnimeUseCase,
    private val deleteAnimeUseCase: DeleteAnimeUseCase,
    private val uploadAnimeImageUseCase: UploadAnimeImageUseCase,
    private val authRepository: AuthRepository,
    private val shakeDetector: ShakeDetector,
    private val vibrationManager: VibrationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnimesUiState())
    val uiState: StateFlow<AnimesUiState> = _uiState.asStateFlow()

    init {
        val currentUserId = authRepository.getCurrentUserId()
        _uiState.update { it.copy(currentUserId = currentUserId) }
        observeAnimes()
    }

    private fun observeAnimes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getAnimesUseCase()
                .catch { e ->
                    _uiState.update { it.copy(error = "Error al cargar los animes: ${e.message}", isLoading = false) }
                }
                .collectLatest { animeList ->
                    _uiState.update { state ->
                        state.copy(
                            animes = animeList,
                            myAnimes = animeList.filter { it.userId == state.currentUserId },
                            otherAnimes = animeList.filter { it.userId != state.currentUserId },
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun toggleMyAnimesExpansion() {
        _uiState.update { it.copy(isMyAnimesExpanded = !it.isMyAnimesExpanded) }
    }

    fun startShakeDetection() {
        shakeDetector.startListening {
            showRandomAnime()
        }
    }

    fun stopShakeDetection() {
        shakeDetector.stopListening()
    }

    private fun showRandomAnime() {
        val currentAnimes = _uiState.value.animes
        if (currentAnimes.isNotEmpty()) {
            val randomIndex = Random.nextInt(currentAnimes.size)
            val randomAnime = currentAnimes[randomIndex]
            vibrationManager.vibrateSuccess()
            _uiState.update { it.copy(randomAnime = randomAnime, showRandomDialog = true) }
        }
    }

    fun onCloseRandomDialog() {
        _uiState.update { it.copy(showRandomDialog = false, randomAnime = null) }
    }

    fun onOpenDialog(anime: Anime? = null) {
        _uiState.update {
            it.copy(
                showDialog = true,
                selectedAnime = anime,
                titulo = anime?.titulo ?: "",
                genero = anime?.genero ?: "",
                anio = anime?.anio?.toString() ?: "",
                descripcion = anime?.descripcion ?: "",
                imageUri = null,
                tituloError = null, generoError = null, anioError = null, descripcionError = null
            )
        }
    }

    fun onCloseDialog() {
        _uiState.update { it.copy(showDialog = false, selectedAnime = null) }
    }

    fun onFieldChange(titulo: String? = null, genero: String? = null, anio: String? = null, descripcion: String? = null) {
        _uiState.update {
            it.copy(
                titulo = titulo ?: it.titulo,
                tituloError = if (titulo != null) null else it.tituloError,
                genero = genero ?: it.genero,
                generoError = if (genero != null) null else it.generoError,
                anio = anio ?: it.anio,
                anioError = if (anio != null) null else it.anioError,
                descripcion = descripcion ?: it.descripcion,
                descripcionError = if (descripcion != null) null else it.descripcionError
            )
        }
    }

    fun onImageSelected(uri: Uri?) {
        _uiState.update { it.copy(imageUri = uri) }
    }

    private fun validateForm(): Boolean {
        val state = _uiState.value
        val tituloError = if (state.titulo.isBlank()) "El título es requerido" else null
        val generoError = if (state.genero.isBlank()) "El género es requerido" else null
        val anioError = when {
            state.anio.isBlank() -> "El año es requerido"
            state.anio.toIntOrNull() == null -> "Debe ser un número"
            else -> null
        }
        val descripcionError = if (state.descripcion.isBlank()) "La descripción es requerida" else null
        _uiState.update {
            it.copy(tituloError = tituloError, generoError = generoError, anioError = anioError, descripcionError = descripcionError)
        }
        return tituloError == null && generoError == null && anioError == null && descripcionError == null
    }

    fun onSaveAnime(imageFile: File? = null) {
        if (!validateForm()) {
            vibrationManager.vibrateError()
            return
        }
        val currentState = _uiState.value
        val anioInt = currentState.anio.toInt()
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val anime = if (currentState.selectedAnime == null) {
                    createAnimeUseCase(currentState.titulo, currentState.genero, anioInt, currentState.descripcion)
                } else {
                    updateAnimeUseCase(currentState.selectedAnime.id, currentState.titulo, currentState.genero, anioInt, currentState.descripcion)
                }
                
                // Si se creó/actualizó correctamente y hay una imagen, subirla
                if (anime != null && imageFile != null) {
                    uploadAnimeImageUseCase(anime.id, imageFile)
                }

                vibrationManager.vibrateSuccess()
                onCloseDialog()
            } catch (e: Exception) {
                vibrationManager.vibrateError()
                _uiState.update { it.copy(error = "Error al guardar el anime: ${e.message}") }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun deleteAnime(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val success = deleteAnimeUseCase(id)
                if (success) {
                    vibrationManager.vibrateSuccess()
                } else {
                    vibrationManager.vibrateError()
                    _uiState.update { it.copy(error = "Error al borrar el anime.") }
                }
            } catch (e: Exception) {
                vibrationManager.vibrateError()
                _uiState.update { it.copy(error = "Error al borrar el anime: ${e.message}") }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

data class AnimesUiState(
    val animes: List<Anime> = emptyList(),
    val myAnimes: List<Anime> = emptyList(),
    val otherAnimes: List<Anime> = emptyList(),
    val isMyAnimesExpanded: Boolean = true,
    val currentUserId: Int = -1,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showDialog: Boolean = false,
    val selectedAnime: Anime? = null,
    val titulo: String = "",
    val genero: String = "",
    val anio: String = "",
    val descripcion: String = "",
    val imageUri: Uri? = null,
    val tituloError: String? = null,
    val generoError: String? = null,
    val anioError: String? = null,
    val descripcionError: String? = null,
    val showRandomDialog: Boolean = false,
    val randomAnime: Anime? = null
)
