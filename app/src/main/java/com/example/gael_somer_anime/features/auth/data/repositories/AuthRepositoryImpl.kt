package com.example.gael_somer_anime.features.auth.data.repositories

import android.content.Context
import androidx.room.util.appendPlaceholders
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
    @ApplicationContext  context: Context
) : AuthRepository {

    val applicationContext = context;
    override suspend fun login(username: String, password: String): LoginResponse? {
        val response = api.login(LoginRequestDto(username, password))
        return if (response.isSuccessful) {
            response.body()?.let {
                SessionManager.saveToken(applicationContext, it.accessToken)
                SessionManager.saveUserId(applicationContext, it.user.id)
                it.toDomain()
            }
        } else null
    }

    override suspend fun register(username: String, email: String, password: String): Boolean {
        val response = api.register(RegisterRequestDto(username, email, password))
        return if (response.isSuccessful) {
            response.body()?.let {
                SessionManager.saveToken(applicationContext, it.accessToken)
                SessionManager.saveUserId(applicationContext, it.user.id)
            }
            true
        } else false
    }

    override fun logout() {
        SessionManager.clearToken(applicationContext)
    }

    override fun isLoggedIn(): Boolean {
        return SessionManager.fetchToken(applicationContext) != null
    }

    override fun saveCredentials(user: String, pass: String) {
        SessionManager.saveCredentials(applicationContext, user, pass)
    }

    override fun getSavedUser(): String? = SessionManager.getSavedUser(applicationContext)
    override fun getSavedPass(): String? = SessionManager.getSavedPass(applicationContext)
    override fun getCurrentUserId(): Int = SessionManager.fetchUserId(applicationContext)
}
