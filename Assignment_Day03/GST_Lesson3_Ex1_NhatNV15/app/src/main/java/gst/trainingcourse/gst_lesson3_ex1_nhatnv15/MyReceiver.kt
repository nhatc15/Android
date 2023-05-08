package gst.trainingcourse.gst_lesson3_ex1_nhatnv15

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import android.content.Intent.ACTION_SCREEN_ON
import android.util.Log

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val i = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        when(intent.action){
            ACTION_BOOT_COMPLETED -> {
                Log.i("onReceive", "call onReceive ACTION_BOOT_COMPLETED")
                context.startActivity(i)
            }
            ACTION_SCREEN_ON -> {
                Log.i("onReceive", "call onReceive ACTION_SCREEN_ON")
                context.startActivity(i)
            }
            else -> {}
        }

    }
}