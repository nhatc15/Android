package gst.trainingcourse.assignment_day04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_user.*
import kotlinx.android.synthetic.main.item_user.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var userAdapter = mutableListOf(
            User("Ace01","Ace","Brack",21),
            User("Peter02","Peter","Parker",25),
            User("John14","John","Smith",32),
            User("Mia34","Mia","Smith",19),
        )

        val adapter = UserAdapter(userAdapter)
        rvUser.adapter = adapter
        rvUser.layoutManager = LinearLayoutManager(this)
        adapter.setButtonClickListener(object : UserAdapter.OnItemClickListener{
            override fun onClick(position: Int) {
                val user = userAdapter[position]
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra("EXTRA",user)
                    startActivity(it)
                }
            }
        })
    }
}