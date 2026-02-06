package com.example.gael_somer_anime.core.di

import android.content.Context
import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.features.auth.data.repositories.AuthRepositoryImpl
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(private val context: Context) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://100.50.53.163:8000/") // Asegúrate de que esta es tu IP correcta
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: AnimeApiService by lazy { retrofit.create(AnimeApiService::class.java) }
    
    val authRepository: AuthRepository by lazy { AuthRepositoryImpl(api, context) }
}