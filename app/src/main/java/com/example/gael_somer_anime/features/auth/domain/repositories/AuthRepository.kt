package com.example.gael_somer_anime.features.auth.domain.repositories

import com.example.gael_somer_anime.features.auth.domain.entities.LoginResponse

interface AuthRepository {
    suspend fun login(username: String, password: String): LoginResponse?
    suspend fun register(username: String, email: String, password: String): Boolean
    fun logout()
    fun isLoggedIn(): Boolean
    fun saveCredentials(user: String, pass: String)
    fun getSavedUser(): String?
    fun getSavedPass(): String?
    suspend fun getCurrentUserId(): Int
    fun getFcmToken(): String?
}
