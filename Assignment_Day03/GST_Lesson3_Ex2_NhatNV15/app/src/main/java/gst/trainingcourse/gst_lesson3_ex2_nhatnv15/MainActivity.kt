package gst.trainingcourse.gst_lesson3_ex2_nhatnv15

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val list = listOf("Hello!", "Hi!", "Salut!", "Hallo!", "Ciao!",
        "Ahoj!", "YAH sahs!", "Bog!", "Hej!", "Czesc!", "Ní hảo!",
        "Kon’nichiwa!", "Annyeonghaseyo!", "Shalom!", "Sah-wahd-dee-kah!", "Merhaba!", "Hujambo!", "Olá!")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_click1).setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java).apply { putStringArrayListExtra("LIST", ArrayList(list)) })
        }

    }
}