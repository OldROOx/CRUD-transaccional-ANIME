package com.example.gael_somer_anime.features.tags.data.repositories

import com.example.gael_somer_anime.core.network.AnimeApiService
import com.example.gael_somer_anime.features.tags.data.remote.models.TagSubscribeRequest
import com.example.gael_somer_anime.features.tags.domain.repositories.TagRepository
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val api: AnimeApiService
) : TagRepository {

    override suspend fun subscribeToTag(tag: String, fcmToken: String?): Boolean {
        return try {
            val response = api.subscribeToTag(TagSubscribeRequest(tag, fcmToken))
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun unsubscribeFromTag(tag: String): Boolean {
        return try {
            val response = api.unsubscribeFromTag(tag)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getMyTags(): List<String> {
        return try {
            val response = api.getMyTags()
            if (response.isSuccessful) {
                response.body()?.tags ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
