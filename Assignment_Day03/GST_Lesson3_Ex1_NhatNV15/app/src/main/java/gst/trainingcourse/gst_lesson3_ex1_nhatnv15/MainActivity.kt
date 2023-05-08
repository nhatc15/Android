package gst.trainingcourse.gst_lesson3_ex1_nhatnv15

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings

class MainActivity : AppCompatActivity() {
    private val myReceiver = MyReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(myReceiver, IntentFilter(Intent.ACTION_BOOT_COMPLETED))
        registerReceiver(myReceiver, IntentFilter(Intent.ACTION_SCREEN_ON))

        if(!Settings.canDrawOverlays(applicationContext)){
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
        }
    }
}