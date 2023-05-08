package com.example.basicandroidfinalexam

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

const val TAG = "tag"
@Suppress("UNREACHABLE_CODE")
class MyService : Service() {
    var result:Long = 0
    private val binder = LocalBinder()
    inner class LocalBinder : Binder() {
        fun getService(): MyService = this@MyService
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG,"onBind")
        CoroutineScope(Dispatchers.IO).launch {
            var sum : Long = 0
            for(i in 1.. 1000000){
                sum += i
            }
            delay(5000L)
            Log.d(TAG,"$sum")
            result = sum
        }
//        runBlocking {
//            var sum : Long = 0
//            for(i in 1.. 1000000){
//                sum += i
//            }
//            delay(5000L)
//            Log.d(TAG,"$sum")
//            result = sum
//        }
        return binder
    }
}