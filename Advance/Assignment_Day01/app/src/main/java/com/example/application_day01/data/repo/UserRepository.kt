package com.example.application_day01.data.repo

import android.database.Cursor
import com.example.application_day01.data.UserState
import com.example.application_day01.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(username: String, password:String): UserState
    suspend fun register(user: User): UserState
    fun getAllUser(): List<User>
    fun getAllUserCursor(): Cursor
}