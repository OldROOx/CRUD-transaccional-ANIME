package com.example.gael_somer_anime.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gael_somer_anime.features.auth.domain.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    fun onUsernameChange(username: String) {
        _username.value = username
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _authError.value = null
            try {
                val loginResponse = loginUseCase(_username.value, _password.value)
                _isLoading.value = false
                if (loginResponse != null) {
                    onSuccess()
                } else {
                    _authError.value = "Usuario o contraseña incorrectos"
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _authError.value = "Error de conexión"
            }
        }
    }
}