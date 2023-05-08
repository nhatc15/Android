package com.myapp.newsapp.data.network.firebase.user

import com.myapp.newsapp.data.network.firebase.model.FireBaseUser
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun signIn(email:String, password:String):Boolean
    suspend fun signUp(fireBaseUser: FireBaseUser):Boolean
    suspend fun signOut(fireBaseUser: FireBaseUser)
    fun updateUserInfo(fireBaseUser: FireBaseUser?, updatedInfo: HashMap<String,Any?>):Boolean
    fun getUserInfo(): Flow<FireBaseUser>
    fun getUserUid(): String
}