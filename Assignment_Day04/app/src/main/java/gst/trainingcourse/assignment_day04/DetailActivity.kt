package gst.trainingcourse.assignment_day04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val user = intent.getSerializableExtra("EXTRA") as User
        tvUserName.text = user.username
        tvFirstName.text = user.firstname
        tvLastName.text = user.lastname
        tvAge.text = user.age.toString()
        btnBack.setOnClickListener{
            finish()
        }
    }
}