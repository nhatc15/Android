package com.example.server_app

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat


const val LOCATION_PERMISSION="Location permission is still not granted!"
const val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
const val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION

class MainActivity : AppCompatActivity() {
    private val requestPermission =registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){
            result->
        if(!result.getOrDefault(FINE_LOCATION,false) ||
            !result.getOrDefault(COARSE_LOCATION,false))
        {
            Toast.makeText(this,LOCATION_PERMISSION, Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkNeccessaryPermissions()
    }

    private fun checkNeccessaryPermissions() {
        when{
            !isGranted(FINE_LOCATION) || !isGranted(COARSE_LOCATION) ->
                requestPermission.launch(
                    arrayOf(FINE_LOCATION ,COARSE_LOCATION)
                )
        }
    }

    private fun Context.isGranted(permission:String) :Boolean{
        return ContextCompat.checkSelfPermission(this,permission)== PackageManager.PERMISSION_GRANTED
    }
}