package com.example.gael_somer_anime.features.auth.data.repositories

import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.core.network.SessionManager
import com.example.gael_somer_anime.features.auth.data.remote.models.*
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository

class AuthRepositoryImpl(private val api: AnimeApiService) : AuthRepository {

    override suspend fun login(username: String, password: String): String? {
        val response = api.login(LoginRequestDto(username, password))
        if (response.isSuccessful) {
            val token = response.headers()["Authorization"]
            SessionManager.saveToken(token)
            return token
        }
        return null
    }

    override suspend fun register(username: String, email: String, password: String): Boolean {
        return api.register(RegisterRequestDto(username, email, password)).isSuccessful
    }
}