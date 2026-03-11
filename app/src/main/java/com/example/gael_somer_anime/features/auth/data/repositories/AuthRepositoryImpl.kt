package com.example.gael_somer_anime.features.auth.data.repositories

import android.content.Context
import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.core.network.SessionManager
import com.example.gael_somer_anime.features.auth.data.remote.mappers.toDomain
import com.example.gael_somer_anime.features.auth.data.remote.models.LoginRequestDto
import com.example.gael_somer_anime.features.auth.data.remote.models.RegisterRequestDto
import com.example.gael_somer_anime.features.auth.domain.entities.LoginResponse
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AnimeApiService,
    @ApplicationContext private val context: Context
) : AuthRepository {

    override suspend fun login(username: String, password: String): LoginResponse? {
        val response = api.login(LoginRequestDto(username, password))
        return if (response.isSuccessful) {
            response.body()?.let {
                SessionManager.saveToken(context, it.accessToken)
                it.toDomain()
            }
        } else null
    }

    override suspend fun register(username: String, email: String, password: String): Boolean {
        val response = api.register(RegisterRequestDto(username, email, password))
        return response.isSuccessful
    }

    override fun logout() {
        // Solo borramos el token para permitir que la biometría use las credenciales guardadas
        SessionManager.clearToken(context)
    }

    override fun isLoggedIn(): Boolean {
        return SessionManager.fetchToken(context) != null
    }

    override fun saveCredentials(user: String, pass: String) {
        SessionManager.saveCredentials(context, user, pass)
    }

    override fun getSavedUser(): String? = SessionManager.getSavedUser(context)
    override fun getSavedPass(): String? = SessionManager.getSavedPass(context)
}
