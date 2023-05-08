package com.example.application_day01.data.repo

import android.database.Cursor
import com.example.application_day01.data.UserState
import com.example.application_day01.data.local.UserDatabase
import com.example.application_day01.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val db: UserDatabase
): UserRepository{
    override suspend fun login(username: String, password: String): UserState=
        when (db.userDao.login(username,password)){
            true -> UserState.Success
            false -> UserState.Failed
        }

    override suspend fun register(user: User): UserState =
        if(db.userDao.isExist(user.username)){
            UserState.Failed
        }else{
            db.userDao.register(user)
            UserState.Success
        }

    override fun getAllUser(): List<User> {
        return db.userDao.getAllUser()
    }

    override fun getAllUserCursor(): Cursor {
        return db.userDao.getAllUserCursor()
    }


}