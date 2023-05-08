package gst.trainingcourse.applicationa

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReceiver : BroadcastReceiver() {
    private val MY_ACTION = "com.example.broadcast.MY_ACTION"
    override fun onReceive(p0: Context?, p1: Intent?) {
        val pm = p0?.packageManager
        if (MY_ACTION == p1?.action) {
            p0?.startActivity(pm?.getLaunchIntentForPackage("gst.trainingcourse.applicationb"))
        }
    }
}