package com.example.gael_somer_anime.features.tags.domain.usecases

import com.example.gael_somer_anime.features.tags.domain.repositories.TagRepository
import javax.inject.Inject

class SubscribeToTagUseCase @Inject constructor(
    private val repository: TagRepository
) {
    suspend operator fun invoke(tag: String, fcmToken: String?) = 
        repository.subscribeToTag(tag, fcmToken)
}

class UnsubscribeFromTagUseCase @Inject constructor(
    private val repository: TagRepository
) {
    suspend operator fun invoke(tag: String) = 
        repository.unsubscribeFromTag(tag)
}

class GetMyTagsUseCase @Inject constructor(
    private val repository: TagRepository
) {
    suspend operator fun invoke() = 
        repository.getMyTags()
}
