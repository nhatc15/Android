package com.myapp.newsapp.data.repo

import com.google.firebase.FirebaseException
import com.myapp.newsapp.data.mapper.NewsMapper
import com.myapp.newsapp.data.mapper.UserMapper
import com.myapp.newsapp.data.mapper.UserMapper.toFirebaseUser
import com.myapp.newsapp.data.network.firebase.favourite.FavouriteDataSource
import com.myapp.newsapp.data.network.firebase.user.UserDataSource
import com.myapp.newsapp.domain.repo.UserRepository
import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.presentation.model.Article
import com.myapp.newsapp.presentation.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val userDataSource: UserDataSource,
    private val favouriteDataSource: FavouriteDataSource
):UserRepository {
        override suspend fun signUp(user: User): UserResource<String> {
        try {
            return when{
                user.email.isEmpty() -> UserResource.Failed("Email can't be empty")
                user.password.isEmpty() -> UserResource.Failed("Password can't be empty")
                !userDataSource.signUp(toFirebaseUser(user)) -> UserResource.Failed("Sign up failed")
                else -> UserResource.Success("Sign up successfully")
            }
        }catch (ex: Exception){
            return UserResource.Failed(ex.message.toString())
        }
    }

    override suspend fun signIn(email: String, password: String): UserResource<String> {
        try{
            return when{
                email.isEmpty() -> UserResource.Failed("Email can't be empty")
                password.isEmpty() -> UserResource.Failed("Password can't be empty")
                !userDataSource.signIn(email,password) -> UserResource.Failed("Wrong email or password!")
                else -> UserResource.Success("Sign in successfully")
            }
        } catch (ex: Exception){
            return UserResource.Failed(ex.message.toString())
        }
    }

    override suspend fun signOut(user: User) {
        userDataSource.signOut(toFirebaseUser(user))
    }

    override suspend fun getUserInfo(): Flow<User> {
        return userDataSource.getUserInfo().map {
            UserMapper.fromFirebaseUser(it)
        }
    }

    override suspend fun updateUserInfo(user: User, updatedUserInfo: HashMap<String, Any?>): UserResource<String> {
        return try {
            run {
                userDataSource.updateUserInfo(UserMapper.toFirebaseUser(user), updatedUserInfo)
                UserResource.Success("Success")
            }
        } catch (ex: FirebaseException) {
            UserResource.Failed(ex.message.toString())
        }
    }

    override suspend fun addToFavourite(article: Article): UserResource<String> {
        return when{
            !favouriteDataSource.insertFavouriteNews(NewsMapper.toFireBase(article)) -> UserResource.Failed("Add failed")
            else -> UserResource.Success("Add successfully")
        }
    }

    override suspend fun deleteFavouriteNew(article: Article): UserResource<String> {
        return when{
            !favouriteDataSource.deleteFavouriteNews(NewsMapper.toFireBase(article)) -> UserResource.Failed("Delete failed")
            else -> UserResource.Success("Delete successfully")
        }
    }

    override fun getFavouriteNews(): Flow<List<Article>> {
        return favouriteDataSource.getFavouriteNews().map { NewsMapper.fromFireBase(it) }
    }

    override fun getUserUid(): String {
        return userDataSource.getUserUid()
    }
}