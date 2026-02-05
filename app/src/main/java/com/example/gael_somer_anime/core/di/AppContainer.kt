package com.example.gael_somer_anime.core.di

import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.core.network.AuthInterceptor
import com.example.gael_somer_anime.features.auth.data.repositories.AuthRepositoryImpl
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://100.50.53.163:8000/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val animeApiService: AnimeApiService by lazy {
        retrofit.create(AnimeApiService::class.java)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(animeApiService)
    }
}