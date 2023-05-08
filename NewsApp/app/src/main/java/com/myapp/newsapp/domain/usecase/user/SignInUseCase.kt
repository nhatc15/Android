package com.myapp.newsapp.domain.usecase.user

import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.domain.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<UserResource<String>> = flow {
        emit(UserResource.Loading)

        val result = userRepository.signIn(email,password)
        emit(result)
    }.flowOn(Dispatchers.IO)
}