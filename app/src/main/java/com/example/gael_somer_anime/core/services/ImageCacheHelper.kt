package com.example.gael_somer_anime.core.services

import android.content.Context
import java.io.File

object ImageCacheHelper {

    private fun getCacheDir(context: Context): File {
        val dir = File(context.filesDir, "anime_images")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    fun getLocalPath(context: Context, animeId: Int): File {
        return File(getCacheDir(context), "anime_$animeId.jpg")
    }

    fun getImageModel(context: Context, animeId: Int, remoteUrl: String?): Any {
        val localFile = getLocalPath(context, animeId)
        return if (localFile.exists()) localFile else (remoteUrl ?: "https://via.placeholder.com/150")
    }
}