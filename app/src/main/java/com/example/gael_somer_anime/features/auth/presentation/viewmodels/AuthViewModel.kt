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
            val token = repository.login(user, pass)
            isLoading = false
            if (token != null) onSuccess() else authError = "Credenciales incorrectas"
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