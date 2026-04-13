package com.example.gael_somer_anime.features.auth.domain.usecases

import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Int = repository.getCurrentUserId()
}
