package com.example.gael_somer_anime.core.services

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.gael_somer_anime.core.database.AppDatabase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

@HiltWorker
class ImageCacheWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val database: AppDatabase
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TAG = "ImageCacheWorker"
        const val WORK_NAME = "image_cache_periodic"
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "Iniciando descarga periódica de imágenes...")

        return try {
            val animes = database.animeDao().getAllAnimes().first()
            var downloaded = 0

            for (anime in animes) {
                val imageUrl = anime.imageUrl
                if (imageUrl.isNullOrBlank()) continue

                val localFile = ImageCacheHelper.getLocalPath(applicationContext, anime.id)

                if (localFile.exists() && localFile.length() > 0) continue

                try {
                    downloadImage(imageUrl, localFile)
                    downloaded++
                    Log.d(TAG, "Imagen descargada: anime_${anime.id}")
                } catch (e: Exception) {
                    Log.w(TAG, "Error descargando anime ${anime.id}: ${e.message}")
                }
            }

            Log.d(TAG, "Descarga completada: $downloaded imágenes nuevas")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error general: ${e.message}")
            Result.retry()
        }
    }

    private fun downloadImage(url: String, destination: File) {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connectTimeout = 10_000
        connection.readTimeout = 15_000
        connection.connect()

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            connection.inputStream.use { input ->
                FileOutputStream(destination).use { output ->
                    input.copyTo(output)
                }
            }
        } else {
            throw Exception("HTTP ${connection.responseCode}")
        }
    }
}