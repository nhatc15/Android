package com.example.gst_lesson7_8_ex1_nhatnv15

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChanel()
    }

    private fun createNotificationChanel() {
        val notificationChannel = NotificationChannel(
            /* id = */ "notification_channel_id",
            /* name = */ "notification_channel_name",
            /* importance = */ NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

    }
}