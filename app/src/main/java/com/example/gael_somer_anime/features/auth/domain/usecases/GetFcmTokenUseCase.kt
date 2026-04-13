package com.example.gael_somer_anime.features.auth.domain.usecases

import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class GetFcmTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): String? = repository.getFcmToken()
}
