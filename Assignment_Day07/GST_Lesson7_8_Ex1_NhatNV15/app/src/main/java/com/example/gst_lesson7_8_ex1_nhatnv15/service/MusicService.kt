package com.example.gst_lesson7_8_ex1_nhatnv15.service

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.gst_lesson7_8_ex1_nhatnv15.R
import com.example.gst_lesson7_8_ex1_nhatnv15.model.Song
import com.example.gst_lesson7_8_ex1_nhatnv15.ui.MainActivity

class MusicService : Service() {
    lateinit var mediaPlayer: MediaPlayer
    val songs = mutableListOf<Song>()


    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        Log.d("MusicService", "onCreate")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d("MusicService", "onBind")
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songId = intent?.getIntExtra("songId", -1)
        showNotification()
        Log.d("MusicService", "$songId")
        songId?.let {
            if (it != -1) {
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(this, songId)
                mediaPlayer.start()
            }
        }

        when (intent?.getStringExtra("onStartCommand")) {
            "prev" -> {
                Toast.makeText(applicationContext, "prev", Toast.LENGTH_SHORT).show()
            }
            "play" -> {
                mediaPlayer.start()
            }
            "pause" -> {
                if(mediaPlayer.isPlaying)
                    mediaPlayer.pause()
                else{
                    mediaPlayer.start()
                }
            }
            "next" -> {
                Toast.makeText(applicationContext, "next", Toast.LENGTH_SHORT).show()
            }
        }
        return START_STICKY
    }

    private fun showNotification() {
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(
                    this, 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }

        val notification: Notification =
            Notification.Builder(this, "notification_channel_id")
                .setContentTitle("Music app")
                .setContentText("Song")
                .setSmallIcon(R.drawable.ic_play)
                .setContentIntent(pendingIntent)
                .build()

        startForeground(1, notification)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("MusicService", "onUnBind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MusicService", "onDestroy")
        mediaPlayer.stop()
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

}