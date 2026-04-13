package com.example.gael_somer_anime.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gael_somer_anime.core.hardware.VibrationManager
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository
import com.example.gael_somer_anime.features.auth.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val authRepository: AuthRepository,
    private val vibrationManager: VibrationManager
) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    fun onUsernameChange(username: String) { _username.value = username }
    fun onPasswordChange(password: String) { _password.value = password }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            try {
                val loginResponse = loginUseCase(_username.value, _password.value)
                if (loginResponse != null) {
                    authRepository.saveCredentials(_username.value, _password.value)
                    vibrationManager.vibrateSuccess()
                    _isLoading.value = false
                    onSuccess()
                } else {
                    vibrationManager.vibrateError()
                    _isLoading.value = false
                    _authError.value = "Usuario o contraseña incorrectos"
                }
            } catch (_: Exception) {
                vibrationManager.vibrateError()
                _isLoading.value = false
                _authError.value = "Error de conexión"
            }
        }
    }

    fun onBiometricError(error: String) {
        vibrationManager.vibrateError()
        _authError.value = error
    }

    fun onBiometricSuccess(onSuccess: () -> Unit) {
        performAutoLogin(onSuccess)
    }

    private fun performAutoLogin(onSuccess: () -> Unit) {
        val savedUser = authRepository.getSavedUser()
        val savedPass = authRepository.getSavedPass()

        if (savedUser != null && savedPass != null) {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val response = loginUseCase(savedUser, savedPass)
                    if (response != null) {
                        vibrationManager.vibrateSuccess()
                        _isLoading.value = false
                        onSuccess()
                    } else {
                        vibrationManager.vibrateError()
                        _isLoading.value = false
                        _authError.value = "Sesión expirada. Ingrese contraseña."
                    }
                } catch (_: Exception) {
                    vibrationManager.vibrateError()
                    _isLoading.value = false
                    _authError.value = "Error de conexión"
                }
            }
        } else {
            vibrationManager.vibrateError()
            _authError.value = "Debe iniciar sesión manualmente la primera vez"
        }
    }
}
