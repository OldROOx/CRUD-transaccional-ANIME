package com.example.gael_somer_anime.features.auth.domain.repositories

interface AuthRepository {
    suspend fun login(username: String, password: String): String?
    suspend fun register(username: String, email: String, password: String): Boolean
}