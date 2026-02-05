package com.example.gael_somer_anime.core.network

object SessionManager {
    var userToken: String? = null

    fun saveToken(token: String?) {
        userToken = token
    }

    fun fetchToken(): String? = userToken
}