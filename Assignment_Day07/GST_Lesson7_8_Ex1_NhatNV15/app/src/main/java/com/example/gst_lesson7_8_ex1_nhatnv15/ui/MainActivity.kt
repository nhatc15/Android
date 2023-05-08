package com.example.gst_lesson7_8_ex1_nhatnv15.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.ImageView
import com.example.gst_lesson7_8_ex1_nhatnv15.R
import com.example.gst_lesson7_8_ex1_nhatnv15.databinding.ActivityMainBinding
import com.example.gst_lesson7_8_ex1_nhatnv15.model.Song
import com.example.gst_lesson7_8_ex1_nhatnv15.service.MusicService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val songs = listOf(
        Song("Call the nightingale","Vian Izak" ,R.raw.a),
        Song("This is what heartbreak fell like","JVKE",R.raw.b),
        Song("Pastlives","BORNS",R.raw.c),
        Song("Love Story","Taylor Swift",R.raw.d),
        Song("We are the crystal gem","Steven Universe",R.raw.e)
    )

    private lateinit var mService: MusicService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = (service as MusicService.MusicBinder).getService()
            mService.songs.addAll(songs)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun play(song: Song) {
        Intent(this, MusicService::class.java)
            .putExtra("songId", song.songId)
            .also {intent ->
                startService(intent)
            }
        binding.flMusicPlayer.visibility = View.VISIBLE
        findViewById<ImageView>(R.id.ivPlay).visibility = View.VISIBLE
        findViewById<ImageView>(R.id.ivPause).visibility = View.GONE

    }

    fun pause() {
        findViewById<ImageView>(R.id.ivPlay).visibility = View.GONE
        findViewById<ImageView>(R.id.ivPause).visibility = View.VISIBLE
        Intent(this, MusicService::class.java)
            .putExtra("onStartCommand", "pause")
            .also {intent ->
                startService(intent)
            }
    }

    fun next() {
        Intent(this, MusicService::class.java)
            .putExtra("onStartCommand", "next")
            .also {intent ->
                startService(intent)
            }
    }

    fun prev() {
        Intent(this, MusicService::class.java)
            .putExtra("onStartCommandd", "prev")
            .also {intent ->
                startService(intent)
            }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, MusicService::class.java).also {
            bindService(it, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        unbindService(connection)
        Intent(this, MusicService::class.java)
            .also {intent ->
                startService(intent)
            }
        super.onStop()
    }

}