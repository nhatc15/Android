package com.example.assignment_day02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
const val TAG2 = "Activity 2"
class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Log.d(TAG2, "onCreate: this = $this ")

        val sendData = intent.getStringExtra("send_key")
        val replaceData = sendData?.replace('A', 'B')
        val textView2 = findViewById<TextView>(R.id.textView2)
        textView2.text = replaceData
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            val string = intent.putExtra("result_key", replaceData)
            setResult(RESULT_OK, string)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG2,"onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG2,"onResume: this = $this")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG2,"onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG2,"onStop: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG2, "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG2, "onDestroy: ")
    }
}