package com.example.gael_somer_anime.core.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import android.util.Log
import com.example.gael_somer_anime.MainActivity
import com.example.gael_somer_anime.core.hardware.VibrationManager
import com.example.gael_somer_anime.core.network.SessionManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var vibrationManager: VibrationManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        SessionManager.saveFcmToken(applicationContext, token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("FCM_RECIBIDO", "¡Mensaje recibido!")
        Log.d("FCM_RECIBIDO", "De: ${message.from}")
        Log.d("FCM_RECIBIDO", "Data: ${message.data}")
        Log.d("FCM_RECIBIDO", "Notification: ${message.notification?.body}")

        val title = message.data["title"] ?: message.notification?.title ?: "Notificación de Anime"
        val bodyText = message.data["body"] ?: message.notification?.body ?: "Tienes contenido nuevo por revisar"
        val tags = message.data["tags"]
        val animeId = message.data["anime_id"]

        val body = if (tags != null) "$bodyText\nTags: $tags" else bodyText

        vibrationManager.vibrateSuccess()
        sendNotification(title, body, animeId)
    }

    private fun sendNotification(title: String, body: String, animeId: String?) {
        Log.d("FCM_RECIBIDO", "Preparando notificación para mostrar: $title")
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            animeId?.let { putExtra("anime_id", it) }
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // Botón "Ver Anime"
        val actionVer = NotificationCompat.Action.Builder(
            0, "Ver Anime", pendingIntent
        ).build()

        val channelId = "anime_notifications"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificaciones de Anime",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para alertas de nuevos animes"
                enableLights(true)
                enableVibration(true)
            }
                notificationManager.createNotificationChannel(channel)
            }

            val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body)) // Permite saltos de línea para mostrar todos los tags
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .addAction(actionVer) // Agregar botón Ver Anime

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }
}
