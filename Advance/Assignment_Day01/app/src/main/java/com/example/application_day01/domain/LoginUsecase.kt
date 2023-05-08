package com.example.application_day01.domain

import com.example.application_day01.data.UserState
import com.example.application_day01.data.model.User
import com.example.application_day01.data.repo.UserRepository
import com.example.application_day01.data.repo.UserRepositoryImp

class LoginUsecase(
    private val userRepositoryImp: UserRepositoryImp
) {
    suspend operator fun invoke(username: String, password: String): UserState =
        userRepositoryImp.login(username,password)
}