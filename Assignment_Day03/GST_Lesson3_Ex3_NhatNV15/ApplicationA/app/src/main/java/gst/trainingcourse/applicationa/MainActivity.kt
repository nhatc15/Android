package gst.trainingcourse.applicationa

import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val MY_ACTION = "com.example.broadcast.MY_ACTION"
    private val receiver = MyReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnIntent:Button = findViewById<Button>(R.id.btnIntent)
        val btnBroadcast:Button = findViewById<Button>(R.id.btnBroadcast)

        btnIntent.setOnClickListener {
            val intent = packageManager.getLaunchIntentForPackage("gst.trainingcourse.applicationb")
            intent?.let {
                startActivity(it)
            }
        }

        btnBroadcast.setOnClickListener {
            sendBroadcast(Intent(MY_ACTION))
        }
    }
    override fun onStart() {
        super.onStart()
        registerReceiver(receiver, IntentFilter(MY_ACTION))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}