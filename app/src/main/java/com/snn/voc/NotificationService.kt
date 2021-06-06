package com.snn.voc

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.*


class NotificationService : Service() {
    /* I will set first notification time */
    private val notificationChannelID = "10001"
    private val defaultNotificationChannelID = "default"
    private val handler: Handler = Handler()

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private var milliSeconds: Long = 43200000


    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val restartServiceIntent = Intent(applicationContext, this.javaClass)
        restartServiceIntent.setPackage(packageName)
        startService(restartServiceIntent)
        super.onTaskRemoved(rootIntent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                handler.post { createNotification() }
            }
        }
        timer!!.schedule(timerTask, 60000, milliSeconds)
        return START_STICKY
    }

    override fun onCreate() {}

    override fun onDestroy() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
        Log.e("TAG", "onDestroy: ")
        super.onDestroy()
    }

    private fun createNotification() {
        val intent = Intent(this, NotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder =
            NotificationCompat
                .Builder(this, defaultNotificationChannelID)
                .setContentTitle("Voc")
                .setContentText("Hey, is there a word you need to remember?")
                .setSmallIcon(R.drawable.ic_reading)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                notificationChannelID,
                "NOTIFICATION_CHANNEL_NAME",
                importance
            )
            builder.setChannelId(notificationChannelID)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }
}