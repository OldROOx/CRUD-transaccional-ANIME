package com.example.gael_somer_anime.core.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.gael_somer_anime.MainActivity
import com.example.gael_somer_anime.core.network.SessionManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Guardar el nuevo token
        SessionManager.saveFcmToken(applicationContext, token)
        Log.d("FCM_HILT", "Nuevo token generado: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        
        Log.d("FCM_HILT", "Mensaje recibido - Data: ${message.data}")
        Log.d("FCM_HILT", "Mensaje recibido - Notification: ${message.notification?.body}")

        // Priorizamos los datos (Data Messages) para tener control total del diseño
        val title = message.data["titulo"] ?: message.notification?.title ?: "Nuevo Anime"
        val body = message.data["cuerpo"] ?: message.notification?.body ?: "¡Se ha publicado algo que te interesa!"
        
        sendNotification(title, body)
    }

    private fun sendNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this, System.currentTimeMillis().toInt(), intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "anime_notifications"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificaciones de Anime",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alertas de nuevos animes según tus tags"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }
}
