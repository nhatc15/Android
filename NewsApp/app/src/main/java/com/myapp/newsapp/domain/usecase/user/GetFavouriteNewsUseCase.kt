package com.myapp.newsapp.domain.usecase.user

import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.domain.repo.UserRepository
import com.myapp.newsapp.presentation.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFavouriteNewsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<UserResource<List<Article>>> = flow {
        userRepository.getFavouriteNews().collect(){
            try {
                emit(UserResource.Loading)
                if(it.isEmpty()){
                    emit(UserResource.Failed("List empty"))
                }else{
                    emit(UserResource.Success(it))
                }

            }catch (ex:Exception){
                emit(UserResource.Failed(ex.message.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)
}