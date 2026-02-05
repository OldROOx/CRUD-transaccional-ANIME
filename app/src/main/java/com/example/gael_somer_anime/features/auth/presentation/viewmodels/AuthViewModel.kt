package com.example.gael_somer_anime.features.auth.presentation.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    var isLoading by mutableStateOf(false)
    var authError by mutableStateOf<String?>(null)

    fun login(user: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            authError = null
            try {
                val token = repository.login(user, pass)
                isLoading = false
                if (token != null) {
                    onSuccess() // Ejecuta el callback sin pasarle el String para evitar el error de cast
                } else {
                    authError = "Usuario o contraseña incorrectos"
                }
            } catch (e: Exception) {
                isLoading = false
                authError = "Error de conexión"
            }
        }
    }

    fun register(user: String, email: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            val success = repository.register(user, email, pass)
            isLoading = false
            if (success) onSuccess() else authError = "Error al registrar"
        }
    }
}