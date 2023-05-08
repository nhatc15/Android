package com.example.application_day01.domain

import com.example.application_day01.data.UserState
import com.example.application_day01.data.model.User
import com.example.application_day01.data.repo.UserRepositoryImp

class RegisterUsecase(
    private val userRepositoryImp: UserRepositoryImp
) {
    suspend operator fun invoke(user:User): UserState =
        userRepositoryImp.register(user)
}