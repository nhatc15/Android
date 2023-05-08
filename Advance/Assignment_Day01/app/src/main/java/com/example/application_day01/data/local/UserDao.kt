package com.example.application_day01.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.application_day01.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUser(): List<User>

    @Insert(User::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun register(user: User)

    @Query("SELECT EXISTS (SELECT username FROM users WHERE username = :username AND password = :password)")
    suspend fun login(username: String, password: String): Boolean

    @Query("SELECT EXISTS(SELECT username FROM users WHERE username = :userName)")
    suspend fun isExist(userName: String): Boolean

    @Query("SELECT * FROM users")
    fun getAllUserCursor(): Cursor
}