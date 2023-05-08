package com.example.finalexam

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.server_app.ServiceAidlInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val ACTION = "pass_data"
class MainActivity : AppCompatActivity() {

    private var serverService: ServiceAidlInterface? = null

    private var isBoundServerSerice = true



    private val mServerConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            isBoundServerSerice = true
            serverService = ServiceAidlInterface.Stub.asInterface(p1)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBoundServerSerice = false
            serverService = null
        }
    }

    private fun connectToServerService() {
        val intent = Intent(ACTION)
        this.bindService(
            convertingIntent(intent, this),
            mServerConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    private fun convertingIntent(implicit: Intent, requireContext: Context): Intent {
        val pm = requireContext.packageManager
        val resolvedList = pm.queryIntentServices(implicit, 0)
        val explicit = Intent(implicit)
        if (resolvedList == null || resolvedList.size != 1) {
            return explicit
        }
        val serviceInfo = resolvedList[0]
        val componetnName =
            ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name)
        explicit.component = componetnName
        return explicit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectToServerService()
        findViewById<Button>(R.id.btnCount).setOnClickListener {
            val result = serverService?.operation()
            Log.d(ACTION, result.toString())
            findViewById<TextView>(R.id.tvCount).setText(result.toString())
//            Toast.makeText(this,"${result.toString()}",Toast.LENGTH_LONG).show()
        }

        findViewById<Button>(R.id.btnGetLocation).setOnClickListener {
            val location = serverService?.locateMe()
            Log.d(ACTION, location.toString())
            findViewById<TextView>(R.id.tvLocation).setText(location.toString())
//            Toast.makeText(this,"${location.toString()}",Toast.LENGTH_LONG).show()
        }
    }
}