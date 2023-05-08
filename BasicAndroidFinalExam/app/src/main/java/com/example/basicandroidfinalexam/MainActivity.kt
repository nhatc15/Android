package com.example.basicandroidfinalexam

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var btnStartService: Button
private lateinit var mService: MyService
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MyService.LocalBinder
            mService = binder.getService()
            mBound = true
            if(mBound) {
                val sum:Long = mService.result
                dialogShow(sum)
            }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStartService = findViewById(R.id.btnStartService)
        btnStartService.setOnClickListener{
            Intent(this, MyService::class.java).also { intent ->
                bindService(intent, connection, Context.BIND_AUTO_CREATE)

            }
        }
    }
    fun dialogShow(sum: Long){
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Service")
            .setMessage("Service complete action with sum = $sum")
            .setIcon(R.drawable.ic_launcher_foreground)
            .setPositiveButton("OK"){dialog,which->
            }
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

}