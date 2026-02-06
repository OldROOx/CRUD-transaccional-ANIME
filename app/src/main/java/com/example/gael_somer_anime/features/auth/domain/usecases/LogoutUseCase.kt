package com.example.gael_somer_anime.features.auth.domain.usecases

import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    // Al usar 'operator fun invoke', puedes llamar a esta clase como una función: logoutUseCase()
    operator fun invoke() {
        repository.logout()
    }
}