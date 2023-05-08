package com.myapp.newsapp.domain.repo

import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.presentation.model.Article
import com.myapp.newsapp.presentation.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun signUp(user: User): UserResource<String>
    suspend fun signIn(email: String, password: String): UserResource<String>
    suspend fun signOut(user: User)

    suspend fun getUserInfo(): Flow<User>
    suspend fun updateUserInfo(user:User, updatedUserInfo: HashMap<String, Any?>): UserResource<String>
    suspend fun addToFavourite(article: Article): UserResource<String>
    suspend fun deleteFavouriteNew(article: Article): UserResource<String>
    fun getFavouriteNews(): Flow<List<Article>>
    fun getUserUid(): String
}