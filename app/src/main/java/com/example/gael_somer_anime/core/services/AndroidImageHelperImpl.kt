package com.example.gael_somer_anime.core.services

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidImageHelperImpl @Inject constructor(
    @ApplicationContext applicationContext: Context
) : ImageHelper {

    private val context = applicationContext
    private fun getCacheDir(): File {
        val dir = File(context.filesDir, "anime_images")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    override fun getLocalPath(animeId: Int): File {
        return File(getCacheDir(), "anime_$animeId.jpg")
    }

    override fun getImageModel(animeId: Int, remoteUrl: String?): Any {
        val localFile = getLocalPath(animeId)
        return if (localFile.exists()) localFile else (remoteUrl ?: "https://via.placeholder.com/150")
    }
}
