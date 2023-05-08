package com.myapp.newsapp.data.mapper

import com.myapp.newsapp.data.network.firebase.model.FireBaseUser
import com.myapp.newsapp.presentation.model.User

object UserMapper {

    fun fromFirebaseUser(fireBaseUser: FireBaseUser): User {
        return User(
            email = fireBaseUser.email,
            password = fireBaseUser.password,
            firstName = fireBaseUser.firstName,
            lastName = fireBaseUser.lastName,
            birth = fireBaseUser.birth
        )
    }

    fun toFirebaseUser(user: User): FireBaseUser{
        return FireBaseUser(
            uid = user.uid ?: "",
            email = user.email,
            password = user.password,
            firstName = user.firstName ?: "",
            lastName = user.lastName ?: "",
            birth = user.birth ?: ""
        )
    }

}