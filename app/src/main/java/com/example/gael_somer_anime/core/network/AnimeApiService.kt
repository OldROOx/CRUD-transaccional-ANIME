package com.example.gael_somer_anime.core.network

import com.example.gael_somer_anime.features.anime.data.remote.models.AnimeRequestDto
import com.example.gael_somer_anime.features.anime.data.remote.models.AnimeResponseDto
import com.example.gael_somer_anime.features.auth.data.remote.models.LoginRequestDto
import com.example.gael_somer_anime.features.auth.data.remote.models.LoginResponseDto
import com.example.gael_somer_anime.features.auth.data.remote.models.RegisterRequestDto
import com.example.gael_somer_anime.features.watchlist.data.remote.models.WatchlistRequestDto
import com.example.gael_somer_anime.features.watchlist.data.remote.models.WatchlistResponseDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface AnimeApiService {
    @POST("api/users/login")
    suspend fun login(@Body request: LoginRequestDto): Response<LoginResponseDto>

    @POST("api/users/register")
    suspend fun register(@Body request: RegisterRequestDto): Response<LoginResponseDto>

    @GET("api/animes")
    suspend fun getAnimes(): Response<List<AnimeResponseDto>>

    @POST("api/animes")
    suspend fun createAnime(@Body anime: AnimeRequestDto): Response<AnimeResponseDto>

    @PUT("api/animes/{id}")
    suspend fun updateAnime(@Path("id") id: Int, @Body anime: AnimeRequestDto): Response<AnimeResponseDto>

    @DELETE("api/animes/{id}")
    suspend fun deleteAnime(@Path("id") id: Int): Response<Unit>

    @Multipart
    @POST("api/animes/{id}/upload")
    suspend fun uploadAnimeImage(
        @Path("id") id: Int,
        @Part file: MultipartBody.Part
    ): Response<AnimeResponseDto>

    // Watchlist
    @POST("api/watchlist/")
    suspend fun addToWatchlist(@Body request: WatchlistRequestDto): Response<WatchlistResponseDto>

    @GET("api/watchlist/me")
    suspend fun getMyWatchlist(): Response<List<WatchlistResponseDto>>

    @DELETE("api/watchlist/{anime_id}")
    suspend fun removeFromWatchlist(@Path("anime_id") animeId: Int): Response<Unit>
}
