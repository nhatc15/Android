package com.example.server_app

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class PassDataService : Service() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate() {
        super.onCreate()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private val result = object: ServiceAidlInterface.Stub(){
        override fun operation(): Int {
            val x = 1000
            process()
            return (x * (x + 1))/2
        }

        override fun process() {
            Thread.sleep(5000L)
        }

        @SuppressLint("MissingPermission")
        override fun locateMe(): String {
            var locationResult = "Location Info is not found !"
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->

                locationResult = StringBuilder("Lat : ")
                    .append(location.latitude.toString())
                    .append("\nLong :")
                    .append(location.longitude.toString())
                    .toString()
            }.addOnFailureListener {
            }
            Thread.sleep(100)
            return locationResult
        }
    }
    override fun onBind(intent: Intent): IBinder {
        return result
    }
}