package com.example.gael_somer_anime.core.di

import android.content.Context
import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.core.network.AuthInterceptor
import com.example.gael_somer_anime.features.auth.data.repositories.AuthRepositoryImpl
import com.example.gael_somer_anime.features.auth.domain.repositories.AuthRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(private val context: Context) { // <--- Agregamos el contexto aquí

    // El cliente OkHttp ahora recibe el contexto para que el Interceptor pueda leer el token persistente
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(context)) // <--- Pasamos el contexto al Interceptor
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://100.50.53.163:8000/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val animeApiService: AnimeApiService by lazy {
        retrofit.create(AnimeApiService::class.java)
    }

    // El repositorio ahora recibe el contexto para poder GUARDAR el token en el disco
    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(animeApiService, context)
    }
}