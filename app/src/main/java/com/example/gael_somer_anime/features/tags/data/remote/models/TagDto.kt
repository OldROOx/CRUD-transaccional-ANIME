package com.example.gael_somer_anime.features.tags.data.remote.models

import com.google.gson.annotations.SerializedName

data class TagSubscribeRequest(
    val tag: String,
    @SerializedName("fcm_token") val fcmToken: String?
)

data class TagSubscriptionResponse(
    val id: Int,
    @SerializedName("user_id") val userId: Int,
    val tag: String,
    @SerializedName("fcm_token") val fcmToken: String?,
    @SerializedName("created_at") val createdAt: String
)

data class MyTagsResponse(
    val tags: List<String>
)
