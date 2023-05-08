package com.example.application_day01.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.application_day01.data.model.User

@Database(
    entities = [User::class],
    version = 1
    )
abstract class  UserDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}