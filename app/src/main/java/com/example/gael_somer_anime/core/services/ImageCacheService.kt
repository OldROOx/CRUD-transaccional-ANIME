package com.example.gael_somer_anime.core.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.gael_somer_anime.core.database.AppDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@AndroidEntryPoint
class ImageCacheService : Service() {

    @Inject lateinit var database: AppDatabase

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    companion object {
        private const val CHANNEL_ID = "image_cache_channel"
        private const val NOTIFICATION_ID = 2001
        private const val TAG = "ImageCacheService"

        fun start(context: Context) {
            val intent = Intent(context, ImageCacheService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, buildNotification("Preparando descarga de imágenes..."))

        scope.launch {
            try {
                downloadAllImages()
            } catch (e: Exception) {
                Log.e(TAG, "Error en descarga: ${e.message}")
            } finally {
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }

        return START_NOT_STICKY
    }

    private suspend fun downloadAllImages() {
        val animes = database.animeDao().getAllAnimes().first()
        val total = animes.size
        var downloaded = 0

        for (anime in animes) {
            val imageUrl = anime.imageUrl
            if (imageUrl.isNullOrBlank()) {
                downloaded++
                continue
            }

            val localFile = ImageCacheHelper.getLocalPath(this, anime.id)

            // Si ya existe, saltar
            if (localFile.exists() && localFile.length() > 0) {
                downloaded++
                continue
            }

            try {
                downloadImage(imageUrl, localFile)
                downloaded++
                updateNotification("Descargando imágenes: $downloaded/$total")
                Log.d(TAG, "Imagen descargada: anime_${anime.id}")
            } catch (e: Exception) {
                downloaded++
                Log.w(TAG, "No se pudo descargar imagen de anime ${anime.id}: ${e.message}")
            }
        }

        updateNotification("Descarga completa: $downloaded imágenes")
        delay(1500) // Dar tiempo a que el usuario vea la notificación final
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Caché de Imágenes",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Descarga de imágenes para modo offline"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(text: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Anime App")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.ic_menu_save)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun updateNotification(text: String) {
        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(NOTIFICATION_ID, buildNotification(text))
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}