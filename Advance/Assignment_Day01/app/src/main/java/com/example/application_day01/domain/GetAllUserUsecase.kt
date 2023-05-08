package com.example.application_day01.domain

import com.example.application_day01.data.model.User
import com.example.application_day01.data.repo.UserRepositoryImp
import kotlinx.coroutines.flow.Flow

class GetAllUserUsecase(
    private val userRepositoryImp: UserRepositoryImp
) {
    operator fun invoke(): List<User> = userRepositoryImp.getAllUser()
}