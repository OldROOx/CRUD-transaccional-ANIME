package com.example.gael_somer_anime.features.auth.domain.repositories

interface AuthRepository {
    suspend fun login(username: String, password: String): String?
    suspend fun register(username: String, email: String, password: String): Boolean
    // Esta es la línea que permite que el Caso de Uso reconozca la función
    fun logout()
}