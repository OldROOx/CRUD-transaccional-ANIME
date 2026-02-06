package com.example.gael_somer_anime.features.auth.domain.usecases

import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(user: String, pass: String): String? = repository.login(user, pass)
}