package com.example.contentproviderapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contentproviderapp.databinding.ActivityMainBinding
import com.example.myapplication.UserAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private val userList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUsers()
        userAdapter = UserAdapter(userList)
        binding.rvUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }

    fun getUsers(){
        val uri = Uri.parse("content://com.example.application_day01.data.provider/user")
        contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )?.use {
            it.use { cursor ->
                while (cursor.moveToNext()) {
                    val user = User(
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("username")),
                        cursor.getString(cursor.getColumnIndexOrThrow("password"))
                    )
                    userList.add(user)
                }
            }
        }
    }

}