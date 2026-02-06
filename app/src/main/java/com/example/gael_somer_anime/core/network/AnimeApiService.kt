package com.example.gael_somer_anime.core.network

import com.example.gael_somer_anime.features.auth.data.remote.models.*
import com.example.gael_somer_anime.features.auth.domain.entities.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET

interface AnimeApiService {
    @POST("api/users/login")
    suspend fun login(@Body request: LoginRequestDto): Response<LoginResponse>

    @POST("api/users/register")
    suspend fun register(@Body request: RegisterRequestDto): Response<Unit>

    @GET("api/animes")
    suspend fun getAnimes(): Response<List<Any>>
}