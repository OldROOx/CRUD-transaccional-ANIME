package com.example.gael_somer_anime.features.auth.data.repositories

import android.content.Context
import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.core.network.SessionManager
import com.example.gael_somer_anime.features.auth.data.remote.models.LoginRequestDto
import com.example.gael_somer_anime.features.auth.data.remote.models.RegisterRequestDto
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val api: AnimeApiService,
    private val context: Context
) : AuthRepository {

    override suspend fun login(username: String, password: String): String? {
        val response = api.login(LoginRequestDto(username, password))
        if (response.isSuccessful) {
            val token = response.headers()["Authorization"]
            SessionManager.saveToken(context, token)
            return token
        }
        return null
    }

    override suspend fun register(username: String, email: String, password: String): Boolean {
        val response = api.register(RegisterRequestDto(username, email, password))
        return response.isSuccessful
    }

    // Implementación real que borra el token del almacenamiento local
    override fun logout() {
        SessionManager.saveToken(context, null)
    }
}