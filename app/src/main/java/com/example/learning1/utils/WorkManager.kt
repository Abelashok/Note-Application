package com.example.learning1.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextParams
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.learning1.R
import kotlinx.coroutines.delay
import kotlin.random.Random

class WorkManager(
    private val context: Context,
    private val workerParams: WorkerParameters,


): CoroutineWorker(context,workerParams) {
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"
    override suspend fun doWork(): Result {
        Log.e("MILLI"," " +NavEvent.DelayMilli/60)
       delay(NavEvent.DelayMilli)
        createNotificationChannel()
        return Result.success()
    }


companion object {
 const val CHANNEL_ID = "channel_id"
 const val notificationId = 1
}

    private fun createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name ="Note Title"
            val descriptionText = "Note description"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL_ID,name, importance).apply {
                description =descriptionText
            }
            val notificationManager:NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
               as NotificationManager
            notificationManager.createNotificationChannel(channel)

            sendNotification()
        }

    }
    private fun sendNotification() {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentText("Note Description")
            .setContentTitle("Note Title")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            with(NotificationManagerCompat.from(context)) {
            notify(notificationId,builder.build())
        }
    }
}