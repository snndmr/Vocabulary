package com.snn.voc

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.util.*


class NotificationService : Service() {

    private val notificationChannelID = "10001"
    private val defaultNotificationChannelID = "default"
    private val handler: Handler = Handler()

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private var milliSeconds: Long = 4320000


    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                handler.post { createNotification() }
            }
        }
        timer!!.schedule(timerTask, 1, milliSeconds)
        return START_STICKY
    }

    override fun onCreate() {}

    override fun onDestroy() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
        super.onDestroy()
    }

    private fun createNotification() {
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(applicationContext, defaultNotificationChannelID)
        mBuilder.setContentTitle("Anan")
        mBuilder.setContentText("Hey! You Should See This Word.")
        mBuilder.setSmallIcon(R.drawable.ic_baseline_add_24)
        mBuilder.setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                notificationChannelID,
                "NOTIFICATION_CHANNEL_NAME",
                importance
            )
            mBuilder.setChannelId(notificationChannelID)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        mNotificationManager.notify(System.currentTimeMillis().toInt(), mBuilder.build())
    }
}