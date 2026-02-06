package com.example.gael_somer_anime.core.network

import com.example.gael_somer_anime.features.anime.data.remote.models.AnimeRequestDto
import com.example.gael_somer_anime.features.anime.data.remote.models.AnimeResponseDto
import com.example.gael_somer_anime.features.auth.data.remote.models.LoginRequestDto
import com.example.gael_somer_anime.features.auth.data.remote.models.LoginResponseDto
import com.example.gael_somer_anime.features.auth.data.remote.models.RegisterRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AnimeApiService {
    // Auth
    @POST("api/users/login")
    suspend fun login(@Body request: LoginRequestDto): Response<LoginResponseDto>

    @POST("api/users/register")
    suspend fun register(@Body request: RegisterRequestDto): Response<Unit>

    // Animes
    @GET("api/animes")
    suspend fun getAnimes(): Response<List<AnimeResponseDto>>

    @POST("api/animes")
    suspend fun createAnime(@Body anime: AnimeRequestDto): Response<AnimeResponseDto>

    @PUT("api/animes/{id}")
    suspend fun updateAnime(@Path("id") id: Int, @Body anime: AnimeRequestDto): Response<AnimeResponseDto>

    @DELETE("api/animes/{id}")
    suspend fun deleteAnime(@Path("id") id: Int): Response<Unit> // Cambiado
}