package com.example.gael_somer_anime.features.auth.data.repositories

import android.content.Context
import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.core.network.SessionManager
import com.example.gael_somer_anime.features.auth.data.remote.mappers.toDomain
import com.example.gael_somer_anime.features.auth.data.remote.models.LoginRequestDto
import com.example.gael_somer_anime.features.auth.data.remote.models.RegisterRequestDto
import com.example.gael_somer_anime.features.auth.domain.entities.LoginResponse
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val api: AnimeApiService,
    private val context: Context
) : AuthRepository {

    override suspend fun login(username: String, password: String): LoginResponse? {
        val response = api.login(LoginRequestDto(username, password))
        return if (response.isSuccessful) {
            val loginResponseDto = response.body()
            loginResponseDto?.let {
                SessionManager.saveToken(context, it.accessToken)
                it.toDomain()
            }
        } else {
            null
        }
    }

    override suspend fun register(username: String, email: String, password: String): Boolean {
        val response = api.register(RegisterRequestDto(username, email, password))
        return response.isSuccessful
    }

    override fun logout() {
        SessionManager.saveToken(context, null)
    }
}