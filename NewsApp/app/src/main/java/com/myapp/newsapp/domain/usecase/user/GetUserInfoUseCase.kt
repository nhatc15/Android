package com.myapp.newsapp.domain.usecase.user

import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.domain.repo.UserRepository
import com.myapp.newsapp.presentation.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
)  {
    suspend operator fun invoke(): Flow<UserResource<User>> = flow{
        emit(UserResource.Loading)
        try {
            userRepository.getUserInfo().collect(){ result->
                emit(UserResource.Success(result))
            }
        }catch (ex: Exception){
            emit(UserResource.Failed(ex.message.toString()))
        }

    }.flowOn(Dispatchers.IO)
}