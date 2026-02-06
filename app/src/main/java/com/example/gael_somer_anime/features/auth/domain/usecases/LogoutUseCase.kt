package com.example.gael_somer_anime.features.auth.domain.usecases

import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    operator fun invoke() = repository.logout()
}