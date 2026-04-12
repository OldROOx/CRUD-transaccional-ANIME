package com.example.gael_somer_anime.features.tags.domain.repositories

interface TagRepository {
    suspend fun subscribeToTag(tag: String, fcmToken: String?): Boolean
    suspend fun unsubscribeFromTag(tag: String): Boolean
    suspend fun getMyTags(): List<String>
}
