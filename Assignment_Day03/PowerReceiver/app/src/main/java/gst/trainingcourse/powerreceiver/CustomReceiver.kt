package gst.trainingcourse.powerreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CustomReceiver : BroadcastReceiver() {

    private val ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST"

    override fun onReceive(context: Context, intent: Intent) {
        val intentAction: String? = intent.action
        if (intentAction != null) {
            var toastMessage = "unknown intent action"
            when (intentAction) {
                Intent.ACTION_POWER_CONNECTED ->
                    toastMessage = "Power connected"

                Intent.ACTION_POWER_DISCONNECTED ->
                    toastMessage = "Power disconnected"
                ACTION_CUSTOM_BROADCAST ->
                    toastMessage = "Custom Broadcast Recieved"
            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

}