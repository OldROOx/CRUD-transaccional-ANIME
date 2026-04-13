package com.example.gael_somer_anime.features.auth.data.repositories

import android.content.SharedPreferences
import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.core.network.SessionManager
import com.example.gael_somer_anime.features.auth.data.remote.mappers.toDomain
import com.example.gael_somer_anime.features.auth.data.remote.models.LoginRequestDto
import com.example.gael_somer_anime.features.auth.data.remote.models.RegisterRequestDto
import com.example.gael_somer_anime.features.auth.domain.entities.LoginResponse
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AnimeApiService,
    private val prefs: SharedPreferences
) : AuthRepository {

    override suspend fun login(username: String, password: String): LoginResponse? {
        val response = api.login(LoginRequestDto(username, password))
        return if (response.isSuccessful) {
            response.body()?.let {
                SessionManager.saveToken(prefs, it.accessToken)
                SessionManager.saveUserId(prefs, it.user.id)
                it.toDomain()
            }
        } else null
    }

    override suspend fun register(username: String, email: String, password: String): Boolean {
        val response = api.register(RegisterRequestDto(username, email, password))
        return if (response.isSuccessful) {
            response.body()?.let {
                SessionManager.saveToken(prefs, it.accessToken)
                SessionManager.saveUserId(prefs, it.user.id)
            }
            true
        } else false
    }

    override fun logout() {
        SessionManager.clearToken(prefs)
    }

    override fun isLoggedIn(): Boolean {
        return SessionManager.fetchToken(prefs) != null
    }

    override fun saveCredentials(user: String, pass: String) {
        SessionManager.saveCredentials(prefs, user, pass)
    }

    override fun getSavedUser(): String? = SessionManager.getSavedUser(prefs)
    override fun getSavedPass(): String? = SessionManager.getSavedPass(prefs)
    override suspend fun getCurrentUserId(): Int = SessionManager.fetchUserId(prefs)
    override fun getFcmToken(): String? = SessionManager.fetchFcmToken(prefs)
}
