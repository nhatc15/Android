package gst.trainingcourse.gst_lesson3_ex2_nhatnv15

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        findViewById<TextView>(R.id.txtMain2).setText(intent.getStringArrayListExtra("LIST").toString())
    }
}